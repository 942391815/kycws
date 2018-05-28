<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>
<head>
    <title>人员管理</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript" charset="UTF-8">
        $(function () {
            $("#mf").Validform({
                datatype: {
                    "password": function (gets, obj, curform, regxp) {
                        var password = curform.find("#password").val();
                        if (password != "") {
                            if (gets == "") {
                                return "请再输入一次密码";
                            } else {
                                if (gets != password) {
                                    return "两次输入的密码不一致";
                                }
                            }
                        }
                        return true;
                    }
                },
                showAllError: true,
                tiptype: 3,
                beforeSubmit: function (curform) {
                    //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                    loading('正在提交，请稍等...');
                }
            });
        });

        $(document).ready(function () {
            $("#organizationId").change(function () {
                $("#name").focus();
            });
            $("#user_type").on("select2-blur", function (e) {
                // 触发onblur,使校验生效
                $("#user_type").blur();
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/user?organization.id=${orgId}&organization.name=${orgName}">人员列表</a></li>
    <li class="active"><a href="#">人员${user.id != 0 ?'修改':'添加'}</a></li>
</ul>
<br/>

<form:form modelAttribute="user" action="${ctx}/user/save" method="post" id="mf" class="form-horizontal">
    <tags:message content="${message}"/>

    <div class="control-group">
        <label class="control-label" for="login_name"><font color="red">*</font>登录名:</label>
        <div class="controls">
            <form:input path="login_name" datatype="*2-15" maxlength="15" id="login_name" htmlEscape="false"
                        nullmsg="请输入登录名！" errormsg="登录名至少2个字符,最多15个字符！" ajaxurl="${ctx}/user/checkUser?id=${user.id}"/>
            <div class="Validform_checktip">登录名至少2个字符,最多15个字符</div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="name"><font color="red">*</font>姓名:</label>
        <div class="controls">
            <form:input path="name" datatype="*1-30" maxlength="30" htmlEscape="false"
                        nullmsg="请输入姓名！" errormsg="姓名过长！"/>
            <div class="Validform_checktip"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="password"><c:if test="${user.id == 0}"><font
                color="red">*</font></c:if>密码:</label>
        <div class="controls">
            <form:password id="password" path="newPassword" datatype="${user.id == 0?'*6-16':''}" maxlength="16"
                           nullmsg="${user.id == 0?'请输入密码':''}"/>
            <c:if test="${user.id == 0}">
                <div class="Validform_checktip">密码至少6个字符,最多16个字符</div>
            </c:if>
            <c:if test="${user.id != 0}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="confirmPassword"><c:if test="user.id == 0 "><font color="red">*</font></c:if>确认密码:</label>
        <div class="controls">
            <input id="confirmPassword" name="confirmPassword" type="password" datatype="password"
                   nullmsg="请再输入一次密码" errormsg="两次输入的密码不一致"/>
            <div class="Validform_checktip"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="phone">手机号:</label>
        <div class="controls">
            <form:input path="phone" ignore="ignore" maxlength="20" htmlEscape="false" datatype="m"
                        errormsg="手机号码格式不对！"/>
            <div class="Validform_checktip"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="user_type"><font color="red">*</font>用户类型:</label>
        <div class="controls">
            <form:select path="user_type" cssStyle="width:220px" datatype="*" nullmsg="请选择用户类型!">
                <form:option value="" label="请选择"/>
                <form:options items="${fns:getCodeListNormal('user_type')}" itemLabel="code_name"
                              itemValue="code_id" htmlEscape="false"/>
            </form:select>
            <div class="Validform_checktip"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="remark">备注:</label>
        <div class="controls">
            <form:textarea path="remark" maxlength="255" htmlEscape="false" rows="3" class="input-xlarge"/>
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