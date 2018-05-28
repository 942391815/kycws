package com.wxy.dg.modules.model;

import java.io.Serializable;
import java.util.Date;

public class Position implements Serializable {

	private static final long serialVersionUID = 7035973259016684272L;
	
	// 经度
	private Double longitude;
	// 纬度
	private Double latitude;
	// 时间
	private Date time;
	// 人员信息
	private int userId;

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}