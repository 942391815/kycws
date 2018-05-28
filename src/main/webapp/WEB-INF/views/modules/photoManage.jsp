<!DOCTYPE html>
<%@ page language="java" import="com.wxy.dg.common.config.Global" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <title>拍照管理</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <style type="text/css">
    .control-label_1 {
	  	float: left;
	  	width: 80px;
	  	padding-top: 5px;
	  	text-align: right;
	}
	.controls_1 {
	  *display: inline-block;
	  padding-left: 10px;
	  margin-left: 90px;
	  *margin-left: 0;
	}
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/photo">照片列表</a></li>
		<li class="active"><a href="#">照片管理</a></li>
	</ul>
 	
 	<form:form modelAttribute="photo" action="${ctx}/photo/save" method="post" id="mf" class="form-horizontal">
		<table>
		  <tr>
			<td align="right">
			  	<a href="<%=Global.getPhotoUrl() %>${photo.name}" target="_blank"><img alt="" src="<%=Global.getPhotoUrl() %>${photo.name}" style="border: 1 solid #CCC;padding: 5px;max-width: 400px;max-height: 400px"/></a>
			  	<p align="center" style="margin: 5px">
			  	    <a style="font-size: 12px" href="${ctx}/photo/delete?id=${photo.id}" onclick="return confirmx('要删除该照片吗？', this.href)">删除</a>
			  	</p>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label_1" for="description">描述:</label>
					<div class="controls_1">
						<form:textarea path="description" id="description" htmlEscape="false" maxlength="255" rows="3" class="input-xlarge"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label_1">类别:</label>
					<div class="controls_1" style="font-size: 12;margin-top:5px;">
						<c:out value="${photo.category_name}"/>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label_1">林场:</label>
					<div class="controls_1" style="font-size: 12;margin-top:5px;">
						<c:out value="${photo.uploader.organization.name}"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label_1">上传者:</label>
					<div class="controls_1" style="font-size: 12;margin-top:5px;">
						<c:out value="${photo.uploader.name}"/>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label_1">上传时间:</label>
					<div class="controls_1" style="font-size: 12;margin-top:5px;">
						<fmt:formatDate value="${photo.uploadtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label_1">拍摄地点:</label>
					<div class="controls_1" style="font-size: 12;margin-top:5px;">
						<c:out value="${photo.shotplace}"/>
					</div>
				</div>
			</td>
		  </tr>
		</table>
		
		<div class="form-actions" style="padding-left: 400px">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="修改" onclick="loading('正在提交，请稍等...');"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<form:hidden path="id"/>
	</form:form>
</body>
</html>