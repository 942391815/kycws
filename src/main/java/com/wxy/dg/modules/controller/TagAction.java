package com.wxy.dg.modules.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wxy.dg.common.base.BaseAction;

/**
 * 标签
 */
@Controller
@RequestMapping("/tag")
public class TagAction extends BaseAction {

	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequestMapping("treeselect")
	public String treeselect(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getParameter("url")); // 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("childUrl", request.getParameter("childUrl")); // 异步加载子节点时的url
		model.addAttribute("childParam", request.getParameter("childParam")); // 异步加载子节点时的参数
		return "modules/tagTreeselect";
	}
}