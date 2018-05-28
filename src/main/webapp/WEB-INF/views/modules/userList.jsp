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
		$(document).ready(function() {
			$("#organizationId").change(function() {
				$("#name").focus();
		});
		});
	</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="#">人员列表</a></li>
		<c:if test="${userType==100}">
			<li><a href="${ctx}/user/manage">人员添加</a></li>
		</c:if>
	</ul>
	
	<tags:message content="${message}"/>
	<display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/user" id="table" sort="external"
		partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
		<display:column property="name" title="姓名" style="width:150px"/>
		<display:column property="login_name" title="登录名" style="width:200px" sortable="true" sortName="login_name"/>
		<display:column property="remark" title="备注"/>
		<display:column title="操作">
			<a href="${ctx}/user/manage?id=${table.id}">修改</a>
			<c:if test="${table.user_type!='100'}">
				<a href="${ctx}/user/delete?id=${table.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
			</c:if>
		</display:column>
	</display:table>
</body>
</html>