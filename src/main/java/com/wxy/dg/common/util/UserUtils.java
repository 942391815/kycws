package com.wxy.dg.common.util;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.wxy.dg.modules.dao.OrgDao;
import com.wxy.dg.modules.dao.UserDao;
import com.wxy.dg.modules.model.Organization;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.security.SystemAuthorizingRealm.Principal;
import com.wxy.dg.modules.service.SpringBeanService;

/**
 * 用户工具类
 */
public class UserUtils {

	private static UserDao userDao = SpringBeanService.getBean(UserDao.class);
	private static OrgDao orgDao = SpringBeanService.getBean(OrgDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID = "id_";
	public static final String USER_CACHE_LOGIN_NAME = "ln";
	public static final String USER_CACHE_LIST_BY_ORG_ID = "oid_";
	public static final String CACHE_ORG_LIST = "orgList";

	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(int id){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID + id);
		if (user ==  null){
			user = userDao.get(id);
			if (user == null){
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME + user.getLogin_name(), user);
		}
		return user;
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME + loginName);
		if (user == null){
			user = userDao.findByLoginName(loginName);
			if (user == null){
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME + user.getLogin_name(), user);
		}
		return user;
	}

	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME + user.getLogin_name());
		if (user.getOrganization() != null && user.getOrganization().getId() != 0){
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_ORG_ID + user.getOrganization().getId());
		}
	}

	public static List<Organization> getOrgList(){
		@SuppressWarnings("unchecked")
		List<Organization> orgList = (List<Organization>)getCache(CACHE_ORG_LIST);
		if (orgList == null || orgList.size() == 0){
			orgList = orgDao.findAllValid();
			putCache(CACHE_ORG_LIST, orgList);
		}
		return orgList;
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Object getCache(String key) {
		return getSession().getAttribute(key);
	}
	
	public static void putCache(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		getSession().removeAttribute(key);
	}
	
}