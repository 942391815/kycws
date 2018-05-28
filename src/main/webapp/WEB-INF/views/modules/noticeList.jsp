<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
	<title>通知列表</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">通知列表</a></li>
		<li><a href="${ctx}/notice/manage" target="mainFrame">通知发布</a></li>
	</ul>
	
	<tags:message content="${message}"/>
	<display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/noticelist" id="table" sort="external"
		partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
		<display:column property="send_time" title="发送时间" style="width:200px" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" sortName="send_time"/>
		<display:column property="title" title="标题" style="width:300px" sortable="true" sortName="title" escapeXml="true"/>
		<display:column title="操作">
			<a href="${ctx}/notice/push?id=${table.id}" onclick="loading('正在提交，请稍等...');">重发</a>
			<a href="${ctx}/notice/manage?id=${table.id}">查看</a>
			<a href="${ctx}/notice/delete?id=${table.id}" onclick="return confirmx('确认要删除该通知吗？', this.href)">删除</a>
		</display:column>
	</display:table>
</body>
</html>