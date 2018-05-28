package com.wxy.dg.common.enums;

/**
 * Created by test on 2016/12/17.
 */
public enum OrderTypeEnum {
    SELF_MEDIA(1,"自媒体"),ENTERPRISE(2,"企业"),BRAND(3,"品牌");
    private int code;
    private String message;
    OrderTypeEnum(int code, String message){
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
