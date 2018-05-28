<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <title>轨迹查询</title>
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
            document.getElementById("clearLayer").click();
      }
      
      function check() {
    	var userId = $("#userId").val();
    	var queryDate = $("#queryDate").val();
    	if(userId == 0) {
    		top.$.jBox.alert("请选择人员");
    		return;
    	}
    	if(queryDate == "") {
    		top.$.jBox.alert("请选择查询日期");
    		return;
    	}
    	$("#searchMap").click();
      }
    </script>

</head>

  <f:view>
    <f:loadBundle basename="res.mapviewer" var="res"/>
    
    <a:context value="#{mapContext}" />
    
    <body class="soria" onload="init()">
      <h:form id="mapForm" style="height: 100%; width: 100%;">
        <div id="layoutContainer">
          
        </div>
        
        <div id="content">
          
          <span id="titleText"></span>
          <div id="linksPane">
                <label style="margin-left:20px">人员：</label>
				<tags:treeselect id="user" name="user" value="" labelName="userName" labelValue="" url="/org/treeData?isChild=true"
					title="人员" notAllowSelectParent="true" childUrl="/user/treeData" childParam="orgId" cssClass="input-small"/>
                <label>日期：</label><input type="text" style="width: 130px;" id="queryDate" readonly="readonly" name="queryDate" value="${queryDate}" class="Wdate" onClick="WdatePicker()"/>
				<input id="btnSubmit" class="btn btn-primary" style="margin-bottom:10px;margin-left: 10px;" type="button" value="查询" onclick="check();"/>
                <a:button toolTip="查询" id="searchMap" style="display:none;"
                 serverAction="#{mapContext.attributes['mapEditor'].addPointonMap }" 
                 mapId="map1"/>
                <a:button toolTip="重置轨迹" id="clearLayer" style="display:none;"
                 serverAction="#{mapContext.attributes['mapEditor'].cleartempLayer }" 
                 mapId="map1"/> 
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
            
          <h:inputHidden id="taskWindows"/>
        </div>
      </h:form>
    </body>
  </f:view>
</html>