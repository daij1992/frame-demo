package com.dj.common.utils.properties;

/**
 * Created by daijie on 2017-8-4.
 */
public class NoSuchPropertyException extends Exception {

    private static final long serialVersionUID = -9199251835427552322L;

    public NoSuchPropertyException(String message)
    {
        super(message);
    }
}
