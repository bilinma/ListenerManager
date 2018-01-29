function initConsolePage()
{
	startProcess();
	$.get("listenerConsole!checkListenerState.action",createListenerListInfo);
}

function startListener()
{
	var selCount = 0;
	var objs = $("input:checked");
	if (objs.size() == 0)
	{
		alert("请选择要操作的侦听！");
		return;
	}
	startProcess();
	objs.each(function(){
		$.get("listenerConsole!startListener.action",{name:$(this).attr("name")});
	});
	//侦听N秒后刷新页面
	if(objs.size()>1){
		setTimeout(initConsolePage,8000);
	}
	else{
		setTimeout(initConsolePage,5000);
	}
}

function stopListener()
{
	var selCount = 0;
	var objs = $("input:checked");
	if (objs.size() == 0)
	{
		alert("请选择要操作的侦听！");
		return;
	}
	
	startProcess();
	objs.each(function(){
		$.get("listenerConsole!stopListener.action",{name:$(this).attr("name")},stopListenerListInfo);
	});
	//侦听N秒后刷新页面
	if(objs.size()>1){
		setTimeout(initConsolePage,8000);
	}
	else{
		setTimeout(initConsolePage,5000);
	}	
}

//初始化侦听页面
function createListenerListInfo(dom)
{
	var src = $("#tabListenerInfo");
	$("#tabListenerInfo .listener_list").remove();
	$(dom).find('root').find('listener').each(function(){
		var v_this = $(this);
		var name = v_this.children('name').text();
		var description = v_this.children('description').text();
		var msgId = v_this.children('msgId').text();
		var msgContext = v_this.children('msgContext').text();
		
		var imgSrc = "../images/unavail.gif";
		if (msgId == "0"){
			imgSrc = "../images/running.gif";
			
		}else if (msgId == "1"){
			imgSrc = "../images/stop.gif";
			
		}else if (msgId == "2"){
			imgSrc = "../images/unavail.gif";
			alert(msgContext);
			return;
		}
		var newrow = $("<tr class='listener_list'/>")
		newrow.append($("<td align='center'/>").append('<input type="checkbox" name="' + name + '" id="chk' + name + '">'));		
		newrow.append($("<td/>").text(description));
		newrow.append($("<td align='center'/>").append('<img id="img' + name + 'State" border="0" src="' + imgSrc + '" alt="状态">'));
		src.append(newrow);
	});
	endProcess();
}

//停止侦听
function stopListenerListInfo(dom)
{
	$(dom).find('root').find('listener').each(function(){
		var v_this = $(this);
		var name = v_this.children('name').text();
		var msgId = v_this.children('msgId').text();
		var msgContext = v_this.children('msgContext').text();
		var imgObj = $("#img" + name + "State");
		if (msgId == "0"){
			imgObj.src = "../images/stop.gif";
		}else if (msgId == "1"){
			alert(msgContext);
		}else if (msgId == "2"){
			alert(msgContext);
		}
	});
}

function setListener()
{
	window.showModalDialog("listenerConfig.jsp?rand="+ Math.random(),"","dialogHeight: 580px; dialogWidth: 710px; center:yes;help:off; resizable:off; scroll:on;status:off;");
	initConsolePage();
}

//全选复选框
function allCheck()
{
	$("input[type=checkbox]").each(function() {
		this.checked = true; 
	});
}

//取消全选复选框
function cancelAllCheck()
{
	$("input[type=checkbox]").each(function() {
		this.checked = false; 
	});
}

//开始处理
function startProcess()
{
	$("#load").show();
}

//结束处理
function endProcess()
{
	$("#load").hide();
}

$( function() {
	//DOM加载完成后注册事件
	$("input,img").slice(1,7).css({cursor:'pointer'});
	$("#all").bind("click",allCheck);
	$("#cancel").bind("click",cancelAllCheck);
	$("#btnStart").bind("click",startListener);
	$("#btnStop").bind("click",stopListener);
	$("#btnSet").bind("click",setListener);
	$("#initImg").bind("click",initConsolePage);
	//初始页面
	initConsolePage();
});

