package com.wxy.dg.modules.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "organization")
@DynamicInsert @DynamicUpdate
public class Organization implements Serializable {

	private static final long serialVersionUID = 6112456163933930772L;
	// 编号
	private int id;
	// 上级机构
	private Organization parent;
	// 名称
	private String name;
	// 负责人
	private String master;
	// 负责人电话
	private String phone;
	// 备注
	private String remark;
	// 删除标识
	private String del_flag;
	
	private List<User> userList = new ArrayList<User>(); // 拥有用户列表

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Organization getParent() {
		return parent;
	}
	public void setParent(Organization parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=30)
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	
	@Length(min=0, max=20)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=1, max=1)
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='0'")
	@OrderBy(value = "id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Transient
	public static void sortList(List<Organization> list, List<Organization> sourcelist, int parentId){
		for (int i=0; i<sourcelist.size(); i++){
			Organization e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()== parentId){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					Organization child = sourcelist.get(j);
					if (child.getParent()!=null && child.getParent().getId() == e.getId()){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}

}