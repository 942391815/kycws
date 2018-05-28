<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>用户登录</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<link rel="stylesheet" href="${ctxStatic}/common/typica-login.css">
	<style type="text/css">
		.control-group{border-bottom:0px;}
	</style>
    <script src="${ctxStatic}/common/backstretch.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$.backstretch([
 		      "${ctxStatic}/img/bg1.jpg", 
 		      "${ctxStatic}/img/bg2.jpg",
 		      "${ctxStatic}/img/bg3.jpg",
 		      "${ctxStatic}/img/bg4.jpg",
 		      "${ctxStatic}/img/bg5.jpg"
 		  	], {duration: 10000, fade: 2000});
	  		$("#loginForm").Validform({
	  			btnSubmit : "#lg",
	  	        tiptype: 2
	  		});
		});
		// 如果在框架中，则跳转刷新上级页面
		if(self.frameElement && self.frameElement.tagName=="IFRAME"){
			parent.location.reload();
		}
	</script>
</head>
<body>
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand">
              <%--<img src="${ctxStatic}/img/top_logo.png" alt="代感人工智能系统" style="height:40px;">--%>
          </a>
        </div>
      </div>
    </div>

    <div class="container">
		<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
		<%String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);%>
		<div id="messageBox" class="alert alert-error <%=error==null?"hide":""%>"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error"><%=error==null?"":"org.apache.shiro.authc.IncorrectCredentialsException".equals(error)?"用户或密码错误, 请重试.":"非系统管理员不能登录" %></label>
		</div>
        <div id="login-wraper">
            <form id="loginForm" class="form login-form" action="${ctx}/sysLogin" method="post">
                <fieldset><legend><span style="color:#08c;">系统登录</span></legend></fieldset>
                <div class="body">
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" id="username" name="username" value="${username}" maxlength="15" placeholder="用户名" datatype="*" nullmsg="请输入用户名">
                        </div>
                        <div class="Validform_checktip"></div>
                    </div>
                    
                    <div class="control-group">
                        <div class="controls">
                            <input type="password" id="password" name="password" maxlength="16" placeholder="密码" datatype="*" nullmsg="请输入密码"/>
                        </div>
                        <div class="Validform_checktip"></div>
                    </div>
                </div>
                <div class="footer">
                    <label class="checkbox inline">
                        <input type="checkbox" id="rememberMe" name="rememberMe"><span style="color:#08c;">记住我</span>
                    </label>
                    <input class="btn btn-primary" type="submit" id="lg" value="登 录"/>
                </div>
            </form>
        </div>
    </div>
    <footer class="white navbar-fixed-bottom">
		技术支持：<a href="http://www.hnkaiyun.com/" target="_blank">河南赛恩科技有限公司</a>
    </footer>
  </body>
</html>