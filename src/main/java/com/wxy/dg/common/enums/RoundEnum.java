package com.wxy.dg.common.enums;

/**
 * Created by test on 2016/12/17.
 */
public enum RoundEnum {
    FIRST(1,"第一次处理"),SECOND(2,"第二次处理"),THIRD(3,"第三次处理"),FOURTH(4,"第四次处理"),END(5,"任务完结");

    private int code;
    private String message;

    RoundEnum(int code,String message){
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
