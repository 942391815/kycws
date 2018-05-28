package com.wxy.dg.modules.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxy.dg.common.config.Global;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.modules.dao.AttendanceDao;
import com.wxy.dg.modules.dao.OrgDao;
import com.wxy.dg.modules.dao.PositionDao;
import com.wxy.dg.modules.dao.UserDao;
import com.wxy.dg.modules.model.Attendance;
import com.wxy.dg.modules.model.Position;
import com.wxy.dg.modules.model.User;

@Service
@Transactional(readOnly = true)
public class AttendanceService {

	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrgDao orgDao;

	public Page<Attendance> findAttendance(Page<Attendance> page, Map<String, Object> paramMap) {
		DetachedCriteria criteria = attendanceDao.createDetachedCriteria();
		criteria.createAlias("user", "user");
		// 组织结构
		String orgId = ObjectUtils.toString(paramMap.get("orgId"));
		if(orgId != "" && orgId != "0") {
			List<Integer> orgIds = new ArrayList<Integer>();
			orgIds.add(Integer.valueOf(orgId));
			for (Integer id : orgDao.findChildList(Integer.valueOf(orgId))) {
				orgIds.add(id);
			}
			criteria.add(Restrictions.in("user.organization.id", orgIds));
		}
		String userId = ObjectUtils.toString(paramMap.get("user"));
		if(userId != "" && userId != "0") {
			// 用户
			criteria.add(Restrictions.eq("user.id", Integer.valueOf(userId)));
		}
		// 开始日期
		String startDate = ObjectUtils.toString(paramMap.get("startDate"));
		if (StringUtils.isNotBlank(startDate)) {
			criteria.add(Restrictions.ge("date", startDate));
		}
		// 结束日期
		String endDate = ObjectUtils.toString(paramMap.get("endDate"));
		if (StringUtils.isNotBlank(endDate)) {
			criteria.add(Restrictions.le("date", endDate));
		}
		criteria.add(Restrictions.eq("user.del_flag", "0"));
		if(StringUtils.isEmpty(page.getOrderBy())) {
			criteria.addOrder(Order.desc("date")).addOrder(Order.asc("user.organization.id"));
		}
		
		return attendanceDao.find(page, criteria);
	}
	
	@Transactional(readOnly = false)
	public void attendanceCheck() {
		Calendar calendar = Calendar.getInstance();
		// 得到前一天
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		
		int distanceTarget = Integer.valueOf(Global.getDistanceTarget());
		List<Position> positions = new ArrayList<Position>();
		double distance;
		List<User> users = userDao.findAll();
		for (User u : users) {
			distance = 0;
			// 计算距离
			positions = positionDao.findByEveryday(u.getId(), yesterday);
			for (int i = 0; i < positions.size() - 1; i++) {
				distance += getDistance(positions.get(i)
						.getLatitude(), positions.get(i).getLongitude(),
						positions.get(i + 1).getLatitude(),
						positions.get(i + 1).getLongitude());
			}
			String date = DateUtils.formatDate(yesterday);
			Attendance attendance = attendanceDao.findUserAttendance(u.getId(), date);
			if(attendance == null) {
				attendance = new Attendance();
				attendance.setDate(date);
				attendance.setUser(u);
			}
			attendance.setDistance(distance);
			if (distance >= distanceTarget) {
				attendance.setStatus("是");
			} else {
				attendance.setStatus("否");
			}
			attendanceDao.clear();
			attendanceDao.save(attendance);
		}
	}
	
	private static double EARTH_RADIUS = 6378137;// 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	/**
	 * 根据经纬度算两点距离
	 * 
	 * @param lat1
	 *            纬度1
	 * @param lng1
	 *            经度1
	 * @param lat2
	 *            纬度2
	 * @param lng2
	 *            经度2
	 * @return 两点距离
	 */
	private double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

}