<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
    <title>待审核任务</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript">
        $(function () {
            $("#farmId").change(function () {
                setSelect($("#farmId").val(), "user");
                $("#user").select2("val", "");
            });
        });

        // 设置人员的默认选中值
        function setUser() {
            if ($("#farmId").val() != "" && $("#farmId").val() != 0) {
                setSelect($("#farmId").val(), "user");
                $("#user").select2("val", $("#userId").val());
            }

        }

        function insert() {
            $.ajax({//文章标题，作者
                url: "${ctx}/task/insert",
                data: {
                    "title": "一个演员的自我修养",
                    "writer": "张三",
                    "url": "www.baidu.com",
                    "openid": "1212"
                },
                type: "POST",
                dataType: "json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
            });
        }
        function check() {
            $("#formid").submit();
        }

        function toBackInfo(id) {

        }
    </script>
</head>
<body onload="setUser()">
<ul class="nav nav-tabs">
    <li ${subType=='1' ?"class='active'":''}><a href="${ctx}/backInfo/toAudit?subType=1">自媒体任务</a></li>
    <li ${subType=='2' ?"class='active'":''}><a href="${ctx}/backInfo/toAudit?subType=2">企业任务</a></li>
    <li ${subType=='3' ?"class='active'":''}><a href="${ctx}/backInfo/toAudit?subType=3">品牌任务</a></li>
</ul>
<tags:message content="${message}"/>
<display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/mywork" id="table" sort="external"
               partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
    <display:column property="url" title="链接" sortable="false" sortName="url" escapeXml="true"/>
    <display:column property="title" title="标题" sortable="false" sortName="title"/>
    <display:column property="source" title="来源" sortable="false" sortName="writer" class="distance"/>
    <display:column property="submitTime" title="提交时间" sortable="false"/>
    <display:column title="操作">
        <a href="${ctx}/backInfo/detail?bid=${table.bid}&from=aduit&subType=${subType}">审核</a>
    </display:column>
</display:table>

</body>
</html>