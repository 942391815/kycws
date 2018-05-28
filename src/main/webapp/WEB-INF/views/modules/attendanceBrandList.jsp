<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
    <title>任务管理</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript">

        function getIds() {
            var ids = new Array();
            $("input:checkbox[name='ids']:checked").each(function () {
                var each = {};
                var id = $(this).val();
                each.sid = id;
                each.round = $("#" + id).val();
                ids.push(each);
            });
            $.ajax({
                url: "${ctx}/claim",
                data: {
                    ids: JSON.stringify(ids)
                },
                dataType: "json",
                success: function (data) {
                    window.location.reload();
                }
            });
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/${path}?subType=1">自媒体任务</a></li>
    <li><a href="${ctx}/${path}?subType=2">企业任务</a></li>
    <li class="active"><a href="${ctx}/${path}?subType=3">品牌任务</a></li>
</ul>
<tags:message content="${message}"/>
<display:table name="page.list" pagesize="${page.pageSize}" requestURI="${ctx}/attendance" id="table" sort="external"
               partialList="true" size="page.count" class="table table-striped table-bordered table-condensed">
    <display:column property="name" title="品牌名称" sortable="false" sortName="url" escapeXml="true"/>
    <display:column property="businessType" title="品牌类别" sortable="false" sortName="url" escapeXml="true"/>
    <display:column property="describe" title="客户描述" sortable="false" sortName="url" escapeXml="true"/>
    <display:column property="nextHandleTime" title="任务推送时间"/>

    <display:column title="选取任务">
        <c:if test="${path=='attendance'}">
            <input name="ids" title="选择" type="checkbox" value="${table.sid}"/>
            <input id="${table.sid}" type="hidden" value="${table.round}"/>
        </c:if>

        <c:if test="${path=='mywork'}">
            <c:if test="${table.state=='1'}">
                <a href="${ctx}/backInfo/toBackInfo?subId=${table.sid}">处理</a>
            </c:if>
            <c:if test="${table.state=='2'}">
                已提交
            </c:if>
        </c:if>
    </display:column>
</display:table>
<c:if test="${path=='attendance'}">
    <div class="form-actions">
        <c:if test="${page.count>0}">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="getIds()"/>&nbsp;
        </c:if>
    </div>
</c:if>
</body>
</html>