package com.wxy.dg.modules.dao;

import java.io.Serializable;
import java.util.List;

/**
 * MyBatis DAO通用操作类
 * @author LDJ
 * @param <T>
 * @param <PK>
 * @Date 2007-07-02
 */
public interface MyBatisBaseDao<T, PK extends Serializable>{
    /**
     * 增加实体
     *
     * @param object
     */
    public int save(T object);



    /**
     * 按条件查询实体
     *
     * @param obj
     * @return
     */
    public List<T> findByCondition(T obj);

    /**
     * 按条件查询实体并分页
     *
     * @param obj
     * @return
     */
    public List<T> findByCondition(T obj, int start, int limit);

    /**
     *
     * @description 分页查询
     * @param   obj 查询对象
     * @param   pager 分页对象
     * @return  查询结果
     * @throws
     */
//    public List<T> findByCondition(T obj,Pager pager);

    /**
     * 按主键查询
     *
     * @param pk
     * @return
     */
    public T findByPK(PK pk, Class<T> cls);

    /**
     * 更新实体
     *
     * @param object
     */
    public void update(T object);

    /**
     * 按主键删除实体
     *
     * @param pk
     */
    public void delete(PK pk, Class<T> cls);


    /**
     * 按条件查询总记录数
     *
     * @param object
     * @return
     */
    public Integer getTotalCount(T object);


}

