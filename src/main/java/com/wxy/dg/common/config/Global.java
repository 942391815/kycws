package com.wxy.dg.common.config;

import java.util.HashMap;
import java.util.Map;

import com.wxy.dg.common.util.PropertiesLoader;

/**
 * 全局配置类
 */
public class Global {
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("forestry.properties");
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}

	/**
	 * 获取巡视的距离目标
	 */
	public static String getDistanceTarget() {
		return getConfig("distanceTarget");
	}
	
	/**
	 * 获取照片url地址
	 */
	public static String getPhotoUrl() {
		return getConfig("photoUrl");
	}
	
	/**
	 * 获取照片缩略图所在文件夹
	 */
	public static String getLitImgFolder() {
		return getConfig("litImgFolder");
	}
	
	/**
	 * 获取使用说明
	 */
	public static String getDirection() {
		return getConfig("direction");
	}
	
	/**
	 * 获取百度云推送apiKey
	 */
	public static String getApiKey() {
		return getConfig("apiKey");
	}
	
	/**
	 * 获取百度云推送secretKey
	 */
	public static String getSecretKey() {
		return getConfig("secretKey");
	}
	
	
}