<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
	<title>人员列表</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<%@ include file="/WEB-INF/views/include/jbox.jsp" %>
	<script type="text/javascript">
		function publish(){

		}
	</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="#">订单列表</a></li>
		<li>
			<a href="${ctx}/order/detail?id=">发布订单</a>
		</li>
	</ul>
	
	<tags:message content="${message}"/>
	<display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/order/list" id="table" sort="external"
		partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
		<display:column property="name" title="名称" style="width:150px"/>
		<display:column property="price" title="原价" style="width:200px"/>
		<display:column property="discountPrice" title="打折后价格"/>
		<display:column property="count" title="文章篇数"/>
		<display:column property="describe" title="描述"/>
		<display:column title="操作">
			<a href="${ctx}/order/detail?id=${table.id}">修改</a>
			<a href="${ctx}/order/deleteOrder?id=${table.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)">删除</a>
		</display:column>
	</display:table>
</body>
</html>