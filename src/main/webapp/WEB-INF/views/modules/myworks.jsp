<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
	<title>我的任务</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(function(){
			$("#farmId").change(function(){
				setSelect($("#farmId").val(),"user");
				$("#user").select2("val", "");
			});
		});

		// 设置人员的默认选中值
		function setUser() {
			if($("#farmId").val() != "" && $("#farmId").val() != 0) {
				setSelect($("#farmId").val(),"user");
				$("#user").select2("val", $("#userId").val());
			}

		}

		function insert(){
			$.ajax({//文章标题，作者
				url:"${ctx}/task/insert",
				data:{
					"title":"一个演员的自我修养",
					"writer":"张三",
					"url":"www.baidu.com",
					"openid":"1212"
				},
				type : "POST",
				dataType:"json",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
			});
		}
		function check(){
			$("#formid").submit();
		}

		function toBackInfo(id){

		}
	</script>
</head>
<body onload="setUser()">
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mywork">我的任务</a></li>
		<li><a href="${ctx}/myInHandWork">进行中任务</a></li>
		<li><a href="${ctx}/mySubmitWork">已提交任务</a></li>
	</ul>

	<tags:message content="${message}"/>
	<display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/mywork" id="table" sort="external"
		partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
		<display:column property="url" title="链接" sortable="true" sortName="url" escapeXml="true"/>
		<display:column property="title" title="标题" sortable="true" sortName="title"/>
		<display:column property="writer" title="作者" sortable="true" sortName="writer" class="distance"/>
		<display:column property="subTime" title="提交时间" sortable="true" sortName="sub_time"/>
		<display:column title="操作">
			<c:if test="${table.state=='1'}">
				<a href="${ctx}/backInfo/toBackInfo?subId=${table.sid}">处理</a>
			</c:if>
			<c:if test="${table.state=='2'}">
				已提交
			</c:if>
		</display:column>
    </display:table>

	<input type="button" value="click me " onclick="insert()">

</body>
</html>