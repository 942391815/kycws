package com.wxy.dg.common.enums;

/**
 * Created by test on 2016/12/3.
 */
public enum  SubInfoEnum {
    NOT_EXIST(0,"没有购买套餐"),
    INSUFFICIENT(1,"余量不足"),
    UNKNOW_PACKAGE(2,"套餐类型未知"),
    SUCCESS(3,"提交成功"),
    EXCEED_MAX(4,"超过企业用户最大的日处理量，请选择其他时间"),
    ERROR(-1,"提交失败");

    private int code;
    private String message;

    SubInfoEnum(int code,String message){
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
