<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
    <title>订单管理</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery.validate.js"></script>
    <script type="text/javascript" charset="UTF-8">
        $().ready(function () {
            $("#mf").validate({
                rules: {
                    url: {
                        url: true,
                    },
                    title: {
                        required: true,
                        maxlength: 100
                    },
                    source: {
                        required: true,
                        maxlength: 200
                    }
                },
                messages: {
                    url: "请输入正确url",
                    title: {
                        required: "请输入文章名称",
                        maxlength: "最长100个字符"
                    },
                    source: {
                        required: "请输入来源",
                        maxlength: "最长200个字符"
                    }
                }
            });
            disableInput();
        });

        function submitForm(state) {
            var subType = ${subType};
            var subId = $("#subId").val();
            var articleName = $("#articleName").val();

            if(subType==1){//如果是自媒体则先保存文章名称
                $.ajax({
                    url:"${ctx}/subinfo/updateSubinfo",
                    data:{"subId":subId,"articleName":encodeURIComponent(articleName)},
                    success:function(result){
                        if(result.status==500){
                            alert("subinfo 保存失败，请联系管理员");
                        }
                    }
                });

            }
            $("#state").val(state);
            $("#name").val(articleName);
            $("#mf").submit();
        }

        function adoptOrRejected(state) {
            $("#state").val(state);
            $("#mf").attr("action", "${ctx}/backInfo/auditResult?subType=${subType}");
            $("#mf").submit();
        }

        //侵权
        function isTortFun() {
            $("#content").show();
        }

        //未侵权
        function isNotTortFun() {
            $("#content").hide();
        }

        function disableInput() {
            if ($("#from").val() == 'aduit') {
                $("#url").attr("disabled", "disabled");
                $("#title").attr("disabled", "disabled");
                $("#source").attr("disabled", "disabled");
                $("#briefing").attr("disabled", "disabled");
            }
        }
    </script>
</head>


<body>
<input id="from" type="hidden" value="${from}">
<div class="control-group">
    <label class="control-label"><font color="red">*</font>用户提交：</label>
</div>
<display:table name="subInfo" id="table" sort="external"
               partialList="false" class="table table-striped table-bordered table-condensed">
    <c:if test="${subType=='1'}">
        <display:column property="url" title="链接"/>
        <display:column title="文章名称">
            <input name="articleName" id="articleName" value="${subInfo.name}">
        </display:column>
        <display:column property="subTime" title="提交时间"/>
    </c:if>
    <c:if test="${subType=='2'}">
        <display:column property="name" title="企业名称" sortable="true" sortName="url" escapeXml="true"/>
        <display:column property="businessType" title="主营业务" sortable="true" sortName="url" escapeXml="true"/>
        <display:column property="describe" title="主要竞品" sortable="true" sortName="url" escapeXml="true"/>
        <display:column property="subTime" title="提交时间" sortable="true" sortName="sub_time"/>
    </c:if>
    <c:if test="${subType=='3'}">
        <display:column property="name" title="品牌名称" sortable="true" sortName="url" escapeXml="true"/>
        <display:column property="businessType" title="品牌类别" sortable="true" sortName="url" escapeXml="true"/>
        <display:column property="describe" title="客户描述" sortable="true" sortName="url" escapeXml="true"/>
        <display:column property="subTime" title="提交时间" sortable="true" sortName="sub_time"/>
    </c:if>
</display:table>
<div class="control-group">
    <label class="control-label"><font color="red">*</font>历史审核记录：</label>
</div>

<display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/order/list" id="table" sort="external"
               partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
    <display:column property="url" title="链接"/>
    <display:column property="title" title="标题"/>
    <display:column property="source" title="来源"/>
    <display:column property="submitTime" title="审核时间"/>
    <display:column title="是否侵权">
        <c:if test="${table.isTort==0}">
            未侵权
        </c:if>
        <c:if test="${table.isTort==1}">
            侵权
        </c:if>
    </display:column>


</display:table>
<span/>
<span/>
<span/>
<div class="control-group">
    <LABEL class="control-label"><font color="red">*</font>本次审核：</LABEL>
</div>


<form:form action="${ctx}/backInfo/insert?subType=${subType}" method="post" id="mf" class="form-horizontal"
           modelAttribute="backInfo">
    <tags:message content="${message}"/>

    <div class="control-group">
        <p>

            未侵权: <input type="radio" name="isTort" value="0"
                        onclick="isNotTortFun()" ${backInfo.isTort==0?"checked":''} ${from=='aduit' ?"disabled=true":''}/>
            侵权: <input type="radio" name="isTort" value="1"
                       onclick="isTortFun()" ${backInfo.isTort==1?"checked":''} ${from=='aduit' ?"disabled=true":''}/>
        </p>
    </div>

    <div id="content" ${backInfo.isTort==0 ? "style='display: none'":"" }>
        <div class="control-group">
            <label class="control-label" for="url"><font color="red">*</font>链接URL:</label>
            <div class="controls">
                <form:input path="url" datatype="*1-30" maxlength="30" htmlEscape="false"
                            nullmsg="链接URL！" errormsg="链接URL过长！" id="url"/>
                <div class="Validform_checktip"></div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="title"><font color="red">*</font>文章名称:</label>
            <div class="controls">
                <form:input path="title" htmlEscape="false"
                            nullmsg="文章名称！" errormsg="文章名称过长！" id="title"/>
                <div class="Validform_checktip"></div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="source"><font color="red">*</font>来源:</label>
            <div class="controls">
                <form:textarea path="source" maxlength="255" htmlEscape="false" rows="3" class="input-xlarge"
                               id="source" cssStyle="width: 60%"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="briefing">简报:</label>
        <div class="controls">
            <form:textarea path="briefing" maxlength="1000" htmlEscape="false" rows="5" class="input-xlarge"
                           id="briefing" cssStyle="width: 60%"/>
        </div>
    </div>

    <c:if test="${from!='aduit'}">
        <div class="control-group">
            <input id="baidu" class="btn" type="button" onclick="window.open('http://wwww.baidu.com')" value="百度搜索"/>&nbsp;
            <input id="360" class="btn" type="button" onclick="window.open('https://www.so.com/')" value="360搜索"/>&nbsp;
            <input id="sougou" class="btn" type="button" onclick="window.open('https://www.sogou.com/')" value="搜狗搜索"/>&nbsp;

        </div>
    </c:if>
    <div class="form-actions">
        <c:if test="${from=='aduit'}">
            <input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="adoptOrRejected(3)"/>&nbsp;
            <input id="btnSubmit1" class="btn btn-primary" type="button" value="驳 回" onclick="adoptOrRejected(0)"/>&nbsp;
        </c:if>
        <c:if test="${from!='aduit'}">
            <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="submitForm(0)"/>&nbsp;
            <input id="btnSubmit1" class="btn btn-primary" type="button" value="提 交" onclick="submitForm(1)"/>&nbsp;
        </c:if>

        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
    <form:hidden path="subId"/>
    <form:hidden path="bid"/>
    <form:hidden path="state"/>
</form:form>
</body>
</html>