package com.wxy.dg.common.model;

import java.io.Serializable;

/**
 * Created by test on 2016/12/3.
 */
public class ResponseModel implements Serializable{

    private int status;
    private String message;
    private Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
