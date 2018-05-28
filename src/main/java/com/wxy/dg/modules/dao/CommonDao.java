package com.wxy.dg.modules.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.modules.model.SysCode;

@Repository
public class CommonDao extends BaseDao<SysCode> {

	public List<SysCode> findAllList(){
		return find("from SysCode order by code_order");
	}
}