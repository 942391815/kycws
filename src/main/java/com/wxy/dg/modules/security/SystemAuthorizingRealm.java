package com.wxy.dg.modules.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.util.Encodes;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.SpringBeanService;
import com.wxy.dg.modules.service.UserService;

/**
 * 系统安全认证实现类
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private UserService userService;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 校验用户名密码
		User user = getUserService().getUserByLoginName(token.getUsername());
		if (user != null) {
			// PC端非系统管理员不能登录
			if (!Constant.USER_TYPE_ADMIN.equals(user.getUser_type())) {
//				throw new AuthenticationException("非系统管理员不能登录");
			}
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
			return new SimpleAuthenticationInfo(new Principal(user), user.getPassword().substring(16),
					ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		User user = getUserService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 更新登录时间
			getUserService().updateUserLoginInfo(user.getId());
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
		matcher.setHashIterations(1024);
		setCredentialsMatcher(matcher);
	}

	public UserService getUserService() {
		if (userService == null) {
			userService = SpringBeanService.getBean(UserService.class);
		}
		return userService;
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private int id; // 编号
		private String loginName; //登录名
		private String name; //姓名
		private Map<String, Object> cacheMap;

		public Principal(User user) {
			this.id = user.getId();
			this.loginName = user.getLogin_name();
			this.name = user.getName();
		}

		public int getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public Map<String, Object> getCacheMap() {
			if (cacheMap == null) {
				cacheMap = new HashMap<String, Object>();
			}
			return cacheMap;
		}

	}
}