package com.dj.web.aop;

import com.dj.web.utils.profiler.Profiler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daijie on 2017-7-5.
 */
@Aspect
public class ProfilerWatch {



    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilerWatch.class);

    private Integer monitorTime;

    public Integer getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(Integer monitorTime) {
        this.monitorTime = monitorTime;
    }

    @Around("execution(* com.dj.web.service.*.*(..)) || execution(* com.dj.web.dao.*.*(..)) || execution(* com.dj.web.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {


        try{
            String className = pjp.getSignature().getDeclaringTypeName();
            String methodName = pjp.getSignature().getName();
            String name = className+"."+methodName;
            Profiler.start(name);
        }catch (Exception ex){
            LOGGER.error("Profiler.start(name)  exception",ex);

        }


        try{
            Object response = pjp.proceed();
            return  response;
        }finally {
            try {
                String className = pjp.getSignature().getDeclaringTypeName();
                String methodName = pjp.getSignature().getName();
                String name = className+"."+methodName;
                Profiler.stop(name,monitorTime);
            }catch (Exception ex){
                LOGGER.error("Profiler.stop(name,monitorTime) exception",ex);
            }
        }







    }



}



