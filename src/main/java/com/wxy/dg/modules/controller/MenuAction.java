package com.wxy.dg.modules.controller;

import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.security.SystemAuthorizingRealm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wxy.dg.common.base.BaseAction;

import javax.servlet.http.HttpServletRequest;

/**
 * 左侧菜单导航
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuAction extends BaseAction {
	
	@RequestMapping(value = "tree")
	public String tree(HttpServletRequest request, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		User user = UserUtils.get(principal.getId());
		if("100".equals(user.getUser_type())){
			model.addAttribute("isShow",1);
		}
		model.addAttribute("userType",user.getUser_type());
		return "modules/left";
	}

}