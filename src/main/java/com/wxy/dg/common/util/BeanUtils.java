package com.wxy.dg.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by test on 2016/12/8.
 */
public class BeanUtils {
    public static <T>  T mapToObj(Map<String,String> map, Class<T> clazz){
        String requestJson = JSONObject.toJSONString(map);
        return JSONObject.parseObject(requestJson, clazz);
    }
}
