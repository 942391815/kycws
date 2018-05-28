package com.wxy.dg.modules.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "photo")
@DynamicInsert @DynamicUpdate
public class Photo implements Serializable {

	private static final long serialVersionUID = -416879152412645797L;

	// 图片id
	private int id;
	// 图片名称
	private String name;
	// 图片描述
	private String description;
	// 图片类别
	private String category;
	// 拍摄地点
	private String shotplace;
	// 上传者
	private User uploader;
	// 上传时间
	private Date uploadtime;
	// 删除标志
	private String del_flag;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Length(min=1, max=255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min=1, max=20)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@ManyToOne
	@JoinColumn(name = "uploader_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public User getUploader() {
		return uploader;
	}

	public void setUploader(User uploader) {
		this.uploader = uploader;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	@Length(min=1, max=1)
	public String getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

	public String getShotplace() {
		return shotplace;
	}

	public void setShotplace(String shotplace) {
		this.shotplace = shotplace;
	}

	// 图片类别
	private String category_name;

	@Transient
	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
}
