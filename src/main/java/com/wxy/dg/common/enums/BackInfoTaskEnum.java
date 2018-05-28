package com.wxy.dg.common.enums;

/**
 * Created by test on 2016/12/5.
 */
public enum BackInfoTaskEnum {
    TODO_TASK(0,"待处理的定时任务"),COMPLETED_TASK(1,"已经处理完成的任务"),ERROR_TASK(2,"失败的短信任务");

    private int code;
    private String message;
    BackInfoTaskEnum(int code,String message){
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
