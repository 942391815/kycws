package com.wxy.dg.modules.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.SubInfoService;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.security.SystemAuthorizingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.config.Global;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.common.util.excel.WriteExcelUtil;
import com.wxy.dg.modules.model.Attendance;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.AttendanceService;

/**
 * 考勤管理
 */
@Controller
public class AttendanceAction extends BaseAction {

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private SubInfoService subInfoService;

	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			Page<Attendance> page = attendanceService.findAttendance(new Page<Attendance>(request, response,-1), paramMap);
			List<Attendance> list = page.getList();
			// excel列信息
			List<String> headers = new ArrayList<String>();
			// 林场
			headers.add("林场");
			// 人员
			headers.add("人员");
			// 登录名
			headers.add("登录名");
			// 日期
			List<String> dateList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				if (!dateList.contains(list.get(i).getDate())) {
					dateList.add(list.get(i).getDate());
				}
			}
			for (int i = 0; i < dateList.size(); i++) {
				headers.add(dateList.get(i));
			}
			// 出勤天数
			headers.add("出勤天数");

			// excel行信息
			List<List<String>> infos = new ArrayList<List<String>>();
			List<User> userList = new ArrayList<User>();
			for (Attendance e : list) {
				User user = e.getUser();
				if (!userList.contains(user)) {
					userList.add(user);
				}
			}

			for (User user : userList) {
				List<String> rowInfo = new ArrayList<String>();
				// 林场
				rowInfo.add(user.getOrganization().getName());
				// 人员
				rowInfo.add(user.getName());
				// 登录名
				rowInfo.add(user.getLogin_name());
				int id = user.getId();
				// 该人员所有的考勤信息
				List<Attendance> list2 = new ArrayList<Attendance>();
				// 该人员所有的考勤时间
				List<String> dateList2 = new ArrayList<String>();
				int distanceTarget = Integer.valueOf(Global.getDistanceTarget());
				int count = 0;
				for (Attendance e : list) {
					if (id == e.getUser().getId()) {
						dateList2.add(e.getDate());
						list2.add(e);
						if (e.getDistance() >= distanceTarget) {
							count++;
						}
					}
				}
				// excel每行具体信息
				for (int i = 0; i < dateList.size(); i++) {
					String date = dateList.get(i);
					if (dateList2.contains(date)) {
						for (Attendance e : list2) {
							if (e.getDate().equals(date)) {
								// 巡视距离
								rowInfo.add(String.valueOf(e.getDistance()));
							}
						}
					} else {
						rowInfo.add("0");
					}
				}
				// 合计考勤天数
				rowInfo.add(String.valueOf(count));
				infos.add(rowInfo);
			}
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date());
			String fileName = "attendance_" + timestamp + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName + ".xls");
			OutputStream os = response.getOutputStream();
			WriteExcelUtil.writeExcel(os, headers, infos);
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！");
			logger.error("导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:/attendance";
	}
}