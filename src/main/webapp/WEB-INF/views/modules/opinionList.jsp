<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
	<title>意见列表</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<style type="text/css">
		.sub {
			display: -moz-inline-box;
			display: inline-block;
			width: 250px;
			text-overflow: ellipsis;
			white-space: nowrap;
			overflow: hidden;
			}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">意见列表</a></li>
	</ul>
	
	<form:form id="searchForm" modelAttribute="opinion" action="${ctx}/opinion" method="post" class="breadcrumb form-search">
		<label>发送人：</label><form:input path="user.name" htmlEscape="false" maxlength="30" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	
	<tags:message content="${message}"/>
    <display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/opinion" id="table" sort="external"
		partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
		<display:column property="user.name" title="发送人" style="width:100px" sortable="true" sortName="user.name"/>
		<display:column property="send_time" title="发送时间" style="width:200px" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" sortName="send_time"/>
		<display:column property="title" title="标题" style="width:250px" sortable="true" sortName="title" escapeXml="true"/>
		<display:column title="内容" style="width:350px">
			<c:out value="${fns:trimByte(table.content,50)}" />
		</display:column>
		<display:column title="回复内容" style="width:350px">
			<c:out value="${fns:trimByte(table.reply,50)}" />
		</display:column>
		<display:column title="操作">
			<a href="${ctx}/opinion/manage?id=${table.id}">查看</a>
			<a href="${ctx}/opinion/delete?id=${table.id}" onclick="return confirmx('确认要删除该建议吗？', this.href)">删除</a>
		</display:column>
    </display:table>
</body>
</html>