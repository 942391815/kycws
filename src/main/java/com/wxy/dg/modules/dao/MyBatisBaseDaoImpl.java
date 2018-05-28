package com.wxy.dg.modules.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @description: mybatis DAO 通用工具类
 * @version  Ver 1.0
 * @since    Ver 1.0
 * @Date	 2012	2012-8-3		下午4:40:35
 *
 */
@Component(value="myBatisBaseDao")
public class MyBatisBaseDaoImpl<T, PK extends Serializable>   implements  MyBatisBaseDao<T, PK> {

    private static Logger logger = Logger.getLogger(MyBatisBaseDaoImpl.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 插入
     */
    public String INSERT = ".insert";

    /**
     * 批量插入
     */
    public String INSERT_BATCH = ".insertBatch";

    /**
     * 更新
     */
    public String UPDATE = ".update";

    /**
     * 根据ID 删除
     */
    public String DELETE = ".delete";

    /**
     * 根据ID 查询
     */
    public String GETBYID = ".getById";

    /**
     * 根据条件 分页查询
     */
    public String COUNT = ".findPage_count";
    /**
     * 根据条件 分页查询
     */
    public String PAGESELECT = ".findPage";

    private Object target;

    private Method invokingMethod;


    @Override
    public int save(T object) {
        if (object == null) {
            throw new RuntimeException(" object can't null!");
        }
        SqlSession session = sqlSessionFactory.openSession(false);
        return session.insert(object.getClass().getName() + INSERT, object);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCondition(T obj) {
        if(obj == null) {
            throw new RuntimeException(" condition can't null!");
        }
        SqlSession session = sqlSessionFactory.openSession();
        return (List<T>) session.selectList(obj.getClass().getName()+ PAGESELECT, obj);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCondition(T obj, int offset, int limit) {
        if(obj == null) {
            throw new RuntimeException(" condition can't null!");
        }
        SqlSession session = sqlSessionFactory.openSession();
        RowBounds rb = new RowBounds(offset, limit);
        return (List<T>) session.selectList(obj.getClass().getName()+PAGESELECT, obj, rb);
    }

//    @Override
//    public List<T> findByCondition(T obj, Pager pager) {
//        if(obj == null) {
//            throw new RuntimeException(" condition can't null!");
//        }
//        if (pager != null) {
//            return this.findByCondition(obj,pager.getStartRow(),pager.getPageSize());
//        } else {
//            return this.findByCondition(obj);
//        }
//    }

    @SuppressWarnings("unchecked")
    @Override
    public T findByPK(PK pk, Class<T> cls) {
        if(pk == null) {
            throw new RuntimeException(" pk can't null!");
        }
        SqlSession session = sqlSessionFactory.openSession();
        return (T) session.selectOne(cls.getName()+GETBYID, pk);

    }

    @Override
    public void update(T object) {
        if(object == null) {
            throw new RuntimeException(" object can't null!");
        }
            SqlSession session = sqlSessionFactory.openSession();
            session.update(object.getClass().getName()+ UPDATE,object);
    }

    @Override
    public void delete(PK pk, Class<T> cls) {
        if(pk == null) {
            throw new RuntimeException(" pk can't null!");
        }
            SqlSession session = sqlSessionFactory.openSession(false);
            session.delete(cls.getName()+DELETE, pk);
        }

    @Override
    public Integer getTotalCount(T object) {
        if(object == null) {
            throw new RuntimeException(" condition can't null!");
        }
        SqlSession session = sqlSessionFactory.openSession(false);
        Object obj = session.selectOne(object.getClass().getName()+COUNT, object);
        if (obj != null) {
            return Integer.parseInt(obj.toString());
        }
        return 0;
    }


    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}

