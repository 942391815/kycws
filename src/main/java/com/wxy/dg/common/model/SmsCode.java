package com.wxy.dg.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by test on 2016/12/7.
 */
public class SmsCode implements Serializable{

    private Long id;
    private String code;
    private String mobile;
    private Date createTime;
    private String sortColumns;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSortColumns() {
        return sortColumns;
    }

    public void setSortColumns(String sortColumns) {
        this.sortColumns = sortColumns;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
