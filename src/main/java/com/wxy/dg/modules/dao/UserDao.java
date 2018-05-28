package com.wxy.dg.modules.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.modules.model.User;

@Repository
public class UserDao extends BaseDao<User> {

	public User findByLoginName(String loginName) {
		return getByHql("from User where login_name =? and del_flag = '0'", loginName);
	}

	public List<User> findAll() {
		return find("from User where del_flag = '0'");
	}
	
	public void updateLoginInfo(Date loginDate, int id){
		update("update User set login_date=? where id = ?", loginDate, id);
	}

}