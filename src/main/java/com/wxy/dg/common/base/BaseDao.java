package com.wxy.dg.common.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import com.wxy.dg.common.util.Page;
import com.wxy.dg.common.util.Reflections;


/**
 * DAO支持类实现
 * @param <T>
 */
public class BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 实体类类型(由构造方法自动赋值)
	 */
	private Class<T> entityClass;
	
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public BaseDao() {
		entityClass = Reflections.getClassGenricType(getClass());
	}

	/**
	 * 获取 Session
	 */
	public Session getSession(){  
	  return sessionFactory.getCurrentSession();
	}

	/**
	 * 清除缓存数据
	 */
	public void clear(){ 
		getSession().clear();
	}
	
	/**
	 * 设置查询参数
	 * @param query
	 * @param parameter
	 */
	private void setParameter(Query query, Object... parameter){
		if(parameter != null) {
			for(int i = 0;i < parameter.length;i++) {
				query.setParameter(i, parameter[i]);
			}
		}
	}
	
	/**
	 * 创建 QL 查询对象
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	public Query createQuery(String qlString, Object... parameter){
		Query query = getSession().createQuery(qlString);
		setParameter(query, parameter);
		return query;
	}

    /**
	 * QL 查询
	 * @param qlString
	 */
	public <E> List<E> find(String qlString){
		return find(qlString, new Object[]{});
	}
	
    /**
	 * QL 查询
	 * @param qlString
	 * @param parameter
	 */
	public <E> List<E> find(String qlString, Object... parameter){
		Query query = createQuery(qlString, parameter);
		return query.list();
	}
	
	/**
	 * 获取实体
	 * @param id
	 */
	public T get(Serializable id){
		return (T)getSession().get(entityClass, id);
	}
	
	/**
	 * 获取实体
	 * @param qlString
	 */
	public T getByHql(String qlString){
		return getByHql(qlString, new Object[]{});
	}
	
	/**
	 * 获取实体
	 * @param qlString
	 * @param parameter
	 */
	public T getByHql(String qlString, Object... parameter){
		Query query = createQuery(qlString, parameter);
		return (T)query.uniqueResult();
	}
	
	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	/**
	 * 更新
	 * @param qlString
	 * @return
	 */
	public void update(String qlString){
		update(qlString, new Object[]{});
	}
	
	/**
	 * 更新
	 * @param qlString
	 * @param parameter
	 */
	public void update(String qlString, Object... parameter) {
		createQuery(qlString, parameter).executeUpdate();
	}
	
	/**
	 * 物理删除
	 * @param entity
	 */
	public void delete(Object entity){
		getSession().delete(entity);
	}
	
	/**
	 * 逻辑删除
	 * @param id
	 */
	public void deleteById(Serializable id){
		update("update "+entityClass.getSimpleName()+" set del_flag='1' where id = ?", id);
	}

	/**
	 * 使用检索标准对象查询
	 * @param detachedCriteria
	 */
	public List<T> find(DetachedCriteria detachedCriteria) {
		return find(detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	/**
	 * 使用检索标准对象查询
	 * @param detachedCriteria
	 * @param resultTransformer
	 * @return
	 */
	public List<T> find(DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		criteria.setResultTransformer(resultTransformer);
		return criteria.list(); 
	}

	/**
	 * 创建与会话无关的检索标准对象
	 * @param criterions Restrictions.eq("name", value);
	 * @return 
	 */
	public DetachedCriteria createDetachedCriteria(Criterion... criterions) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		for (Criterion c : criterions) {
			dc.add(c);
		}
		return dc;
	}
	
	
	/**
	 * 创建 SQL 查询对象
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public SQLQuery createSqlQuery(String sqlString, Object... parameter){
		SQLQuery query = getSession().createSQLQuery(sqlString);
		setParameter(query, parameter);
		return query;
	}
	
	/**
	 * SQL 查询
	 * @param sqlString
	 * @return
	 */
	public List findBySql(String sqlString){
		return findBySql(sqlString, new Object[]{});
	}
	
	/**
	 * SQL 查询
	 * @param sqlString
	 * @param parameter
	 */
	public List findBySql(String sqlString, Object... parameter){
		SQLQuery query = createSqlQuery(sqlString, parameter);
		return query.list();
	}
	
	/**
	 * SQL 更新
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public int updateBySql(String sqlString, Object... parameter){
		return createSqlQuery(sqlString, parameter).executeUpdate();
	}
	
	/**
	 * 使用检索标准对象分页查询
	 * @param page
	 * @param detachedCriteria
	 * @return
	 */
	public Page<T> find(Page<T> page, DetachedCriteria detachedCriteria) {
		return find(page, detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	/**
	 * 使用检索标准对象分页查询
	 * @param page
	 * @param detachedCriteria
	 * @param resultTransformer
	 * @return
	 */
	public Page<T> find(Page<T> page, DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
		// get count
		if (!page.isDisabled()){
			page.setCount(count(detachedCriteria));
			if (page.getCount() < 1) {
				return page;
			}
		}
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		criteria.setResultTransformer(resultTransformer);
		// set page
		if (!page.isDisabled()){
	        criteria.setFirstResult(page.getFirstResult());
	        criteria.setMaxResults(page.getMaxResults()); 
		}
		// order by
		String order = page.getOrderBy();
		if (StringUtils.isNotBlank(order)){
			String[] o = StringUtils.split(order, " ");
			if ("DESC".equals(o[1].toUpperCase())){
				criteria.addOrder(Order.desc(o[0]));
			}else{
				criteria.addOrder(Order.asc(o[0]));
			}
		}
		page.setList(criteria.list());
		return page;
	}
	
	/**
	 * 使用检索标准对象查询记录数
	 * @param detachedCriteria
	 * @return
	 */
	public int count(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		int totalCount = 0;
		try {
			// Get orders
			Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			List orderEntrys = (List)field.get(criteria);
			// Remove orders
			field.set(criteria, new ArrayList());
			// Get count
			criteria.setProjection(Projections.rowCount());
			totalCount = Integer.valueOf(criteria.uniqueResult().toString());
			// Clean count
			criteria.setProjection(null);
			// Restore orders
			field.set(criteria, orderEntrys);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return totalCount;
	}

}