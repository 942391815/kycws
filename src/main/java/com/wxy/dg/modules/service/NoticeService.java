package com.wxy.dg.modules.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxy.dg.common.util.Page;
import com.wxy.dg.modules.dao.NoticeDao;
import com.wxy.dg.modules.model.Notice;

@Service
@Transactional(readOnly = true)
public class NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;

	public List<Notice> getUserNotices(int userId) {
		List<Notice> list = noticeDao.findByUser(userId);
		return list;
	}
	
	public Page<Notice> findNotice(Page<Notice> page) {
		DetachedCriteria criteria = noticeDao.createDetachedCriteria();
		if(StringUtils.isEmpty(page.getOrderBy())) {
			criteria.addOrder(Order.desc("send_time"));
		}
		return noticeDao.find(page, criteria);
	}
	
	public Notice getById(int id) {
		return noticeDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Notice entity) {
		noticeDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Notice entity) {
		noticeDao.delete(entity);
	}

}