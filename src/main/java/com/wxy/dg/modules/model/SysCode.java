package com.wxy.dg.modules.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_code")
public class SysCode implements Serializable {
	
	private static final long serialVersionUID = -6758376728985579310L;
	private String code_id;
	private String code_name;
	private String code_type;
	private Integer code_order;
	
	@Id
	@GeneratedValue
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public Integer getCode_order() {
		return code_order;
	}
	public void setCode_order(Integer code_order) {
		this.code_order = code_order;
	}
}