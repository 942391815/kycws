package com.wxy.dg.common.constant;


public interface Constant {

	// 数据是否删除标识('0':数据有效 '1':数据删除)
	public static final String NotDeleteFlg = "0";
	public static final String IsDeleteFlg = "1";
	
	// 用户类型-系统管理员
	public static final String USER_TYPE_ADMIN = "100";
	// 图片缩略图前缀
	public static final String PREFIX_LIT = "lit_";



	public static final int RESPONSE_SUCCESS=200;
	public static final int RESPONSE_FAIL=500;

	int MAX_ENTERPRISE_EACHDAY=200;

}