package com.wxy.dg.modules.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.util.CommonUtil;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.security.SystemAuthorizingRealm.Principal;
import com.wxy.dg.modules.service.UserService;

/**
 * 系统登录
 */
@Controller
public class LoginAction extends BaseAction {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "sysLogin", method = RequestMethod.GET)
	public String login() {
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if(principal != null && principal.getId() != 0){
			return "redirect:/index";
		}
		return "modules/login";
	}
	
	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "sysLogin", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, Model model) {
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if(principal != null && principal.getId() != 0){
			return "redirect:/index";
		}
		model.addAttribute("username", request.getParameter("username"));
		return "modules/login";
	}
	
	/**
	 * 登录成功，进入首页
	 */
	@RequiresUser
	@RequestMapping(value = "index")
	public String index() {
		Principal principal = UserUtils.getPrincipal();
		// 未登录，则跳转到登录页
		if(principal == null || principal.getId() == 0){
			return "redirect:/sysLogin";
		}
		return "modules/index";
	}
	
	/**
	 * 手机端登录
	 * @throws IOException 
	 */
	@RequestMapping({"/mobile/login", "login"})
	public void mobileLogin(HttpServletResponse response, String userName, String password,
			 String phoneImei, @RequestParam(required=false) String sysTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response", "login");
		User user = userService.getUserByLoginName(userName);
		if (user != null && CommonUtil.validatePassword(password, user.getPassword())) {
			if (StringUtils.isNotBlank(phoneImei)) {
				if (StringUtils.isBlank(user.getImei())) {
					user.setImei(phoneImei);
					userService.save(user);
				} else if (!(user.getImei().equals(phoneImei))) {
					map.put("state", "手机与帐号不匹配,请检查!");
					toJsonString(response, map);
					return;
				}
			}
			map.put("state", "0");
			// 手机时间跟服务器时间差别在一小时以上时进行提醒
			if(sysTime != null) {
				if(Math.abs(System.currentTimeMillis()-Long.parseLong(sysTime)) >= 60 * 60 * 1000) {
					map.put("tip", "手机时间跟服务器时间差别过大,请调整手机时间");
				}
			}
			map.put("userId", user.getId());
			map.put("orgId", user.getOrganization().getId());
			map.put("frequency", user.getFrequency());
			// 更新登录时间
			userService.updateUserLoginInfo(user.getId());
		} else {
			map.put("state", "用户名或密码错误");
		}
		toJsonString(response, map);
	}
	
}