package com.wxy.dg.modules.dao;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.modules.model.Attendance;

@Repository
public class AttendanceDao extends BaseDao<Attendance> {
	
	public Attendance findUserAttendance(int userId, String date) {
		Attendance attendance = getByHql("from Attendance where user.id = ? and date = ?",
				new Object[]{userId, date});
		return attendance;
	}
}