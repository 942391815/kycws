package com.wxy.dg.modules.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.modules.model.Organization;
import com.wxy.dg.modules.service.OrgService;

/**
 * 组织机构
 */
@Controller
@RequestMapping(value = "/org")
public class OrgAction extends BaseAction{

	@Autowired
	private OrgService orgService;
	
	@ModelAttribute
	public Organization get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id) && !"0".equals(id)) {
			return orgService.getById(Integer.parseInt(id));
		} else {
			return new Organization();
		}
	}
	
	@RequestMapping("")
	public String list(Model model) {
		List<Organization> orgList = orgService.getAllValid();
		List<Organization> list = new ArrayList<Organization>();
		Organization.sortList(list, orgList, 1);
        model.addAttribute("orgList", list);
		return "modules/orgList";
	}
	
	@RequestMapping("manage")
	public String form(Organization org, Model model) {
		if (org.getParent() == null) {
			org.setParent(orgService.getById(1));
		}
		org.setParent(orgService.getById(org.getParent().getId()));
		model.addAttribute("org", org);
		return "modules/orgManage";
	}
	
	@RequestMapping("save")
	public String save(Organization org, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, org)) {
			return form(org, model);
		}
		orgService.save(org);
		addMessage(redirectAttributes, "保存机构'" + org.getName() + "'成功");
		return "redirect:/org";
	}
	
	@RequestMapping("delete")
	public String delete(Integer id, RedirectAttributes redirectAttributes) {
		orgService.deleteOrg(id);
		addMessage(redirectAttributes, "删除机构成功");
		return "redirect:/org";
	}
	
	@RequiresUser
	@RequestMapping("treeData")
	public void treeData(HttpServletResponse response, @RequestParam(required = false) Integer extId,
			@RequestParam(required=false) Boolean isChild) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<Organization> list = orgService.getAllValid();
		for (int i=0; i<list.size(); i++){
			Organization e = list.get(i);
			String parentIds = orgService.findParents(e);
			//选择上级机构时需排除自身以及它的下级机构
			if(extId == null || (!(extId == e.getId()) && parentIds.indexOf(","+extId+",")==-1)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				if(isChild!= null && isChild) {
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		toJsonString(response, mapList);
	}
	
}