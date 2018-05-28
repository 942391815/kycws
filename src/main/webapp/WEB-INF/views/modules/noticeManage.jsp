<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>通知管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<%@ include file="/WEB-INF/views/include/ztree.jsp" %>
     <script type="text/javascript">
       var tree;
	   $(function(){
	    	$("#mf").Validform({
    			datatype:{
    				"objectTree":function(gets,obj,curform,regxp){
    					/*参数gets是获取到的表单元素值，
    					  obj为当前表单元素，
    					  curform为当前验证的表单，
    					  regxp为内置的一些正则表达式的引用。*/
    					var nodes = tree.getCheckedNodes(true);
    					if(nodes.length == 0) {
    						return false;
    					} else {
    						return true;
    					}
    				}
    			},
	    		showAllError:true,
	    		tiptype:3,
	    		beforeSubmit:function(curform) {
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						if(nodes[i].id.indexOf("u_") != -1) {
							ids.push(nodes[i].id.replace("u_",""));
						}
					}
					$("#objIds").val(ids);
					loading('正在提交，请稍等...');
	    		}
	    	});
			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false,dblClickExpand:false},
					async:{enable:true,url:"${ctx}/user/treeData",autoParam:["id=orgId"]},
					data:{simpleData:{enable:true}},callback:{
					onClick:function(event, treeId, treeNode){
						tree.expandNode(treeNode);
					},onCheck: function(e, treeId, treeNode){
						var nodes = tree.getCheckedNodes(true);
						for (var i=0, l=nodes.length; i<l; i++) {
							tree.expandNode(nodes[i], true, false, false);
						}
						return false;
					},onAsyncSuccess: function(event, treeId, treeNode, msg){
						selectCheckNode();
					}
					}
				};
			
			// 发送对象
			var zNodes=[
					<c:forEach items="${orglist}" var="org">{id:'${org.id}', pId:'${org.pId}', isParent:'${org.isParent}', name:"${org.pId ne 0?org.name:'全部人员'}"},
		            </c:forEach>];
			// 初始化树结构 
			tree = $.fn.zTree.init($("#objectTree"), setting, zNodes);
			// 默认展开一级节点
			var nodes = tree.getNodesByParam("level", 0);
			for(var i=0; i<nodes.length; i++) {
				tree.expandNode(nodes[i], true, false, false);
			}
			// 异步加载子节点
			var nodesOne = tree.getNodesByParam("isParent", true);
			for(var j=0; j<nodesOne.length; j++) {
				tree.reAsyncChildNodes(nodesOne[j],"!refresh",true);
			}
			// 默认选择节点
			selectCheckNode();
		});
		// 默认选择节点
		function selectCheckNode(){
			var ids = "${notice.objIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", "u_"+ids[i]);
				try{tree.checkNode(node, true, true);}catch(e){}
			}
		}
     </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/noticelist">通知列表</a></li>
		<li class="active"><a href="#">通知${notice.id != 0 ?'查看':'发布'}</a></li>
	</ul><br/>

	<form:form modelAttribute="notice" action="${ctx}/notice/push" method="post" id="mf" class="form-horizontal">
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="title"><font color="red">*</font>标题:</label>
			<div class="controls">
				<form:input path="title" maxlength="20" datatype="*"
			     htmlEscape="false" nullmsg="请输入标题！" errormsg="标题最多20个字符！" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="content"><font color="red">*</font>内容:</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="8" class="input-xlarge"
				    maxlength="255" datatype="*" nullmsg="请输入内容！" />
			</div>
		</div>
		
 		<div class="control-group">
			<label class="control-label"><font color="red">*</font>发送对象:</label>
			<div class="controls">
				<div id="objectTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="objIds" id="objIds"/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${notice.id == 0}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="发布"/>&nbsp;
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<form:hidden path="id"/>
	</form:form>
</body>
</html>