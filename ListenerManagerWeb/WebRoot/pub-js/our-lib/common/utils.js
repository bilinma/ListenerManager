/**
 * @description 数组相减，如[1,3,5]-[1,5,7]=[3]
 */
function arrayMinus(srcArr,tarArr){
	$.each(srcArr,function(i,n){
		srcArr[i]=n.toString();
	});
	$.each(tarArr,function(i,n){
		tarArr[i]=n.toString();
	});
	return $.grep(srcArr,function(n,i){
		return $.inArray(n,tarArr)==-1;
	});
}

/**
 * @description 获取URL参数数组功能
 */
var urlParam = (function (url){
	if(url==null)url = location.search;
    var qs = url.substring(url.indexOf("?")+1,url.length).split("&");
    var get = {};
    for (i=0; j=qs[i]; i++){ 
        get[j.substring(0,j.indexOf("="))] = j.substring(j.indexOf("=")+1,j.length); 
    }
    return get;
})();

/**
 * @description 模拟JAVA的StringBuffer,提升JS拼接字符串的效率.
 */
function StringBuffer() {
    this.__strings__ = new Array;
}

StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
};

StringBuffer.prototype.toString = function () {
    return this.__strings__.join("");
};
/**
 * @description <pre>通过Ajax调用后台Action,判断Action是否抛出了异常,
 * 如果抛出了异常将异常页面的信息以弹出DIV的形式显示出来.用到了Jquery的两个弹出DIV插件
 * 如果传递了两个参数将显示异常页面中的"异常详情",一个参数只显示异常的message.<br/>
 * 传递两个参数时页面引入simpleModal插件相关文件,一个参数引入jquery.alerts插件相关文件.</pre>
 * TODO 以后要删除掉
 */
function isThrowException(msg,isMoreMsg) {
	var sb = new StringBuffer();
	if(arguments.length == 2 && msg.indexOf("isErrorPage=\"true\"") > -1){
		//将异常页面中的"返回"按钮移除
		var re = /<input type=\"button\" id=\"retbutton\".+><\/input>/gi;
		msg = msg.replace(re, "");
		//把整个异常页面以弹出DIV显示出来
		$.modal(msg,{containerCss:{width:document.body.clientWidth / 2 + 200,height:document.body.clientHeight-150}});
		return true;		
	}
	else if(arguments.length == 1 && msg.indexOf("isErrorPage=\"true\"") > -1){
		//用正则表达式取出页面中的异常message
		var re = /<p.+>([\s\S]*)<\/p>/gi;
		re.test(msg,{containerCss:{}});
		sb.append("<font color='red'>查询过程中出现异常:</font><br/>");
		sb.append("<textarea cols='30' rows='6'>");
		sb.append(RegExp.$1.trim() + "</textarea>");
		jAlert(sb.toString(),"异常");
		return true;
	}
	else{
		return false;
	}
}

/**
 * @description <pre>通过Ajax调用后台Action,Action抛出异常时,
 * 将异常信息以弹出DIV的形式显示出来.用到了Jquery的simpleModal插件
 * @param {} jsonObj 后台返回的JSON对象
 */
function promptExcetionMsg(jsonObj){
		var sb = new StringBuffer();
		sb.append("<center><h2><font color='red'>处理过程中出现了异常</font></h2></center>");
		sb.append("<br/><p class='red_long'>异常信息:" + jsonObj.exceptionDto.exceptionMsg + "</p>");
		sb.append("<br/><p class='red_long'> 异常编码: " + jsonObj.exceptionDto.exceptionCode + "</p>");
		sb
				.append('<br/><br/><input type="button" id="moreExceptionButton" class="button" value="详细信息"/>');
		sb.append("<div id='moreExceptionDiv' style='color: red;'>");
		$.modal(sb.toString(), {
					containerCss : {
						width : document.body.clientWidth / 2,
						height : document.body.clientHeight - 150
					}
				});
		var divWidth = document.body.clientWidth / 2;
		var divHeight = document.body.clientHeight - 300;
		if($.browser.msie){
			 divWidth = document.body.clientWidth / 2 - 50;
			 divHeight = document.body.clientHeight - 350;
		}
		$("#moreExceptionDiv").hide().css({
					overflow : 'auto',
					width : divWidth,
					height : divHeight
				}).text(jsonObj.exceptionDto.exceptionStack);
		$("#moreExceptionButton").click(function() {
					$("#moreExceptionDiv").toggle();
				});
}