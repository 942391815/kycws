package com.wxy.dg.modules.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.modules.model.Organization;

@Repository
public class OrgDao extends BaseDao<Organization> {

	// 删除组织结构
	public void delete(int id) {
		// 删除组织
		deleteById(id);
		// 删除下级组织
		List<Integer> childIds = findChildList(id);
		for (Integer childId : childIds) {
			deleteById(childId);
		}
	}

	// 获取所有子组织id
	public List<Integer> findChildList(int id) {
		List<Integer> orgs = new ArrayList<Integer>();
		String hql = "select id from Organization where parent.id = ?";
		getChilds(hql, id, orgs);
		return orgs;
	}

	private void getChilds(String hql, int id, List<Integer> list) {
		List<Integer> childList = find(hql, id);
		if (null != childList && childList.size() > 0) {
			list.addAll(childList);
			for (Integer orgId : childList) {
				getChilds(hql, orgId, list);
			}
		}
	}

	// 获取所有上级组织
	public String findParentList(Organization org) {
		StringBuffer sb = new StringBuffer();
		getParens(org, sb);
		sb.append("org" + org.getId() + ",");
		return sb.toString();
	}

	// 获取所有上级组织
	private void getParens(Organization org, StringBuffer sb) {
		if(org.getParent() != null) {
			sb.append("org" + org.getParent().getId() + ",");
			getParens(org.getParent(),sb);
		}
	}
	
	public List<Organization> findAllValid(){
		String hql = "from Organization where del_flag = '0'";
		return find(hql);
	}
	
	public Organization getByName(String name) {
		return getByHql("from Organization where name = ? and del_flag = '0'", name);
	}
}