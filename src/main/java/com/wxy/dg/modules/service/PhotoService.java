package com.wxy.dg.modules.service;

import java.util.ArrayList;
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

import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.common.util.DictUtils;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.modules.dao.OrgDao;
import com.wxy.dg.modules.dao.PhotoDao;
import com.wxy.dg.modules.model.Photo;

@Service
@Transactional(readOnly = true)
public class PhotoService {

	@Autowired
	private PhotoDao photoDao;
	@Autowired
	private OrgDao orgDao;

	// 根据条件获取照片列表
	public Page<Photo> getPhotos(Page<Photo> page, Map<String, Object> paramMap) {
		DetachedCriteria criteria = photoDao.createDetachedCriteria();
		criteria.createAlias("uploader", "uploader");
		// 组织结构
		String orgId = ObjectUtils.toString(paramMap.get("orgId"));
		if (orgId != "" && orgId != "0") {
			List<Integer> orgIds = new ArrayList<Integer>();
			orgIds.add(Integer.valueOf(orgId));
			for (Integer id : orgDao.findChildList(Integer.valueOf(orgId))) {
				orgIds.add(id);
			}
			criteria.add(Restrictions.in("uploader.organization.id", orgIds));
		}
		// 类别
		String category = ObjectUtils.toString(paramMap.get("category"));
		if (StringUtils.isNotBlank(category)) {
			criteria.add(Restrictions.eq("category", category));
		}
		// 开始日期
		String startDate = ObjectUtils.toString(paramMap.get("startDate"));
		if (StringUtils.isNotBlank(startDate)) {
			criteria.add(Restrictions.ge("uploadtime", DateUtils.getDateStart(DateUtils.parseDate(startDate))));
		}
		// 结束日期
		String endDate = ObjectUtils.toString(paramMap.get("endDate"));
		if (StringUtils.isNotBlank(endDate)) {
			criteria.add(Restrictions.lt("uploadtime", DateUtils.getDateEnd(DateUtils.parseDate(endDate))));
		}
		// 未被删除
		criteria.add(Restrictions.eq("del_flag", "0"));
		criteria.addOrder(Order.desc("uploadtime"));

		Page<Photo> photos = photoDao.find(page, criteria);
		for (Photo photo : photos.getList()) {
			photo.setCategory_name(DictUtils.getCodeName(photo.getCategory(), "CATEGORY"));
		}
		return photos;
	}

	// 根据条件获取照片列表
	public List<Photo> getPhotos(int userId) {
		List<Photo> photos = photoDao.findPhotosByUser(userId);
		for (Photo photo : photos) {
			photo.setCategory_name(DictUtils.getCodeName(photo.getCategory(), "CATEGORY"));
		}
		return photos;
	}

	// 根据图片ID获取照片
	public Photo getPhoto(int photoId) {
		Photo photo = photoDao.get(photoId);
		photo.setCategory_name(DictUtils.getCodeName(photo.getCategory(), "CATEGORY"));
		return photo;
	}

	@Transactional(readOnly = false)
	public void save(Photo entity) {
		photoDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(int id) {
		photoDao.deleteById(id);
	}

}