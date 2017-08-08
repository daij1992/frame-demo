package com.dj.web.utils.profiler;

import java.util.LinkedList;

/**
 * Created by daijie on 2017-7-6.
 */
public class StopContainer {

    private String uuid;
    private LinkedList<ExecNode> nodes;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LinkedList<ExecNode> getNodes() {
        return nodes;
    }

    public void setNodes(LinkedList<ExecNode> nodes) {
        this.nodes = nodes;
    }
}
