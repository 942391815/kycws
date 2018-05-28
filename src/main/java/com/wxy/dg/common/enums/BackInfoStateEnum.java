package com.wxy.dg.common.enums;

/**
 * Created by test on 2016/12/17.
 */
public enum  BackInfoStateEnum {
    TEMPORARY_TASK(0,"任务暂时存储"),SUBMIT_TASK(1,"任务已经提交"),APPROVAL_TASK(3,"任务审核通过"),COMPLETE_TASK(4,"定时任务处理完成");
    private int code;
    private String message;
    BackInfoStateEnum(int code,String message){
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
