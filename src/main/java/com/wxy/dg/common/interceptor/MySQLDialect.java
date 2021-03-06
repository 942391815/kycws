package com.wxy.dg.common.interceptor;

public class MySQLDialect extends Dialect{
    public boolean supportsLimitOffset(){
        return true;
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {
            return sql + " limit "+offsetPlaceholder+","+limitPlaceholder;
        } else {
            return sql + " limit "+limitPlaceholder;
        }
    }
    @Override
    public String getCountString(String sql){
        return "select count(*) len from (" + sql  + ") as a";
    }
}
