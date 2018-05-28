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
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class User implements Serializable, Comparable<User> {

	private static final long serialVersionUID = 4128733939664385260L;
	// 编号
	private int id;
	// 登录名
	private String login_name;
	// 密码
	private String password;
	// 新密码
	private String newPassword;
	// 姓名
	private String name;
	// 手机号
	private String phone;
	// IMEI
	private String imei;
	// 定位频率
	private int frequency;
	// 组织机构
	private Organization organization;
	// 用户类型
	private String user_type;
	// 设备类型 3:android,4:ios
	private String deviceType;
	// 推送通道ID
	private String channelId;
	// 最后登陆日期
	private Date login_date;
	// 创建日期
	private Date create_date;
	// 更新日期
	private Date update_date;
	// 备注
	private String remark;
	// 删除标识
	private String del_flag;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Length(min = 1, max = 15)
	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	@Length(min = 1, max = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Length(min = 1, max = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 20)
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@ManyToOne
	@JoinColumn(name = "orgId")
	@NotFound(action = NotFoundAction.IGNORE)
//	@NotNull(message = "组织机构不能为空")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Length(min = 1, max = 3)
	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	@Length(min = 0, max = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Date getLogin_date() {
		return login_date;
	}

	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	@Length(min = 1, max = 1)
	public String getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

	@Transient
	public static boolean isAdmin(String id) {
		return id != null && id.equals("1");
	}

	@Override
	public int compareTo(User o) {
		return o.organization.getName().compareTo(this.organization.getName());
	}

}