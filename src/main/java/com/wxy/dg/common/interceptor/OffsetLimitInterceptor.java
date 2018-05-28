package com.wxy.dg.common.interceptor;

/**
 * Created by test on 2016/12/10.
 */
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ReflectionUtils;


@Intercepts({@Signature(
        type= Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class OffsetLimitInterceptor implements Interceptor{
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    private boolean isFilterParam = true;

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    Dialect dialect;

    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    void processIntercept(final Object[] queryArgs) {
        //queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
        MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];

        Object parameter = queryArgs[PARAMETER_INDEX];

        if(isFilterParam) {
            parameter = sqlFilter(parameter);
        }

        final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];

        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        if( offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)  {
            BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().trim();
            sql = dialect.getLimitString(sql, offset, limit);
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds();

            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());

            MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql),null);


            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        }else if (queryArgs.length>=4 && queryArgs[RESULT_HANDLER_INDEX]!=null ){
            Object resultHandler = queryArgs[RESULT_HANDLER_INDEX];
                BoundSql boundSql = ms.getBoundSql(parameter);
                String sql = boundSql.getSql().trim();
                queryArgs[ROWBOUNDS_INDEX] = new RowBounds();

                BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
                ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(
                        ms.getConfiguration(),
                        ms.getId() + "-InlineCount.RestulMapper",
                        Integer.class,
                        new ArrayList<ResultMapping>());
                ResultMap rm =inlineResultMapBuilder.build();
                List<ResultMap> list =   new ArrayList<ResultMap>();
                list.add(rm);

                MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql),list);

                queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
//            }
        }


    }
    //see: MapperBuilderAssistant
    private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource,List<ResultMap> resultMap) {
        Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());

        builder.parameterMap(ms.getParameterMap());
        if(resultMap==null){//有自定义的映射结果集就用自己的，否则还用原来配置文件中的
            builder.resultMaps(ms.getResultMaps());
        }else{
            builder.resultMaps(resultMap);
        }
        builder.resultSetType(ms.getResultSetType());
        //setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    /**
     * dejian.liu add
     * @description  SQL 过滤
     * @param parameter
     */
    private  Object sqlFilter(Object parameter) {
        try {
            Class <?> paramType = (parameter == null || parameter.getClass() == null) ? Object.class : parameter.getClass();
            if (paramType != Object.class && !(parameter instanceof Map)) {
                Field [] fields = paramType.getDeclaredFields();
                for (Field field2 : fields) {
                    if (field2.getType() == String.class) {
                        Field field = FieldUtils.getDeclaredField(parameter.getClass(), field2.getName(),true);

                        Object obj = field.get(parameter); //FieldUtils.getProtectedFieldValue(field2.getName(), parameter);
                        if (obj != null) {
                            String paramStr = String.valueOf(obj.toString());
                            if (paramStr.trim().contains("%")) {
                                paramStr = paramStr.replaceAll("%", "\\\\%");
                                field.set(parameter, paramStr);
                            }
                            if (paramStr.trim().contains("'")) {
                                paramStr = paramStr.replaceAll("'", "\\\\'");
                                field.set(parameter, paramStr);
                            }
                        }
                    }
                }
            } else if (parameter != null && parameter instanceof Map) {
                HashMap<String,Object> paramMap = (HashMap<String, Object>)parameter;
                for(Iterator<Map.Entry<String,Object>> it = paramMap.entrySet().iterator();it.hasNext();) {
                    Map.Entry<String,Object> entry = it.next();
                    String key = entry.getKey();
                    Object obj = entry.getValue();
                    if (obj instanceof String) {
                        String par = obj.toString();
                        if (par.trim().contains("%")) {
                            par = par.replaceAll("%", "\\\\%");
                            paramMap.put(key, par);
                        }
                        if (par.trim().contains("'")) {
                            par = par.replaceAll("'", "\\\\'");
                            paramMap.put(key,par);
                        }
                    }
                }

            } else if (parameter != null && parameter instanceof String) {
                String str = (String)parameter;
                if (str.trim().contains("%")) {
                    str = str.replaceAll("%", "\\\\%");
                }
                if (str.trim().contains("'")) {
                    str = str.replaceAll("'", "\\\\'");
                }
                parameter = str;
            }
        }catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }
        return parameter;

    }


    public boolean isFilterParam() {
        return isFilterParam;
    }

    public void setFilterParam(boolean isFilterParam) {
        this.isFilterParam = isFilterParam;
    }

}



