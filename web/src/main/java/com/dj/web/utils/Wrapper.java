package com.dj.web.utils;

/**
 * Created by daijie on 2017-8-4.
 */
public class Wrapper<T> {

    //响应状态
    private  ResponseStatus ret;

    //相应数据
    private T data;


    public Wrapper(){
        super();
    }
    public Wrapper(T data){
        ResponseStatus ret = new ResponseStatus("0","success");
        this.ret = ret;
        this.data = data;
    }


    public Wrapper(String code, String msg, T data){
        ResponseStatus ret = new ResponseStatus(code,msg);
        this.ret = ret;
        this.data = data;
    }

    public ResponseStatus getRet() {
        return ret;
    }

    public void setRet(ResponseStatus ret) {
        this.ret = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
