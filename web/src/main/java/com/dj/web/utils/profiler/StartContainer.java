package com.dj.web.utils.profiler;

import java.util.LinkedList;

/**
 * Created by daijie on 2017-7-6.
 */
public class StartContainer {

    private String uuid;

    private Integer level;

    private LinkedList<ExecNode> nodes;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public LinkedList<ExecNode> getNodes() {
        return nodes;
    }

    public void setNodes(LinkedList<ExecNode> nodes) {
        this.nodes = nodes;
    }
}
