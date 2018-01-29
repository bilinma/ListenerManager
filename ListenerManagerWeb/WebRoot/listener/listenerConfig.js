window.onload = win_onload;
var dom;
var delData = new Array();
var delFlag = 1 ; //  0表示删除 ，1表示除删除其它操作
var delNum = 0;
//页面加载时调用方法
function win_onload() {
	$.get('listenerConfig!checkListenerStateForConfig.action', null, createListenerConfigInfo, null);
}

function formatTable(tableId) {
	$("#" + tableId + " tr:first").addClass("greytable");
	$("#" + tableId + " tr:not(:first)").addClass("list-white");
}

function createListenerConfigInfo(dom){
	if (dom != null)
	{
		var table = $("#listenerSetUpInfo");
		var listeners = $(dom).find("root").find("listener");
		listeners.each(function(i, domEle){
			v_domEle = $(domEle);
			var nameDesc = v_domEle.children('description').text();
			var threadCount = v_domEle.children('threadCount').text();
			var className = v_domEle.children('className').text();
			var msgId = v_domEle.children('msgId').text();
			table.append(insertRowAll(nameDesc, className, threadCount, 1, msgId));
		});
		formatTable("listenerSetUpInfo");
		endProcess();
	}
}

function createListenerTd(name, nameDesc, type){
	var td = $("<td/>");
	td.append($("<input type='hidden'/>").attr("name", name).attr("value", nameDesc));
	if(type == 1){
		td.append($("<label/>").text(nameDesc));
		td.append($("<input type='text'style='border:2px inset;width:100%; display:none'/>").attr("name", name).attr("value", nameDesc));
	}else{
		td.append($("<label/>").text(nameDesc).css("display", "none"));
		td.append($("<input type='text'style='border:2px inset;width:100%;'/>").attr("name", name).attr("value", nameDesc));
	}
	return td;
}

//创建对配置进行操作的表格
function createOPTd(msgId, type){
	var modelBt = $("<input type='button' />").attr("disabled", msgId=="0" ? true : false).addClass(msgId=="0" ? "disable-button" : "button");
	var firstBt = modelBt.clone().attr("name", "modifyButton").attr("value", (type == 0 ? "确定" : "修改")).bind("click", modifyRow);
	var secondBt = modelBt.clone().attr("name", "deleButton").attr("value", "删除").bind("click", deleteRow);
	return $("<td/>").append(firstBt).append(secondBt);	
}
//type指明是修改已有的行还是新增一行记录
//type=0新增；type=1修改
function insertRowAll(nameDesc, className, threadCount, type, msgId){
	var tr = $("<tr/>").attr("align", "center");
	tr.append(createListenerTd("listenerNameDesc", nameDesc, type));
	tr.append(createListenerTd("listenerClass", className, type));
	tr.append(createListenerTd("threadCount", threadCount, type));
	tr.append(createOPTd(msgId, type));
	return tr;
}

function insertRow(){
	var table = $("#listenerSetUpInfo");
	table.append(insertRowAll("", "", "", 0, 1));
	formatTable("listenerSetUpInfo");
}

function modifyRow() {   	
	var v_this = $(this);
	var value;
	v_this.parent().parent().children().each(function(i, domEle){
		var showCtrl;
		var hideCtrl;
		if(v_this.attr("value") == "修改"){
			value = "确定";
			hideCtrl = $(domEle).find("label");
			showCtrl = $(domEle).find("input[type='text']");
			showCtrl.attr("value", hideCtrl.text());
		}else{
			value = "修改";
			showCtrl = $(domEle).find("label");
			hideCtrl = $(domEle).find("input[type='text']");
			showCtrl.text(hideCtrl.attr("value"));
		}
		hideCtrl.css("display", "none");
		showCtrl.css("display", "block");
	});
	v_this.attr("value", v_this.attr("value") == "修改" ? "确定" : "修改");		
}

function deleteRow() {
	var delTr = $(this).parent().parent();
	var desc = delTr.find("input[name='listenerNameDesc'][type='hidden']").attr("value");
	var listenerClass = delTr.find("input[name='listenerClass'][type='hidden']").attr("value");
	var threadCount = delTr.find("input[name='threadCount'][type='hidden']").attr("value");
	if(desc != "" && listenerClass != "" && threadCount != ""){
		delFlag = 0;
		delData[delNum] = +delFlag + "|" + desc + "|" + listenerClass + "|" + threadCount +"|";	
		delNum++;
	}
	$(this).parent().parent().remove();
	return;
	var deleButton = document.getElementsByName("deleButton");
	
	for(var i=0;i<deleButton.length;i++){
		if (deleButton[i] == this1){
			var tr = this1.parentNode.parentNode;
			var desc = tr.cells[0].children(0).value;
			var listenerClass = tr.cells[1].children(0).value;
			var threadCount = tr.cells[2].children(0).value;
			if(desc != "" && listenerClass != "" && threadCount != ""){
				delFlag = 0;
				delData[delNum] = +delFlag + "|" + desc + "|" + listenerClass + "|" + threadCount +"|";	
				delNum++;
			}
			break;
		} 
	}
}

function submitRow(){
	var tb1 = document.getElementById("listenerSetUpInfo");
	var dataInfoArray = new Array();
	var j = 0;
	dataInfoArray = dataInfoArray.concat(delData);
	var modifyAddData = new Array();
	$("#listenerSetUpInfo tr:not(:first)").each(function(i, domEle){
		var v_domEle = $(domEle);
		var newNameDesc = v_domEle.find("input[name='listenerNameDesc'][type='text']").attr("value");
		var newClassName = v_domEle.find("input[name='listenerClass'][type='text']").attr("value");
		var newThreadCount = v_domEle.find("input[name='threadCount'][type='text']").attr("value");
		var oldNameDesc = v_domEle.find("input[name='listenerNameDesc'][type='hidden']").attr("value");
		var oldClassName = v_domEle.find("input[name='listenerClass'][type='hidden']").attr("value");
		var oldThreadCount = v_domEle.find("input[name='threadCount'][type='hidden']").attr("value");
		if(newNameDesc == null || newNameDesc.trim() == ""){
			alert('侦听名称描述为空!请注意填写正确的描述 ！');
			return;
		}
		if(newClassName == null || newClassName.trim() == ""){
			alert('侦听类为空!请注意填写正确的侦听类 ！');
			return;
		}
		if(newThreadCount == null || newThreadCount.trim() == ""){
			alert('侦听起动线程数量为空或 零!请注意填写正确有效的线程数量 ！');
			return;
		}
		var patrn=/^[+\-]?\d+(.\d+)?$/;
		if (!patrn.exec(newThreadCount.trim())) {
			alert('线程数['+newThreadCount.trim()+' ]' + '输入不合法字符，应为数字!');
			return;
		}
		
		$.get('listenerConfig!checkListenerClass.action', {newClassName : newClassName}, function(retMsg){
			if(retMsg != ""){
				alert(retMsg);
				return;
			}
		}, null);
		if(oldNameDesc != newNameDesc  || oldClassName != newClassName || oldThreadCount != newThreadCount) {
			delFlag = 1;
			modifyAddData[j] = delFlag + "|" + encodeURIComponent(newNameDesc.trim()) + "|" + newClassName.trim() + "|" + newThreadCount.trim() + "|";
			j++;
		 }
	});
	dataInfoArray = dataInfoArray.concat(modifyAddData);
	if(dataInfoArray.length ==0){
		alert('没有任何修改的配置信息 ！');
		return;
	}
	$.get('listenerConfig!saveListenerConfig.action', {dataInfoArray : dataInfoArray.join(",")}, saveAndClose, "text");
	
}

function saveAndClose(msg){
	if(msg == 'success'){
		alert("侦听配置信息保存成功 ！");
		window.close();
	}
}

function cannelRow(){
	window.close();
}

function startProcess(){
	load.style.display="";
}

function endProcess(){
	load.style.display="none";
}