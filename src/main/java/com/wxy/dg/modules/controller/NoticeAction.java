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
import com.wxy.dg.modules.model.Notice;
import com.wxy.dg.modules.model.Organization;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.NoticeService;
import com.wxy.dg.modules.service.OrgService;
import com.wxy.dg.modules.service.UserService;

/**
 * 通知管理
 */
@Controller
public class NoticeAction extends BaseAction {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrgService orgService;
	
	@ModelAttribute
	public Notice get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id) && !"0".equals(id)){
			return noticeService.getById(Integer.parseInt(id));
		}else{
			return new Notice();
		}
	}
	
	@RequestMapping("/noticelist")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Notice> page = noticeService.findNotice(new Page<Notice>(request, response));
		model.addAttribute("page", page);
		return "modules/noticeList";
	}
	
	@RequestMapping("/notice/manage")
	public String form(Model model) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<Organization> list = orgService.getAllValid();
		for (int i=0; i<list.size(); i++){
			Organization e = list.get(i);
			Map<String, Object> map = new HashMap<String, Object> ();
			map.put("id", e.getId());
			map.put("pId", e.getParent() != null ? e.getParent().getId() : 0);
			map.put("name", e.getName());
			map.put("isParent", true);
			mapList.add(map);
		}
		model.addAttribute("orglist",mapList);
		return "modules/noticeManage";
	}
	
	@RequestMapping("/notice/push")
	public String push(Notice notice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, notice)) {
			return form(model);
		}
        String objIds = notice.getObjIds();
		String[] obj = objIds.split(",");
		List<User> pushUsers = new ArrayList<User>();
		for(int i=0;i<obj.length;i++) {
			int userId = Integer.valueOf(obj[i]);
			User user = userService.getUser(userId);
			pushUsers.add(user);
		}
		// 通知推送
		CommonUtil.pushMsg(notice.getTitle(), notice.getContent(), pushUsers);
        notice.setSend_time(new Date());
        notice.setObjIds(","+objIds+",");
    	noticeService.save(notice);
        addMessage(redirectAttributes, "通知已发布,发送对象"+pushUsers.size()+"人!");
        return "redirect:/noticelist";
	}
	
	@RequestMapping("/notice/delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		noticeService.delete(noticeService.getById(Integer.valueOf(id)));
		addMessage(redirectAttributes,"删除通知成功");
		return "redirect:/noticelist";
	}
	
	@RequestMapping("/mobile/notice")
	public void getNoticeList(@RequestParam Integer userId, HttpServletResponse response) {
		List<Notice> noticeList = noticeService.getUserNotices(userId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Notice notice:noticeList) {
			Map<String, Object> noticeMap = new HashMap<String, Object>();
			noticeMap.put("title", notice.getTitle());
			noticeMap.put("content", notice.getContent());
			noticeMap.put("date", DateUtils.formatDate(notice.getSend_time()));
			list.add(noticeMap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response", "notice");
		map.put("notice", list);
		toJsonString(response, map);
	}

}