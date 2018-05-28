package com.wxy.dg.modules.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxy.dg.modules.dao.PositionDao;
import com.wxy.dg.modules.model.Position;

@Service
@Transactional(readOnly = true)
public class PositionService {
	
	@Autowired
	private PositionDao positionDao;
	
	public List<Position> getPositionInfos(int userId, String queryDate) {
		List<Position> list = positionDao.findUserPositions(userId, queryDate);
		return list;
	}
	
	public List<Position> getPositions() {
		Calendar calendar = Calendar.getInstance(); // 获取系统当前时间
		Date endTime = calendar.getTime(); // 结束时间
		calendar.add(Calendar.MINUTE, -30); // 开始时间
		Date startTime = calendar.getTime();
		List<Position> list = positionDao.findByTime(startTime, endTime);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void insert(double longitude, double latitude, Date locateTime, int userId) {
		positionDao.clear();
		positionDao.save(longitude, latitude, locateTime, userId);
	}

}