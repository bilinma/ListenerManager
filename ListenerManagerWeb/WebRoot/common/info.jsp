<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<SCRIPT type="text/javascript">
function resetWindow(){
	if(window.history.length > 1){
		window.history.back();
	}
	else{
		window.close();
	}
}
</SCRIPT>	
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<LINK rel="stylesheet" href="../theme/css.css" type="text/css">
<title><s:property value="#request.commonMsg"/></title>
</head>
<body topmargin="0" class="scrollbar">
	<table width="100%" border="0" cellspacing="0" cellpadding="10">
		
		<tr>
			<td align="center" class="blackbold">信息提示页面 </td>
		</tr>
		<tr>
			<td align="center" background="../images/dotline.gif" class="blackbold">&nbsp;</td>
		</tr>
		<tr>
			<td>
				<span class="black1">提示信息: </span> 
				<p class="red_long">
					<s:property value="#request.commonMsg"/>
				</p>
			</td>
		</tr>
		<s:iterator value="#request.coIds">
			<tr>
				<td>
				<!--  
			    <s:url id="url" action="order/orderQuery!queryCustOrderByCoId.action">
		        <s:param name="coId"><s:property/></s:param>
		       	</s:url>
		       	-->
				<a href="../order/orderQuery!queryCustOrderByCoId.action?coId=<s:property/>"><s:property/></a>    	
				</td>
			</tr>
		</s:iterator>		
		<tr>
			<td align="right">
				<input type="button" class="button" value="后 退" onclick="resetWindow();">
			</td>
		</tr>
	</table>
</body>
</html>