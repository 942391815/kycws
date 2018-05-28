package com.wxy.dg.common.enums;

/**
 * Created by test on 2016/12/3.
 */
public enum SubInfoStateEnum {
    NO_CLAIM(0, "没有认领的任务"),
    CLAIM_TASK(1, "已经认领，没有处理过的任务"),
    TODO_INFO(2, "已经认领，暂时保存的任务"),
    PROCESSED(3, "本轮次处理完成"),
    DONE(4, "自媒体四轮任务都处理完成"),
    ENTERPRISE_DONE(5, "企业的任务终结");



    private int code;
    private String message;

    SubInfoStateEnum(int code, String message) {
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
