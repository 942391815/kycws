<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>订单管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript" charset="UTF-8">
	</script>
</head>


<body>
	<form:form action="${ctx}/order/update" method="post" id="mf" modelAttribute="order" class="form-horizontal">
		<tags:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label" for="name"><font color="red">*</font>订单名称:</label>
			<div class="controls">
				<form:input path="name" datatype="*1-30" maxlength="30" htmlEscape="false"
							nullmsg="订单名称！" errormsg="订单名称过长！"/>
				<div class="Validform_checktip"></div>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="orderType"><font color="red">*</font>订单类型:</label>
			<div class="controls">
				<form:select path="orderType" cssStyle="width:220px" datatype="*" nullmsg="订单类型!">
					<form:option value="" label="请选择"/>
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
					nullmsg="请输入姓名！" errormsg="姓名过长！"/>
				<div class="Validform_checktip"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="discountPrice">打折后价格:</label>
			<div class="controls">
				<form:input path="discountPrice" datatype="*1-30" maxlength="30" htmlEscape="false"
							/>
				<div class="Validform_checktip"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="count">文章篇数:</label>
			<div class="controls">
				<form:input path="count" datatype="*1-100" maxlength="100" htmlEscape="false"
				/>
				<div class="Validform_checktip"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="describe">备注:</label>
			<div class="controls">
				<form:textarea path="describe" maxlength="255" htmlEscape="false" rows="3" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<form:hidden path="id"/>
	</form:form>
</body>
</html>