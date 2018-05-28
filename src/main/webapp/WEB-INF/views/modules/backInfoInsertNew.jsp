<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
    <title>订单管理</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery.validate.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/css">
    <link rel="stylesheet" href="${ctxStatic}/static/bootstrap/2.3.2/css_default/bootstrap.min.css" type="text/css">
    <%--<script src="${ctxStatic}/static/backinfo/backinfo.js" type="text/javascript"></script>--%>
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
        });

        function submitForm(state) {
            $("#state").val(state);
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

    </script>
</head>


<body>
<div class="control-group">
    <label class="control-label"><font color="red">*</font>用户提交：</label>
</div>
<display:table name="subInfo" id="table" sort="external"
               partialList="false" class="table table-striped table-bordered table-condensed">
    <c:if test="${subType=='1'}">
        <display:column property="url" title="链接"/>
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
    <button class="btn btn-primary btn-lg" onclick="openDialog()">
        添加反馈信息
    </button>
</div>


<table class="table">
    <caption>审核信息</caption>
    <thead>
    <tr>
        <th>链接</th>
        <th>标题</th>
        <th>来源</th>
        <th>是否侵权</th>
        <th>简报</th>
    </tr>
    </thead>
    <tbody id="tbody">
    </tbody>
</table>

<form:form action="${ctx}/backInfo/insert?subType=${subType}" method="post" id="mf" class="form-horizontal"
           modelAttribute="backInfo">
    <tags:message content="${message}"/>

    <div class="control-group">
        <input id="baidu" class="btn" type="button" onclick="window.open('http://wwww.baidu.com')" value="百度搜索"/>&nbsp;
        <input id="360" class="btn" type="button" onclick="window.open('https://www.so.com/')" value="360搜索"/>&nbsp;
        <input id="sougou" class="btn" type="button" onclick="window.open('https://www.sogou.com/')" value="360搜索"/>&nbsp;

    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="submitForm(0)"/>&nbsp;
        <input id="btnSubmit1" class="btn btn-primary" type="button" value="提 交" onclick="submitForm(1)"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
    <form:hidden path="subId" id="subId"/>
    <form:hidden path="bid"/>
    <form:hidden path="state"/>
</form:form>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    <div class="control-group">
                        添加反馈信息
                    </div>
                    <div class="control-group">
                        <p>
                            未侵权: <input type="radio" name="isTort" value="0"
                                        onclick="isNotTortFun()"/>
                            侵权: <input type="radio" name="isTort" value="1"
                                       onclick="isTortFun()"/>
                        </p>
                    </div>
                    <div id="content">
                        <div class="control-group">
                            <label class="control-label"><font color="red">*</font>链接URL:</label>
                            <input type="text" name="url" id="url">
                        </div>
                        <div class="control-group">
                            <label class="control-label"><font color="red">*</font>文章名称:</label>
                            <input type="text" name="title" id="title">
                        </div>
                        <div class="control-group">
                            <label class="control-label"><font color="red">*</font>来源:</label>
                            <input type="text" name="source" id="source">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">简报:</label>
                        <textarea name="briefing" style="width:200px;height:80px;" id="briefing"></textarea>
                    </div>
                </h4>
            </div>
            <input type="hidden" id="bid"/>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" onclick="submitBackInfo()">
                    提交更改
                </button>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">

    function openDialog(){
        $('#myModal').modal('show');
        $("#title").val("");
        $("#url").val("");
        $("#source").val("");
        $("#briefing").val("");
    }
    function submitBackInfo(){
        var backInfo={};
        backInfo.title=$("#title").val();
        backInfo.url=$("#url").val();
        backInfo.source=$("#source").val();
        backInfo.briefing=$("#briefing").val();
        backInfo.subId=$("#subId").val();
        backInfo.isTort=$("input[name='isTort']:checked").val();
        $('#myModal').modal('hide')
        $.ajax({
            url:"${ctx}/backInfo/saveBackInfo",
            data:backInfo,
            dataType:"json",
            async: false,
            success:function(data){
                backInfo.id=data.result;
            },
            error:function(data){
                alert("数据保存失败，请联系管理员！");
            }
        });
        //todo 刷新列表信息
        $("#tbody").append("<tr><td>"+backInfo.url+"</td><td>"+ backInfo.title+"</td><td>"+backInfo.source+"</td><td>"+backInfo.isTort+
                "</td><td><a onclick='updateBackInfo("+backInfo.id+")' >修改</a></td></tr>");
    }

    function updateBackInfo(bid){
        $.ajax({
            url:"${ctx}/backInfo/findbyPk?pk="+bid,
            async: false,
            success:function(data){
                $("#title").val(data.result.title);
                $("#url").val(data.result.url);
                $("#source").val(data.result.source);
                $("#briefing").val(data.result.briefing);
                $("#bid").val(data.result.bid);
                $('#myModal').modal('show');
            },
            error:function(data){
                alert("数据查询失败，请联系管理员！");
            }
        });
    }
</script>
</html>