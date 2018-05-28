<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>组织机构管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<%@ include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#org").treeTable({expandLevel : 5});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/org">机构列表</a></li>
		<li><a href="${ctx}/org/manage">机构添加</a></li>
	</ul>
	<tags:message content="${message}"/>
	<table id="org" class="table table-striped table-bordered table-condensed">
		<tr><th>机构名称</th><th>操作</th></tr>
		<c:forEach items="${orgList}" var="office">
			<tr id="${office.id}" pId="${office.parent.id ne 1?office.parent.id:'0'}">
				<td title="${office.name}" style="width:400px"><c:out value="${office.name}"/></td>
				<td>
					<a href="${ctx}/org/manage?id=${office.id}">修改</a>
					<a href="${ctx}/org/delete?id=${office.id}" onclick="return confirmx('要删除该机构及所有下级机构吗？', this.href)">删除</a>
					<a href="${ctx}/org/manage?parent.id=${office.id}">添加下级机构</a> 
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>