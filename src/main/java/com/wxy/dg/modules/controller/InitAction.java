package com.wxy.dg.modules.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.model.Position;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.PositionService;
import com.wxy.dg.modules.service.UserService;

@Controller
public class InitAction extends BaseAction {

	@Autowired
	private PositionService positionService;
	@Autowired
	private UserService userService;

	/**
	 * 人员状态信息初始化
	 */
	@RequestMapping(value = "init")
	public String init() {
		List<Position> posList = positionService.getPositions();
		List<User> onLineUsers = new ArrayList<User>();
		List<Integer> onLineUserIds = new ArrayList<Integer>();
		List<Position> posList2 = new ArrayList<Position>();
		for(Position pos:posList) {
			User user = userService.getUser(pos.getUserId());
			if(user != null && !onLineUserIds.contains(user.getId())) {
				onLineUsers.add(user);
				onLineUserIds.add(user.getId());
				posList2.add(pos);
			}
		}
		// 按组织机构排序
		Collections.sort(onLineUsers);
		
		List<User> allUsers = userService.getAllValid();
		List<User> offLineUsers = new ArrayList<User>();
		for(User user:allUsers) {
			if(!onLineUserIds.contains(user.getId())) {
				offLineUsers.add(user);
			}
		}
		// 按组织机构排序
		Collections.sort(offLineUsers);

		Session session = UserUtils.getSession();
		//当前在线人员位置信息
		session.setAttribute("posList", posList2);
		//当前在线人员信息
		session.setAttribute("onLineUsers", onLineUsers);
		session.setAttribute("onlineCount",onLineUsers.size());
		//当前离线人员信息
		session.setAttribute("offLineUsers", offLineUsers);
		session.setAttribute("offlineCount", offLineUsers.size());
		return "modules/mainMap";
	}
	
	/**
	 * 轨迹查询中间页面
	 */
	@RequestMapping(value = "pos")
	public String pos() {
		return "modules/posMap";
	}
	
	/**
	 * 检测更新
	 * 
	 * @throws IOException 
	 */
	@RequestMapping({"/mobile/update", "update"})
	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/static/androidupdate.html");
	}
	
	/**
	 * 百度云推送(用户ID跟设备绑定)
	 * @param response
	 * 
	 * @param userId 用户ID
	 * @param deviceType 设备类型
	 * @param channelId 推送通道ID
	 */
	@RequestMapping(value = "/mobile/pushbind")
	public void pushbind(HttpServletResponse response, String userId,
			String deviceType, @RequestParam(required = false) String channelId) {
		User user = userService.getUser(Integer.valueOf(userId));
		if (StringUtils.isNotBlank(channelId)
				&& (!channelId.equals(user.getChannelId()))) {
			user.setDeviceType(deviceType);
			user.setChannelId(channelId);
			userService.save(user);
		}
	}

}