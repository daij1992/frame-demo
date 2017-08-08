package com.dj.web.utils;

/**
 * Created by daijie on 2017-8-4.
 */
public class ResponseStatus {

    //状态码
    private String ret_code;
    //状态码描述
    private String ret_msg;


    public ResponseStatus(String ret_code, String ret_msg) {
        this.ret_code = ret_code;
        this.ret_msg = ret_msg;
    }

    public  ResponseStatus(){
        super();
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }



}
