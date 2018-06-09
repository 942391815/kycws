<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>
<head>
    <title>菜单导航</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".accordion-heading a").click(function () {
                $('.accordion-toggle i').removeClass('icon-chevron-down');
                $('.accordion-toggle i').addClass('icon-chevron-right');
                if (!$($(this).attr('href')).hasClass('in')) {
                    $(this).children('i').removeClass('icon-chevron-right');
                    $(this).children('i').addClass('icon-chevron-down');
                }
            });
            $(".accordion-body a").click(function () {
                $("#menu li").removeClass("active");
                $("#menu li i").removeClass("icon-white");
                $(this).parent().addClass("active");
                $(this).children("i").addClass("icon-white");
            });
        });
    </script>
</head>
<body>
<div class="accordion" id="menu">
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse1" title=""><i
                    class="icon-chevron-down"></i>&nbsp;仓库</a>
        </div>
        <div id="collapse1" class="accordion-body collapse in">
            <div class="accordion-inner">
                <ul class="nav nav-list">
                    <li><a href="${ctx}/warehouse/query" target="mainFrame"><i class="icon-search"></i>&nbsp;查询领料单</a></li>
                    <li><a href="${ctx}/warehouse/index" target="mainFrame"><i class="icon-search"></i>&nbsp;添加领料单</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse2" title=""><i
                    class="icon-chevron-down"></i>&nbsp;销售部</a>
        </div>
        <div id="collapse2" class="accordion-body collapse in">
            <div class="accordion-inner">
                <ul class="nav nav-list">
                    <li><a href="${ctx}/mywork" target="mainFrame"><i class="icon-search"></i>&nbsp;产品购销协议书</a></li>

                </ul>
            </div>
        </div>
    </div>
    <c:if test="${userType!=103}">
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse3" title=""><i
                    class="icon-chevron-down"></i>&nbsp;待审核任务</a>
        </div>
        <div id="collapse3" class="accordion-body collapse in">
            <div class="accordion-inner">
                <ul class="nav nav-list">
                    <li><a href="${ctx}/backInfo/toAudit" target="mainFrame"><i class="icon-search"></i>&nbsp;待审核任务</a></li>
                </ul>
            </div>
        </div>
    </div>
    </c:if>
    <c:if test="${userType==100||userType==101}">
        <div class="accordion-group">
            <div class="accordion-heading">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse4" title=""><i
                        class="icon-chevron-right"></i>&nbsp;系统管理</a>
            </div>
            <div id="collapse4" class="accordion-body collapse in">
                <div class="accordion-inner">
                    <ul class="nav nav-list">
                        <li><a href="${ctx}/user/index" target="mainFrame"><i class="icon-user"></i>&nbsp;人员管理</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${userType==100}">
        <div class="accordion-group">
            <div class="accordion-heading">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse5" title=""><i
                        class="icon-chevron-right"></i>&nbsp;产品管理</a>
            </div>
            <div id="collapse5" class="accordion-body collapse in">
                <div class="accordion-inner">
                    <ul class="nav nav-list">
                        <li><a href="${ctx}/order/list" target="mainFrame"><i class="icon-user"></i>&nbsp;产品管理</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>