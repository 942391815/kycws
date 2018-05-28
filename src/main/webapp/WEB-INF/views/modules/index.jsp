<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>代感人工智能系统</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
    <%@ include file="/WEB-INF/views/include/jbox.jsp"%>
	<style type="text/css">
		#main {padding:0;margin:0;} #main .container-fluid{padding:0 7px 0 10px;}
		#header {margin:0 0 10px;position:static;} #header li {font-size:14px;_font-size:12px;}
		#header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px;}
		#footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
		#footer, #footer a {color:#999;} 
	</style>
	<script type="text/javascript"> 
		$(document).ready(function() {
			$("#menu a.menu").click(function(){
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				if(!$("#openClose").hasClass("close")){
					$("#openClose").click();
				}
			});
		});
	    function updateDate() {
	        document.getElementById("time").innerHTML =
	            new Date().toLocaleString() + " 星期" + "日一二三四五六".charAt(new Date().getDay());
	    }
	    setInterval("updateDate();", 1000);
	</script>
</head>
<body>
<shiro:hasPermission name="*:*:*">
</shiro:hasPermission>
	<div id="main">
		<div id="header" class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="brand">代感人工智能系统</div>
				<div class="nav-collapse">
					<ul class="nav pull-right">
						<li><a href="${ctx}/init" title="访问主页" target="mainFrame"><i class="icon-home"></i></a></li>
						<li><a href="#"><span id="time"></span>&nbsp;&nbsp;您好，<shiro:principal property="name"/></a></li>
						<li><a href="${ctx}/logout" title="退出登录">退出</a></li>
						<li>&nbsp;</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left">
					<iframe id="menuFrame" name="menuFrame" src="${ctx}/menu/tree" style="overflow:visible;"
						scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" src="${ctx}/attendance" style="overflow:visible;"
						scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
			</div>
			<div id="footer" class="row-fluid">
				技术支持：<a href="http://www.hnkaiyun.com/" target="_blank">河南赛恩科技有限公司</a>
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		var leftWidth = "160"; // 左侧窗口大小
		function wSize(){
			var minHeight = 500, minWidth = 980;
			var strs=getWindowSize().toString().split(",");
			$("#menuFrame, #mainFrame, #openClose").height((strs[0]<minHeight?minHeight:strs[0])-$("#header").height()-$("#footer").height()-32);
			$("#openClose").height($("#openClose").height()-5);
			if(strs[1]<minWidth){
				$("#main").css("width",minWidth-10);
				$("html,body").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"});
			}else{
				$("#main").css("width","auto");
				$("html,body").css({"overflow":"hidden","overflow-x":"hidden","overflow-y":"hidden"});
			}
			$("#right").width($("#content").width()-$("#left").width()-$("#openClose").width()-5);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.js" type="text/javascript"></script>
</body>
</html>