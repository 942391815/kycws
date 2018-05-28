package com.wxy.dg.modules.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wxy.dg.modules.security.SystemAuthorizingRealm;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.util.CacheUtils;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.dao.OrgDao;
import com.wxy.dg.modules.dao.UserDao;
import com.wxy.dg.modules.model.User;

@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private OrgDao orgDao;

	public Page<User> findUser(Page<User> page, User user) {
		DetachedCriteria criteria = userDao.createDetachedCriteria();
//		criteria.createAlias("organization", "organization");
		// 组织机构
		if (user.getOrganization() != null && !"0".equals(user.getOrganization().getId())) {
			List<Integer> orgIds = new ArrayList<Integer>();
//			int orgId = user.getOrganization().getId();
//			orgIds.add(orgId);
//			for (Integer id : orgDao.findChildList(orgId)) {
//				orgIds.add(id);
//			}
//			criteria.add(Restrictions.in("organization.id", orgIds));
		}
		// 姓名
		if (StringUtils.isNotBlank(user.getName())) {
			criteria.add(Restrictions.like("name", user.getName(), MatchMode.ANYWHERE));
		}
		//如果类型为高级监督员则只能查找原创守护者
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		User loginUser = UserUtils.get(principal.getId());

		if(loginUser.getUser_type()!=null&&"101".equals(loginUser.getUser_type())){
			criteria.add(Restrictions.eq("user_type", "103"));
		}

		criteria.add(Restrictions.eq("del_flag", Constant.NotDeleteFlg));
		if(StringUtils.isEmpty(page.getOrderBy())) {
			criteria.addOrder(Order.asc("organization.id")).addOrder(Order.asc("login_name"));
		}
		
		return userDao.find(page, criteria);
	}
	
	// 根据用户登录名取得用户信息
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}

	// 取得某个组织下的所有人员
	@SuppressWarnings("unchecked")
	public List<User> findUsersByOrg(int orgId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_ORG_ID + orgId);
		if (list == null || list.size() == 0){
			DetachedCriteria criteria = userDao.createDetachedCriteria();
			if (!"0".equals(orgId)) {
				List<Integer> orgIds = new ArrayList<Integer>();
				orgIds.add(orgId);
				for (Integer id : orgDao.findChildList(orgId)) {
					orgIds.add(id);
				}
				criteria.add(Restrictions.in("organization.id", orgIds));
			}
			criteria.add(Restrictions.eq("del_flag", Constant.NotDeleteFlg));
			list = userDao.find(criteria);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_ORG_ID + orgId, list);
		}
		return list;
	}

	public List<User> getAllValid() {
		return userDao.findAll();
	}
	
	@Transactional(readOnly = false)
	public void save(User user) {
		userDao.clear();
		Date date = new Date();
		if(user.getId() == 0) {
			user.setCreate_date(date);
		}
		user.setUpdate_date(date);
		userDao.save(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(int id) {
		userDao.updateLoginInfo(new Date(), id);
	}
	
	public User getUser(int id) {
		return UserUtils.get(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(int id) {
		userDao.deleteById(id);
		// 清除用户缓存
		UserUtils.clearCache(getUser(id));
	}

}