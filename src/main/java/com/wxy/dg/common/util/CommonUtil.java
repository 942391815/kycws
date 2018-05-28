package com.wxy.dg.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.wxy.dg.common.config.Global;
import com.wxy.dg.common.security.Digests;
import com.wxy.dg.modules.model.User;

public class CommonUtil {

	private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	// 判断对象是否为空
	public static boolean isNotNull(Object obj) {

		boolean flg = false;
		if ((obj != null) && !"".equals(obj) && !"null".equals(obj)) {
			flg = true;
		}
		return flg;
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(8);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, 1024);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, 1024);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}

	// 判断日期格式是否合法
	public static boolean isDate(String str) {
		if (isNotNull(str)) {
			boolean isDate = false;
			int first = str.indexOf('-');
			int second = str.lastIndexOf('-');
			if ((first == 4) && (second == 7)) {
				isDate = true;
			}
			return isDate;
		} else {
			return true;
		}
	}

	// 判断是否为数字
	public static boolean isDigit(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	// 判断是否为手机号码
	public static boolean isMobile(String str) {
		return str.matches("^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$");
	}

	public static String checkFileName(String path, String prefix, String fileName) {
		if (new File(path + prefix + fileName).exists()) {
			fileName = "0_" + fileName;
			return checkFileName(path, prefix, fileName);
		} else {
			return fileName;
		}
	}

	/**
	 * 按字节长度截取字符串
	 * 
	 * @param str
	 *            :要截取的源字符串
	 * @param width
	 *            :字节长度
	 */
	public static String trimByte(String str, int width) {
		if ((str == null) || "".equals(str)) {
			return "";
		}

		int d = 0; // byte length
		int n = 0; // char length
		for (; n < str.length(); n++) {
			d = str.charAt(n) > 256 ? d + 2 : d + 1;
			if (d > width) {
				break;
			}
		}

		if (d > width) {
			n = n - 1;
			return str.substring(0, n > 0 ? n : 0) + "...";
		}

		return str = str.substring(0, n);
	}

	// 通知推送
	public static void pushMsg(String title, String content, List<User> users) {
		// 设置developer平台的ApiKey/SecretKey
		String apiKey = Global.getApiKey();
		String secretKey = Global.getSecretKey();
		// 创建PushKeyPair
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 创建BaiduPushClient，访问SDK接口
		BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

		// 注册YunLogHandler，获取本次请求的交互信息
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
			}
		});
		Map map = new HashMap();
		// title:android必选，ios可选
		map.put("title", title);
		// description不能包含"\r\n"
		map.put("description", content.replace("\r\n", ""));
		Map<String, String> customMap = new HashMap<String, String>();
		customMap.put("date", DateUtils.getDate());
		// 自定义字段
		map.put("custom_content", customMap);

		// 设置请求参数，创建请求实例
		PushMsgToSingleDeviceRequest pushRequest = new PushMsgToSingleDeviceRequest().addMessageType(1).
				addDeviceType(3).addMessage(JSON.toJSONString(map));// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
		for (int i = 0; i < users.size(); i++) {
			// 发送消息给具体的人员
			User user = users.get(i);
			if (StringUtils.isNotEmpty(user.getChannelId())) {
				// 推送通道ID
				pushRequest.addChannelId(user.getChannelId());
				// 消息推送
				try {
					// 执行Http请求，获取返回结果
					pushClient.pushMsgToSingleDevice(pushRequest);
				} catch (PushClientException e) {
					// 处理客户端错误异常
					e.printStackTrace();
				} catch (PushServerException e) {
					// 处理服务端错误异常
					logger.error(String.format(
							"userId: %d, requestId: %d, errorCode: %d, errorMessage: %s",
							user.getId(), e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
				}
			}
		}
	}
	
	// 判断纬度
	public static boolean isLatitude(String str) {
		return str.matches("^([0-9]{1,2})([.]([0-9]{1,6}))$");
	}

}