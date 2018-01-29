<%-- <%@ page language="java" import="bss.common.util.LoginedStaffInfo" pageEncoding="GB18030"%>
<script>
    <%
  		if(request.getSession().getAttribute("loginedStaffInfo") == null) {
		      LoginedStaffInfo staffInfo = new LoginedStaffInfo();
		      staffInfo.setStaffId(new Long(1001));
			  staffInfo.setStaffOnlineSerial("87A94F97C30654159717B668D372A0E0");
		      staffInfo.setAreaId(new Integer(21));
		      staffInfo.setAreaName("昆明市区");
		      staffInfo.setStaffName("昆明电信");
		      staffInfo.setStaffNumber("01332");		      
			  request.getSession().setAttribute("loginedStaffInfo", staffInfo);
		}
  		LoginedStaffInfo staffInfo = (LoginedStaffInfo) request.getSession().getAttribute("loginedStaffInfo");  
	%>
	
	var g_loginedStaffInfo = {};
	g_loginedStaffInfo.staffId = '<%=staffInfo.getStaffId().toString()%>';
	g_loginedStaffInfo.staffSerial = '<%=staffInfo.getStaffOnlineSerial()%>';
	g_loginedStaffInfo.staffAreaId = '<%=staffInfo.getAreaId()%>';
	g_loginedStaffInfo.staffAreaName = '<%= staffInfo.getAreaName()%>';
	g_loginedStaffInfo.staffName = '<%=staffInfo.getStaffName()%>';
	g_loginedStaffInfo.staffNumber = '<%=staffInfo.getStaffNumber()%>';
	g_loginedStaffInfo.channelId = '';
	g_loginedStaffInfo.channelName = '';
	<%if(staffInfo.getChannelId() != null) {%>
		g_loginedStaffInfo.channelId = '<%=staffInfo.getChannelId().toString()%>';
		g_loginedStaffInfo.channelName = '<%=staffInfo.getChannelName()%>';
	<%}%>

</script>
 --%>
