package com.dj.web.utils.profiler;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;

/**
 * Created by daijie on 2017-7-5.
 */
public class Profiler {

    private static ThreadLocal<StartContainer> startThreadHolder = new ThreadLocal<StartContainer>();

    private static ThreadLocal<StopContainer> stopThreadHolder = new ThreadLocal<StopContainer>();

    private static final Logger LOGGER = LoggerFactory.getLogger(Profiler.class);

    public static void start(String name){

        LOGGER.debug("thread-{} :{} start", Thread.currentThread().getId(),name);

        StartContainer start =startThreadHolder.get();
        if(start == null){
            start = new StartContainer();
            start.setUuid(System.currentTimeMillis()+"");
            int level  = 0;
            ExecNode node = new ExecNode();
            node.setName(name);
            node.setLevel(level);
            node.setStart(System.currentTimeMillis());


            LinkedList<ExecNode> nodes = start.getNodes();
            if(nodes == null){
                nodes = new LinkedList<ExecNode>();
                nodes.addLast(node);
                start.setNodes(nodes);
            }else{
                nodes.addLast(node);
            }

            start.setLevel(level+1);
            startThreadHolder.set(start);
        }else{
            int level  = start.getLevel();
            ExecNode node = new ExecNode();
            node.setName(name);
            node.setLevel(level);
            node.setStart(System.currentTimeMillis());


            LinkedList<ExecNode> nodes = start.getNodes();
            if(nodes == null){
                nodes = new LinkedList<ExecNode>();
                nodes.addLast(node);
            }else{
                nodes.addLast(node);
            }

            start.setLevel(level+1);
        }


    }


    public static void stop(String name, Integer monitorTime){
        LOGGER.debug("thread-{} : {} stop , monitorTime:{}", Thread.currentThread().getId(),name,monitorTime);


        StartContainer start = startThreadHolder.get();
        int level = start.getLevel();
        LinkedList<ExecNode> pushNodes = start.getNodes();
        ExecNode node = pushNodes.getLast();
        node.setEnd(System.currentTimeMillis());
        node.setCost(node.getEnd() - node.getStart());
        pushNodes.removeLast();

        StopContainer stop = stopThreadHolder.get();
        if(stop == null){
            stop = new StopContainer();
            stop.setUuid(start.getUuid());


            LinkedList<ExecNode> popNodes = stop.getNodes();
            if(popNodes == null){
                popNodes = new LinkedList<ExecNode>();
                popNodes.addFirst(node);

                stop.setNodes(popNodes);
                stopThreadHolder.set(stop);
            }else{
                popNodes.addFirst(node);
            }
        }else{
            stop.getNodes().addFirst(node);
        }


        if(level != 1){
            start.setLevel(level-1);
        }else{
            if(node.getCost().intValue() > monitorTime){
                LOGGER.info("Profiler: traceid:{} total time:{}  >  monitorTime:{} trace details ",start.getUuid(),node.getCost(),monitorTime);
                LinkedList<ExecNode> nodes = stop.getNodes();
                for(int i = 0 ;i < nodes.size();i++){
                    node = nodes.get(i);
                    LOGGER.info("Profiler:traceid:{} cost:{} trace:{}  method:{}",start.getUuid(),node.getCost(),node.getLevel(),node.getName());
                }
            }else {
                LOGGER.info("Profiler:traceid:{} total time:{}  <  monitorTime:{} perfect,ignore",start.getUuid(),node.getCost(),monitorTime);
            }

            startThreadHolder.get().setNodes(null);
            stopThreadHolder.get().setNodes(null);
            //移除 防止内存泄漏
            startThreadHolder.remove();
            stopThreadHolder.remove();
        }




    }


}
