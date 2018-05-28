<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>意见管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/opinion">意见列表</a></li>
		<li class="active"><a href="#">意见查看</a></li>
	</ul><br/>
 	
 	<form:form modelAttribute="opinion" action="${ctx}/opinion/reply" method="post" id="mf" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<c:out value="${opinion.title}"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="content">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" maxlength="255" rows="5" class="input-xlarge" readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送时间：</label>
			<div class="controls">
				<fmt:formatDate value="${opinion.send_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<br><br><br>
		<div class="control-group">
			<label class="control-label" for="reply">回复：</label>
			<div class="controls">
				<form:textarea path="reply" htmlEscape="false" maxlength="255" rows="5" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复时间：</label>
			<div class="controls">
				<fmt:formatDate value="${opinion.reply_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="确认回复" onclick="loading('正在提交，请稍等...');"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<form:hidden path="id"/>
	</form:form>
</body>
</html>