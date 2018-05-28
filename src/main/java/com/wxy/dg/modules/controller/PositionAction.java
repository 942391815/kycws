package com.wxy.dg.modules.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.util.CommonUtil;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.modules.service.PositionService;

/**
 * 位置信息上传
 */
@Controller
public class PositionAction extends BaseAction {
	
	@Autowired
	private PositionService positionService;

	@RequestMapping("/mobile/position")
	public void uploadPos(HttpServletRequest request, HttpServletResponse response, Integer userId) {
		String latlons = request.getParameter("latLons");
		if(StringUtils.isEmpty(latlons)) {
			logger.info("位置信息为空的用户ID:"+userId);
			return;
		}
		JSONArray latlonsArray = JSON.parseArray(latlons);
		for (int i = 0; i < latlonsArray.size(); i++) {
			JSONObject obj = latlonsArray.getJSONObject(i);
			if(!CommonUtil.isLatitude(obj.getString("latitude"))) {
				logger.info("用户ID:"+userId+",纬度:"+obj.getString("latitude"));
				continue;
			}
			positionService.insert(obj.getDouble("longitude"), obj.getDouble("latitude"),
					DateUtils.parseDate(obj.getString("time")), userId);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "0");
		toJsonString(response, map);
	}
}