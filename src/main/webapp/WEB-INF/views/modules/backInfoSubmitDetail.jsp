<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
	<title>订单管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript" src="${ctx}/static/jquery/jquery.validate.js"></script>
	<script type="text/javascript" charset="UTF-8">
		$().ready(function() {
			$("#mf").validate({
				rules: {
					url: {
						url:true,
					},
					title:{
						required:true,
						maxlength:100
					},
					source:{
						required:true,
						maxlength:200
					}
				},
				messages: {
					url: "请输入正确url",
					title:{
						required:"请输入文章名称",
						maxlength:"最长100个字符"
					},
					source:{
						required:"请输入来源",
						maxlength:"最长200个字符"
					}
				}
			});
		});

		function submitForm(state){
			$("#state").val(state);
			$("#mf").submit();
		}
	</script>
</head>


<body>
	<div class="control-group">
		<label class="control-label"><font color="red">*</font>用户提交记录：</label>
	</div>
	<display:table name="subInfo" id="table" sort="external"
				   partialList="false"  class="table table-striped table-bordered table-condensed">
		<c:if test="${subInfo.subType==1}">
			<display:column property="url" title="链接" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="name" title="文章名称" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="nextHandleTime" title="任务推送时间"/>
		</c:if>
		<c:if test="${subInfo.subType==2}">
			<display:column property="name" title="企业名称" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="businessType" title="主营业务" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="describe" title="主要竞品" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="nextHandleTime" title="任务推送时间"/>
		</c:if>
		<c:if test="${subInfo.subType==3}">
			<display:column property="name" title="品牌名称" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="businessType" title="品牌类别" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="describe" title="客户描述" sortable="false" sortName="url" escapeXml="true"/>
			<display:column property="nextHandleTime" title="任务推送时间"/>
		</c:if>
	</display:table>


	<div class="control-group">
		<label class="control-label"><font color="red">*</font>本次审核记录：</label>
	</div>
	<form:form action="${ctx}/backInfo/insert" method="post" id="mf" class="form-horizontal" modelAttribute="backInfo">
		<tags:message content="${message}"/>

		<div class="control-group">
			<label class="control-label"><font color="red">*</font>是否侵权:</label>
			<div class="controls">
				<c:if test="${backInfo.isTort==0}">
					未侵权
				</c:if>
				<c:if test="${backInfo.isTort==1}">
					侵权
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>链接URL:</label>
			<div class="controls">${backInfo.url}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>文章名称:</label>
			<div class="controls">${backInfo.title}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>来源:</label>
			<div class="controls">
				${backInfo.source}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>