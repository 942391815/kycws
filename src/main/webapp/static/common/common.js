// 根据选择的组织结构动态生成所属的人员信息下拉框
function setSelect(fromSelVal, toSelId) {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var contextPath = pathName.substr(0,index+1);
	jQuery.ajax({
		url : contextPath + "/user/list",
		cache : false,
		async : false,
		data : "orgId=" + fromSelVal,
		success : function(data) {
			createSelectObj(data, toSelId);
		}
	});
}
function createSelectObj(data, toSelId) {
	var obj = document.getElementById(toSelId);
	obj.innerHTML = "";
	var nullOp = document.createElement("option");
	nullOp.setAttribute("value", "");
	nullOp.appendChild(document.createTextNode("请选择"));
	obj.appendChild(nullOp);
	var arr = jsonParse(data);
	if (arr != null && arr.length > 0) {
		for ( var o in arr) {
			var op = document.createElement("option");
			op.setAttribute("value", arr[o].id);
			op.appendChild(document.createTextNode(arr[o].name));
			obj.appendChild(op);
		}
	}
}

// 屏蔽退格键的返回上一页操作
$(document).keydown(function(e) {
	// 获取事件源
	var target = e.target;
	// 获取标签属性
	var tag = e.target.tagName.toUpperCase();
	// 当backspace按下
	if (e.keyCode == 8) {
		if ((tag == 'INPUT' && !$(target).attr("readonly"))
				|| (tag == 'TEXTAREA' && !$(target).attr("readonly"))) {
			if ((target.type.toUpperCase() == "RADIO")
					|| (target.type.toUpperCase() == "CHECKBOX")) {
				// backspace无效
				return false;
			} else {
				// backspace生效
				return true;
			}
		} else {
			// backspace无效
			return false;
		}
	}
});

$(document).ready(function() {
	//所有下拉框使用select2
	$("select").select2();
});


//显示加载框
function loading(mess){
	top.$.jBox.tip.mess = null;
	top.$.jBox.tip(mess,'loading',{opacity:0});
}


//确认对话框
function confirmx(mess, href){
	top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
		if(v=='ok'){
			loading('正在提交，请稍等...');
			location = href;
		}
	},{buttonsFocus:1});
	top.$('.jbox-body .jbox-icon').css('top','55px');
	return false;
}

//获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}