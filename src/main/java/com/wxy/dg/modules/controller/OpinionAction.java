package com.wxy.dg.modules.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.util.CommonUtil;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.modules.model.Opinion;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.OpinionService;

/**
 * 意见汇总
 */
@Controller
public class OpinionAction extends BaseAction {

	@Autowired
	private OpinionService opinionService;

	@ModelAttribute
	public Opinion get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id) && !"0".equals(id)){
			return opinionService.getById(Integer.parseInt(id));
		}else{
			return new Opinion();
		}
	}
	
	@RequestMapping("/opinion")
	public String list(Opinion opinion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Opinion> page = opinionService.findOpinion(new Page<Opinion>(request, response), opinion);
		model.addAttribute("page", page);
		return "modules/opinionList";
	}
	
	@RequestMapping("/opinion/manage")
	public String form(Opinion opinion, Model model) {
		model.addAttribute("opinion", opinion);
		return "modules/opinionManage";
	}
	
	@RequestMapping("/opinion/reply")
	public String reply(Opinion opinion, RedirectAttributes redirectAttributes) {
		opinion.setReply_time(new Date());
		List<User> pushUsers = new ArrayList<User>();
		pushUsers.add(opinion.getUser());
		//通知推送
		CommonUtil.pushMsg("回复：" + opinion.getTitle(), opinion.getReply(), pushUsers);
		addMessage(redirectAttributes, "意见已回复。");
		opinionService.update(opinion);
		return "redirect:/opinion";
	}
	
	@RequestMapping("/opinion/delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		opinionService.delete(opinionService.getById(Integer.valueOf(id)));
		addMessage(redirectAttributes, "删除意见成功");
		return "redirect:/opinion";
	}
	
	@RequestMapping("/mobile/searchOpinion")
	public void searchOpinion(@RequestParam String userId, HttpServletResponse response) {
		List<Opinion> opinionlist = opinionService
				.getOpinionsByUser(Integer.valueOf(userId));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Opinion opinion : opinionlist) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("title", opinion.getTitle());
			valueMap.put("content", opinion.getContent());
			valueMap.put("date",DateUtils.formatDateTime(opinion.getSend_time()));
			list.add(valueMap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response", "opinion");
		map.put("opinions", list);
		toJsonString(response, map);
	}
	
	@RequestMapping("/mobile/sendOpinion")
	public void sendOpinion(HttpServletResponse response, @RequestParam String userId,
			@RequestParam String title, @RequestParam String content) {
		Opinion opinion = new Opinion();
		User user = new User();
		user.setId(Integer.valueOf(userId));
		opinion.setUser(user);
		opinion.setTitle(title);
		opinion.setContent(content);
		opinion.setSend_time(new Date());
		opinionService.save(opinion);
		Map<String, String> map = new HashMap<String, String>();
		map.put("response", "opinion");
		map.put("state", "0");
		toJsonString(response, map);
	}

}