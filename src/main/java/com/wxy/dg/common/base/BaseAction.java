package com.wxy.dg.common.base;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.wxy.dg.common.util.BeanValidators;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.common.util.Exceptions;

/**
 * 控制器支持类
 */
public abstract class BaseAction {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object) {
		try{
			BeanValidators.validateWithException(validator, object);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
		
	/**
	 * 添加Model消息
	 * @param messages 消息
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
     * @param messages 消息
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @return
	 */
	protected void toJsonString(HttpServletResponse response, Object object) {
		toJsonString(response, object, "application/json; charset=UTF-8");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param object
	 */
	protected void toJsonString(HttpServletResponse response, Object object, String type) {
		try {
			response.reset();
	        response.setContentType(type);
			response.getWriter().print(JSON.toJSONString(object, new ValueFilter() {
				@Override
				public Object process(Object obj, String name, Object value) {
					if(value == null) {
						return "";
					}
					return value;
				}
			},	SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.PrettyFormat));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统一异常处理
	 */
	@ExceptionHandler
	public String exception(HttpServletRequest request, Exception ex) {
		StringBuilder params = new StringBuilder();
		int index = 0;
		for (Object param : request.getParameterMap().keySet()) {
			params.append((index++ == 0 ? "?" : "&") + param + "=" + request.getParameter((String) param));
		}
		logger.info("异常发生的url:" + request.getRequestURL() + params.toString());
		logger.error(Exceptions.getStackTraceAsString(ex));
		if(request.getServletPath().startsWith("/mobile")) {
			return "error/error";
		}
		return "error/500";
	}

	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}
}