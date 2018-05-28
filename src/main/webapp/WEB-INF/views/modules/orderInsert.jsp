<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>订单管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript" src="${ctx}/static/jquery/jquery.validate.js"></script>
	<script type="text/javascript" charset="UTF-8">
		$().ready(function() {
			$("#mf").validate({
				rules: {
					name: {
						required:true,
						maxlength:30
					},
					orderType:{
						required:true,
						number:true
					},
					price:{
						required:true,
						number:true
					},
					discountPrice:{
						required:true,
						number:true
					},
					describe:{
						maxlength:200
					}
		},
				messages: {
					name: "请输入订单名称",
					orderType:{
						number:"请选择订单类型！"
					},
					price:{
						number:"请输入数字！"
					},
					discountPrice:{
						number:"请输入打折后价格！"
					},
					describe:{
						maxlength:"最多200个字符"
					}
				}
			});
		});

	</script>
</head>


<body>
	<form:form action="${ctx}/order/insert" method="post" id="mf" class="form-horizontal" modelAttribute="order">
		<tags:message content="${message}"/>

		<div class="control-group">
			<label class="control-label" for="name"><font color="red">*</font>订单名称:</label>
			<div class="controls">
				<form:input path="name" datatype="*1-30" maxlength="30" htmlEscape="false"
							nullmsg="订单名称！" errormsg="订单名称过长！"  id="name"/>
				<div class="Validform_checktip"></div>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="orderType"><font color="red">*</font>订单类型:</label>
			<div class="controls">
				<form:select path="orderType" cssStyle="width:220px" datatype="*" nullmsg="订单类型!" id="orderType">
					<form:options items="${fns:getOrderTypeList()}" itemLabel="orderTypeName"
								  itemValue="orderType" htmlEscape="false"/>
				</form:select>
				<div class="Validform_checktip"></div>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="price">原价:</label>
			<div class="controls">
				<form:input path="price" datatype="*1-30" maxlength="30" htmlEscape="false"
					nullmsg="请价格姓名！" errormsg="价格！" id="price"/>
				<div class="Validform_checktip"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="discountPrice">打折后价格:</label>
			<div class="controls">
				<form:input path="discountPrice" datatype="*1-30" maxlength="30" htmlEscape="false"
							id="discountPrice"/>
				<div class="Validform_checktip"></div>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="count">文章篇数:</label>
			<div class="controls">
				<form:input path="count" datatype="*1-100" maxlength="30" htmlEscape="false"
							id="discountPrice"/>
				<div class="Validform_checktip"></div>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="describe">备注:</label>
			<div class="controls">
				<form:textarea path="describe" rows="3" class="input-xlarge" id="describe"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>