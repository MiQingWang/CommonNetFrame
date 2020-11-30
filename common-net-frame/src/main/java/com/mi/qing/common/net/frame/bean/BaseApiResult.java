package com.mi.qing.common.net.frame.bean;

import java.io.Serializable;

public class BaseApiResult<T> implements Serializable {

    private int code;
    private T data;
    private String message;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getResult() {
        return code;
    }

    public void setResult(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}
