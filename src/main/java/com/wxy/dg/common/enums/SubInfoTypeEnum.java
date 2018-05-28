package com.wxy.dg.common.enums;

/**
 * Created by micheal on 2017/1/1.
 */
public enum  SubInfoTypeEnum {
    SELF_MEDIA(1,"自媒体"),
    ENTERPRISE(2,"企业"),
    BRAND(3,"套餐类型未知");

    private int code;
    private String message;

    SubInfoTypeEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public static SubInfoTypeEnum getSubInfoEnumByCode(int code){

        SubInfoTypeEnum[] subInfos = SubInfoTypeEnum.values();
        for (SubInfoTypeEnum each:subInfos){
            if(each.getCode()==code){
                return each;
            }
        }
        return null;
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
