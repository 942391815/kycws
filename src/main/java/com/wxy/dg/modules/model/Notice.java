package com.wxy.dg.modules.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "notice")
@DynamicInsert
@DynamicUpdate
public class Notice implements Serializable {

	private static final long serialVersionUID = 4368071075166832360L;

	// 编号
	private int id;
	// 标题
	private String title;
	// 内容
	private String content;
	// 发送时间
	private Date send_time;
	// 选中的发送对象
	private String objIds;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Length(min = 1, max = 50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 1, max = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public String getObjIds() {
		return objIds;
	}

	public void setObjIds(String objIds) {
		this.objIds = objIds;
	}

}