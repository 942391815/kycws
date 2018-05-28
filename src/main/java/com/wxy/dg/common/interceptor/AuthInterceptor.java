package com.wxy.dg.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.SpringBeanService;
import com.wxy.dg.modules.service.UserService;

/**
 * 系统拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

	protected Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	private static UserService userService = SpringBeanService.getBean(UserService.class);

	// 在业务处理器处理请求之前被调用
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String userId = request.getParameter("userId");
		if(StringUtils.isNotEmpty(userId)) {
			User user = userService.getUser(Integer.parseInt(userId));
			// 已删除人员不能上传信息
			if(user == null || Constant.IsDeleteFlg.equals(user.getDel_flag())) {
				return false;
			}
		}
		return true;
	}

	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	// DispatcherServlet完全处理完请求后被调用
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}