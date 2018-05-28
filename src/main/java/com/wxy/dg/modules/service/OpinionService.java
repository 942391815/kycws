package com.wxy.dg.modules.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxy.dg.common.util.Page;
import com.wxy.dg.modules.dao.NoticeDao;
import com.wxy.dg.modules.dao.OpinionDao;
import com.wxy.dg.modules.model.Notice;
import com.wxy.dg.modules.model.Opinion;
import com.wxy.dg.modules.model.User;

@Service
@Transactional(readOnly = true)
public class OpinionService {

	@Autowired
	private OpinionDao opinionDao;
	@Autowired
	private NoticeDao noticeDao;

	@Transactional(readOnly = false)
	public void update(Opinion opinion) {
		//插入意见反馈信息
		opinionDao.save(opinion);
		//插入通知信息
		Notice notice = new Notice();
		User user = opinion.getUser();
		notice.setTitle("回复：" + opinion.getTitle() + "(发送人：" + user.getName() + ")");
		notice.setContent(opinion.getReply());
		notice.setSend_time(new Date());
		notice.setObjIds("," + user.getId() + ",");
		noticeDao.save(notice);
	}
	
	// 获取用户意见
	public List<Opinion> getOpinionsByUser(int userId) {
		List<Opinion> list = opinionDao.findByUser(userId);
		return list;
	}

	public Page<Opinion> findOpinion(Page<Opinion> page, Opinion opinion) {
		DetachedCriteria criteria = opinionDao.createDetachedCriteria();
		criteria.createAlias("user", "user");
		if(opinion.getUser() != null) {
			criteria.add(Restrictions.like("user.name", opinion.getUser().getName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isEmpty(page.getOrderBy())) {
			criteria.addOrder(Order.desc("send_time"));
		}
		return opinionDao.find(page, criteria);
	}
	
	public Opinion getById(int id) {
		return opinionDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Opinion entity) {
		opinionDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Opinion entity) {
		opinionDao.delete(entity);
	}

}