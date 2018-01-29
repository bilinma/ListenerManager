<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    <title>侦听控制台</title>
	<jsp:include flush="true" page="../common/crossDomainUrl.jsp"></jsp:include>
	<jsp:include flush="true" page="../common/setSession.jsp"></jsp:include>
	<jsp:include flush="true" page="../common/setDomain.jsp"></jsp:include>		
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/json/json2.js"></script>
	<script type="text/javascript" src="listenerConsole.js"></script>
	<link rel="stylesheet" href="../theme/css.css" type="text/css"></link>
	</head>
  <body topmargin="0" class="scrollbar" style="text-align:center;">
	<div class="current_path">
		<h3>侦听管理   &gt;&gt; 侦听控制台</h3>
	</div>
	
	<div style="width:518px; *width:539px; _width:539px; height:350px;  background:url(../images/lis_bg.gif) no-repeat 0 32px; text-align:center; margin:0 auto; padding-left:10px; padding-right:11px; margin-top:30px;">    
	<img src="../images/lis_title.gif" style="float:left;"/>
	<table width="100%" align="center" height="235"
			cellpadding="1" cellspacing="0" style="border-collapse: collapse; margin:0 auto; clear:both;">
		<tbody>
		<tr>
			<td height="40" style="padding-top:8px;">
				<img border="0" id="all" align="left" src="../images/wtable_select_all.gif" alt="全选">
				<img border="0" id="cancel" align="left" src="../images/wtable_deselect_all.gif" alt="取消选择所有项">
				<div style="float:right; margin-right:10px; display:inline;">
				<input type="button" id="btnStart" name="btnStart" value="启动" class="button">
				<input type="button" id="btnStop" name="btnStop" value="停止" class="button">
				<input type="button" id="btnSet" name="btnSet" value="设置" class="button">
				</div>
		  </td>
		</tr>
		<tr>
			<td valign="top">
				<table  id="tabListenerInfo"  width="100%" align="center"  height="100%"  cellpadding="0" cellspacing="0" style="border-collapse:collapse; border-top:1px solid #e1e1e1;">
				<tbody>
				<tr class="greytable" align="center">
					<td height="28">选择</td>
					<td height="28">名称</td>
					<td height="28">应用程序状态<img id="initImg" border="0" src="../images/refresh.gif">					
					</td>
				</tr>
				</tbody>
			  </table>
			</td>
		</tr>
		<tr>
			<td>
				<div id="load" style=" font-size:12px;color:#666666; margin-left:230px;">
				<img src="../images/load.gif" width="50" height="50"> 
				</div>
			</td>
		</tr>
		</tbody>
	</table>
	</div>

  </body>
</html>
