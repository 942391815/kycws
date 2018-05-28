<!DOCTYPE html>
<%@ page language="java" import="com.wxy.dg.common.config.Global" %>
<%@ page language="java" import="com.wxy.dg.common.constant.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>拍照管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<style type="text/css">
		.pd {width: 95%;height:480px;border: 1 solid #CCC;padding: 5px;margin:5px;overflow-y:auto} 
		.pu li{float:left;list-style-type:none;margin: 5px;padding: 5px}
		.pu li a{display:block;}
		.pu li p{text-align:left;height: 10px;padding-left: 5px}
		.pu li img{position:relative;left:1px;right: 1px}
		.litimg {max-height:300px; max-width:300px; width:expression(this.width > 300 && this.height < this.width ? 300: true); height:expression(this.height > 300 ? 300: true);}
	</style>
	<script type="text/javascript">
		function page(n){
			$("#destpage").val(n);
			$("#searchForm").attr("action","${ctx}/photo").submit();
	    	return false;
	    }
		function set() {
			$("#category").select2("val", $("#categoryId").val());
		}
	</script>
</head>
<body onload="set()">
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/photo">照片列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="photo" action="${ctx}/photo" method="post" class="breadcrumb form-search">
		<input id="destpage" name="destpage" type="hidden" value="${paramMap.destpage}"/>
		<input id="categoryId" name="categoryId" type="hidden" value="${paramMap.category}"/>
		<label>林场：</label><tags:treeselect id="farm" name="orgId" value="${paramMap.orgId}" 
			labelName="orgName" labelValue="${paramMap.orgName}" title="组织结构" url="/org/treeData" cssClass="input-small" allowClear="true"/>
		<label>类别：</label>
		<form:select path="category" cssStyle="width:150px" >
			<form:option value="" label="请选择"/>
			<form:options items="${fns:getCodeList('CATEGORY')}" itemLabel="code_name" itemValue="code_id" htmlEscape="false" />
		</form:select>
		<label>上传时间从：</label><input type="text" style="width: 130px;" id="startDate" readonly="readonly" name="startDate" value="${paramMap.startDate}" class="Wdate" onClick="WdatePicker()"/>
		<label>至：</label><input type="text" style="width: 130px;" id="endDate" readonly="readonly" name="endDate" value="${paramMap.endDate}" class="Wdate" onClick="WdatePicker()"/>
		&nbsp;<input id="button_query" class="btn btn-primary" type="submit" value="查询" onclick="return page(1);"/>
	</form:form>
	
	<tags:message content="${message}"/>
	<div class="pd">
	    <ul class="pu">
		<c:forEach items="${page.list}" var="photo"> 
	      <li>
	        <a href="${ctx}/photo/manage?id=${photo.id}" target="mainFrame" style="height: 140px;width: 160px;border: 1 solid #CCC;overflow:hidden;vertical-align: middle;">
	        	<img class="litimg" src="<%=Global.getPhotoUrl() + Global.getLitImgFolder() + "/" + Constant.PREFIX_LIT %>${photo.name}"/>
	        </a>
	        <p>林&nbsp;&nbsp;&nbsp;场：<label>${photo.uploader.organization.name}</label></p>
	        <p>类&nbsp;&nbsp;&nbsp;型：<label>${photo.category_name}</label></p>
	        <p>上传者：<label>${photo.uploader.name}</label></p>
	        <p>上传时间：<label><fmt:formatDate value="${photo.uploadtime}" type="both"/></label></p>
	      </li> 
	    </c:forEach> 
	    </ul>
	</div>
	<div style="margin-left: 50px">
			当前第<c:out value="${page.pageNo}"/>页  / 共<c:out value="${page.pageCount}"/>页
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${page.pageCount > 0}">
				<a href="javascript:" onclick="page(1);" style="margin: 10px;">首页 </a>
			</c:if>
			<c:if test="${page.pageNo > 1}">
				<a href="javascript:" onclick="page(${page.pageNo-1});" style="margin: 5px;"> 上一页</a>
			</c:if>
			<c:if test="${page.pageNo < page.pageCount}">
				<a href="javascript:" onclick="page(${page.pageNo+1});" style="margin: 5px;">下一页 </a>
			</c:if>
			<c:if test="${page.pageCount > 1}">
				<a href="javascript:" onclick="page(${page.pageCount});" style="margin: 5px;">尾页 </a>
			</c:if>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			跳至第<select style="width: 60px" onchange="page(this.options[this.selectedIndex].value);">
					<c:forEach var="index" begin="1" end="${page.pageCount}" step="1" varStatus="status">
						<c:choose>
							<c:when test="${index == page.pageNo}">
								<option value="${index}" selected="selected">${index}</option>
							</c:when>
							<c:otherwise>
								<option value="${index}">${index}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>页
		</div>
</body>
</html>