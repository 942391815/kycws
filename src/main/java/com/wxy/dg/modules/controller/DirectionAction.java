package com.wxy.dg.modules.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.config.Global;

/**
 * 使用说明
 */
@Controller
public class DirectionAction extends BaseAction {

	@RequestMapping("/mobile/searchDirection")
	public void doPost(HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response", "direction");
		map.put("list", getDirectionList());
		toJsonString(response, map);
	}

	private List<Map<String, String>> getDirectionList() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String tu = Global.getDirection();

		String[] tus = null;
		if (StringUtils.isNotEmpty(tu)) {
			tus = tu.split(";");
		}

		Map<String, String> titleUrl = null;
		for (String string : tus) {
			titleUrl = new HashMap<String, String>();
			titleUrl.put("title", string.split(",")[0]);
			titleUrl.put("url", string.split(",")[1]);
			list.add(titleUrl);
		}
		return list;
	}
}
