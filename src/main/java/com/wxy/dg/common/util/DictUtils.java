package com.wxy.dg.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wxy.dg.common.enums.OrderTypeEnum;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.model.OrderType;
import org.apache.commons.lang3.StringUtils;

import com.wxy.dg.modules.dao.CommonDao;
import com.wxy.dg.modules.model.SysCode;
import com.wxy.dg.modules.service.SpringBeanService;

/**
 * 字典工具类
 */
public class DictUtils {

	private static CommonDao commonDao = SpringBeanService.getBean(CommonDao.class);
	public static final String CACHE_DICT_MAP = "dictMap";

	public static String getCodeName(String codeId, String type){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(codeId)){
			for (SysCode dict : getCodeList(type)){
				if (type.equals(dict.getCode_type()) && codeId.equals(dict.getCode_id())){
					return dict.getCode_name();
				}
			}
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static List<SysCode> getCodeList(String type) {
		Map<String, List<SysCode>> dictMap = (Map<String, List<SysCode>>) CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap == null) {
			dictMap = new HashMap<String, List<SysCode>>();
			for (SysCode dict : commonDao.findAllList()) {
				List<SysCode> dictList = dictMap.get(dict.getCode_type());
				if (dictList != null) {
					dictList.add(dict);
				} else {
					List<SysCode> list = new ArrayList<SysCode>();
					list.add(dict);
					dictMap.put(dict.getCode_type(), list);
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}

		List<SysCode> dictList = dictMap.get(type);
		if (dictList == null) {
			dictList = new ArrayList<SysCode>();
		}
		return dictList;
	}

	public static List<SysCode> getCodeListNormal(String type) {
		Map<String, List<SysCode>> dictMap = (Map<String, List<SysCode>>) CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap == null) {
			dictMap = new HashMap<String, List<SysCode>>();
			for (SysCode dict : commonDao.findAllList()) {
				List<SysCode> dictList = dictMap.get(dict.getCode_type());
				if (dictList != null) {
					dictList.add(dict);
				} else {
					List<SysCode> list = new ArrayList<SysCode>();
					list.add(dict);
					dictMap.put(dict.getCode_type(), list);
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}

		List<SysCode> dictList = dictMap.get(type);
		if (dictList == null) {
			dictList = new ArrayList<SysCode>();
		}
		for (int i=0;i<dictList.size();i++){
			SysCode sysCode = dictList.get(i);
			if(!"102".equals(sysCode.getCode_id())&&!"103".equals(sysCode.getCode_id())){
				dictList.remove(i);
			}
		}
		return dictList;
	}

	public static List<OrderType> getOrderTypeList() {
		OrderTypeEnum[] enums = OrderTypeEnum.values();
		List<OrderType> list = new ArrayList<OrderType>(enums.length);
		for (OrderTypeEnum each:enums){
			OrderType orderType = new OrderType();
			orderType.setOrderType(each.getCode());
			orderType.setOrderTypeName(each.getMessage());
			list.add(orderType);
		}
		return list;
	}
}