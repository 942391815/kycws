package com.wxy.dg.modules.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.modules.model.Position;

@Repository
public class PositionDao extends BaseDao<Position> {

	// 查询巡林员一天的位置列表
	public List<Position> findByEveryday(int userId, Date date) {
		String tbName="position_" + DateUtils.formatDate(date, "MM");
		String sql = "select * from "+tbName+" where user_id = ? and locate_time >= ? and locate_time <= ? order by locate_time desc";
		List<Object[]> result = findBySql(sql, userId, DateUtils.getDateStart(date), DateUtils.getDateEnd(date));
		List<Position> list = new ArrayList<Position>();
		for (Object[] obj : result) {
			Position pos = new Position();
			pos.setLongitude(Double.parseDouble(obj[1].toString()));
			pos.setLatitude(Double.parseDouble(obj[2].toString()));
			pos.setTime((Date)(obj[3]));
			pos.setUserId(Integer.parseInt(obj[4].toString()));
			list.add(pos);
		}
		return list;
	}

	public List<Position> findByTime(Date startTime, Date endTime) {
		String tbName="position_" + DateUtils.getMonth();
		String sql = "select * from "+tbName+" where locate_time >= ? and locate_time <= ? order by locate_time desc";
		List<Object[]> result = findBySql(sql, startTime, endTime);
		List<Position> list = new ArrayList<Position>();
		for (Object[] obj : result) {
			Position pos = new Position();
			pos.setLongitude(Double.parseDouble(obj[1].toString()));
			pos.setLatitude(Double.parseDouble(obj[2].toString()));
			pos.setTime((Date)(obj[3]));
			pos.setUserId(Integer.parseInt(obj[4].toString()));
			list.add(pos);
		}
		return list;
	}
	
	public List<Position> findUserPositions(int userId, String queryDate) {
		String tbName="position_" + queryDate.substring(5,7);
		String sql = "select * from "+tbName+" where user_id = ? and DATE_FORMAT(locate_time,'%Y-%m-%d') = ?";
		List<Object[]> result = findBySql(sql, userId, queryDate);
		List<Position> list = new ArrayList<Position>();
		for (Object[] obj : result) {
			Position pos = new Position();
			pos.setLongitude(Double.parseDouble(obj[1].toString()));
			pos.setLatitude(Double.parseDouble(obj[2].toString()));
			pos.setTime((Date)(obj[3]));
			pos.setUserId(Integer.parseInt(obj[4].toString()));
			list.add(pos);
		}
		return list;
	}
	
	public void save(double longitude, double latitude, Date locateTime, int userId) {
		String tbName="position_" + DateUtils.getMonth();
		String sql = "insert into "+tbName+"(longitude,latitude,locate_time,user_id) values (?,?,?,?)";
		updateBySql(sql, longitude, latitude, locateTime, userId);
	}
}