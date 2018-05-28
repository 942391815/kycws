package com.wxy.dg.modules.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.modules.model.Photo;

@Repository
public class PhotoDao extends BaseDao<Photo> {

	public List<Photo> findPhotosByUser(int userId) {
		List<Photo> photoList = find(
				"from Photo where del_flag = '0' and uploader.id = ? order by uploadtime desc",
				userId);
		return photoList;
	}

}