package com.wxy.dg.common.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by test on 2016/12/2.
 */
public class SubInfo implements Serializable{

    private Long sid;
    private String url;
    private String name;
    private Integer subType;
    private String describe;
    private String businessType;
    private Integer state;
    private Integer userId;//当前轮次的处理人信息
    private String openid;
    private Integer round;


    private String subTime;
    private String subTimeStart;
    private String subTimeEnd;

    /**
     * 订单的id
     */
    private Integer orderId;
    private String handleTime;

    private String sortColumns;
    /**
     * 一轮新任务定时任务处理时间
     */
    private String nextHandleTime;


    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public String getSubTimeStart() {
        return subTimeStart;
    }

    public void setSubTimeStart(String subTimeStart) {
        this.subTimeStart = subTimeStart;
    }

    public String getSubTimeEnd() {
        return subTimeEnd;
    }

    public void setSubTimeEnd(String subTimeEnd) {
        this.subTimeEnd = subTimeEnd;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getSortColumns() {
        return sortColumns;
    }

    public void setSortColumns(String sortColumns) {
        this.sortColumns = sortColumns;
    }

    public String getNextHandleTime() {
        return nextHandleTime;
    }

    public void setNextHandleTime(String nextHandleTime) {
        this.nextHandleTime = nextHandleTime;
    }
}
