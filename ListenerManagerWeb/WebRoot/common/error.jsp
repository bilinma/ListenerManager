<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<LINK rel="stylesheet" href="../theme/css.css" type="text/css">	
<title>异常信息</title>
<script type="text/javascript"
			src="../pub-js/third-lib/js-common-lib/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript"
			src="../pub-js/third-lib/js-common-lib/jquery/simpleModal/js/jquery.simplemodal-1.3.3.js"></script>				
<SCRIPT type="text/javascript">
function resetWindow(){
	if(window.history.length > 1){
		window.history.back();
	}
	else{
		window.close();
	}
}
function viewInfo() {
	$("#moreErrorDiv").toggle();
}

</SCRIPT>		
</head>
<!-- 其他非系统内部自定义抛出异常用的页面 -->
<body topmargin="0" class="scrollbar">
	<table width="100%" border="0" cellspacing="0" cellpadding="10">
		<tr>
			<td align="center" class="blackbold">处理过程中出现异常 </td>
		</tr>
		<tr>
			<td align="center" background="../images/dotline.gif" class="blackbold">&nbsp;</td>
		</tr>
		<tr>
			<td>
				<s:if test="#request.exceptionDto != null">
					<p class="red_long">
						异常信息:
						<s:property value="#request.exceptionDto.exceptionMsg" />
					</p>
					<p class="red_long">
						异常编码:
						<s:property value="#request.exceptionDto.exceptionCode" />
					</p>					
				</s:if>
				<s:else>
					<p class="red_long">
						异常信息:
						<s:property value="exception.message" />
					</p>				
				</s:else>				
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="button" id="detailbutton" class="button" value="详细信息" onclick="viewInfo()"></input>
				<input type="button" id="retbutton" class="button" value="返回" onclick="resetWindow()"></input>
			</td>
		</tr>
	</table>
	<TABLE width="100%"><tr><td style="color: red;">
	<DIV style="overflow-y:auto;display:none" id="moreErrorDiv">
		<s:property value="exceptionStack" />
	</DIV>
	</td></tr></TABLE>
</body>
</html>