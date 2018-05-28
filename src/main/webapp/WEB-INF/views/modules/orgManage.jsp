<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>组织机构管理</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript">
	   $(function(){
	    	$("#mf").Validform({
	    		showAllError:true,
	    		tiptype:3,
	    		beforeSubmit:function(curform){
	    			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
	    			loading('正在提交，请稍等...');
	    		}
	    	});
    	    $("#orgId").change(function() {
        	    $("#name").focus();
    	    });
	   });
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/org">机构列表</a></li>
		<li class="active"><a href="#">机构${org.id != 0 ?'修改':'添加'}</a></li>
	</ul><br/>
 	
 	<form:form action="${ctx}/org/save" method="post" id="mf" modelAttribute="org" class="form-horizontal">
		<tags:message content="${message}"/>
 		<div class="control-group">
			<label class="control-label">上级机构:</label>
			<div class="controls">
                <tags:treeselect id="org" name="parent.id" value="${org.parent.id}" labelName="parent.name" labelValue="${org.parent.name}"
					title="机构" url="/org/treeData" extId="${org.id}" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="name"><font color="red">*</font>机构名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="30" datatype="*" nullmsg="请填写机构名称" />
				<div class="Validform_checktip"></div>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="master">负责人:</label>
			<div class="controls">
				<form:input path="master" htmlEscape="false" maxlength="30"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="phone">负责人电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" ignore="ignore" maxlength="20" datatype="m" errormsg="手机号码格式不对！"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="remark">备注:</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
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