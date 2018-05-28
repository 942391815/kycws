<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <title>首页</title>
    <meta http-equiv="refresh" content="300; url=${ctx}/init">
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript">
      adfdojo.require("adf.adf");
      adfdojo.require("wma.app");
      adfdojo.requireLocalization("resource","adf");
      adfdojo.requireLocalization("resource","wma","en");
      
      adfdojo.addOnLoad(function() {
        if (wma) {
          wma.init();
        }
      });
      function init() {
    	//如果这个方法已经被调用过，就退出
    	  if (arguments.callee.done) return;
    	//标志这个方法,避免onload执行两次
    	  arguments.callee.done = true;
    	  document.getElementById("init").click();
      }
      $(document).ready(function() {
			$("#online").click(function(){
				top.$.jBox($("#onlineUser").html(), {title:"在线人员", buttons:{"关闭":true},height:400});
			});
			$("#offline").click(function(){
				top.$.jBox($("#offlineUser").html(), {title:"离线人员", buttons:{"关闭":true},height:400});
			});
			$("#out").click(function(){
				top.$.jBox($("#outUser").html(), {title:"出界人员", buttons:{"关闭":true},height:400});
			});
      });
    </script>
    <style type="text/css">
      label {
        color:#000;
        font-size: 14px;
        font-weight: bold;
      }
    </style>
</head>

<f:view>
    <f:loadBundle basename="res.mapviewer" var="res"/>
    
    <a:context value="#{mapContext}" />
    
    <body class="soria" onload="init()">
	<div id="onlineUser" class="hide">
	<table class="table table-striped table-bordered table-condensed">
		<tr><th>#</th><th>姓名</th><th>所属林场</th><th>登录名</th></tr>
		<c:forEach items="${onLineUsers}" var="user" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td><c:out value="${user.name}"/></td>
				<td><c:out value="${user.organization.name}" /></td>
				<td><c:out value="${user.login_name}" /></td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<div id="offlineUser" class="hide">
	<table class="table table-striped table-bordered table-condensed">
		<tr><th>#</th><th>姓名</th><th>所属林场</th><th>登录名</th></tr>
		<c:forEach items="${offLineUsers}" var="user" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td><c:out value="${user.name}"/></td>
				<td><c:out value="${user.organization.name}" /></td>
				<td><c:out value="${user.login_name}" /></td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<div id="outUser" class="hide">
	<table class="table table-striped table-bordered table-condensed">
		<tr><th>#</th><th>姓名</th><th>所属林场</th><th>登录名</th></tr>
		<c:forEach items="${outUsers}" var="user" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td><c:out value="${user.name}"/></td>
				<td><c:out value="${user.organization.name}" /></td>
				<td><c:out value="${user.login_name}" /></td>
			</tr>
		</c:forEach>
	</table>
	</div>
      <h:form id="mapForm" style="height: 100%; width: 100%;">
        <div id="layoutContainer">
        </div>
        
        <div id="content">
          <span id="titleText">☆
            <font color="black" style="font-weight:bold;">护林员实时位置</font>
            <img class="img" alt="" src="${ctxStatic}/img/online.png" style="margin-left: 100px;"><label id="online"><font color="blue">${onlineCount}</font>人在线</label>
            <img class="img" alt="" src="${ctxStatic}/img/offline.png" style="margin-left: 20px;"><label id="offline"><font color="red">${offlineCount}</font>人离线</label>
            <img class="img" alt="" src="${ctxStatic}/img/out.png" style="margin-left: 20px;"><label id="out"><font color="red">${empty outCount?0:outCount}</font>人出界</label>
          </span>
          
          <div id="linksPane" style="margin-right: 0px;margin-top:20px">
          </div> 
        
          <!-- Map Control Content -->
          <a:map value="#{mapContext.webMap}" id="map1" width="#{param.width}" height="#{param.height}" scaleBar="#{mapContext.attributes['webappScaleBar']}" />
          <div id="northarrow">
             <h:inputHidden id="northarrowHid" value="#{mapContext.attributes.webappNorthArrow.imageUrl}" />
          </div>
  
          <!-- Map TOC Content -->
          <a:toc id="toc1" value="#{mapContext.webToc}" mapId="map1" clientPostBack="true" />
          <!-- Map Overview Content -->
          <a:overview id="ov1" value="#{mapContext.webOverview}" mapId="map1" width="200" height="150" lineColor="#F00" />
          
          <!-- Map Tools Content -->
          <a:task id="mapToolsTask"
                  value="#{mapContext.attributes.webappMapToolsTask}"
                  taskInfo="#{mapContext.attributes.webappMapToolsTask.taskInfo}"
                  mapId="map1" 
                  windowingSupport="false"
                  style="padding:0px;margin:0px;" 
                  xslFile="maptoolstask.xsl" />
          <input id="overviewToggleButton" width="25px" type="image" height="25px" 
                 title="Toggle Overview Map"
                 onclick="wma.toggleOverview(this); return false;"
                 onload="adf.Utils.onHandleImage(this, 'images/hide-overview-map.png');"
                 src="images/hide-overview-map.png" />
          <a:button id="init" toolTip="查询" 
                 serverAction="#{mapContext.attributes['index'].showOnlineUsers }" 
                 mapId="map1" />
          <h:inputHidden id="taskWindows"/>
        </div>
      </h:form>
    </body>
</f:view>
</html>