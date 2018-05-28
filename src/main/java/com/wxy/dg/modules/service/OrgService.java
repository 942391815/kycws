package com.wxy.dg.modules.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.dao.OrgDao;
import com.wxy.dg.modules.model.Organization;

@Service
@Transactional(readOnly = true)
public class OrgService {

	@Autowired
	private OrgDao orgDao;

	// 删除组织结构
	@Transactional(readOnly = false)
	public void deleteOrg(int id) {
		orgDao.delete(id);
		UserUtils.removeCache(UserUtils.CACHE_ORG_LIST);
	}

	public List<Organization> getAllValid() {
		return UserUtils.getOrgList();
	}
	
	// 获取所有上级组织
	public String findParents(Organization org) {
		StringBuffer sb = new StringBuffer();
		getParents(org, sb);
		return "0," + sb.toString();
	}

	private void getParents(Organization org, StringBuffer sb) {
		if(org.getParent() != null && org.getParent().getId() != 0) {
			sb.append(org.getParent().getId() + ",");
			getParents(org.getParent(),sb);
		}
	}
	
	public Organization getByName(String name) {
		return orgDao.getByName(name);
	}
	
	public Organization getById(int id) {
		return orgDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Organization entity) {
		entity.setDel_flag(Constant.NotDeleteFlg);
		orgDao.clear();
		orgDao.save(entity);
		UserUtils.removeCache(UserUtils.CACHE_ORG_LIST);
	}

}