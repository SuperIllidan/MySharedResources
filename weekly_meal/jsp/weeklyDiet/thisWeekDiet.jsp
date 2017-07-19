<%@page import="com.hidy.hdoa6.info.web.EntryInfoBean"%>
<%@page import="com.hidy.hdoa6.util.HdUtil"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="../sessioncheck.jsp"%>
<%
	String contextPath = request.getContextPath();
	String IDLM = request.getParameter("IDLM")==null?"":request.getParameter("IDLM");
	String RoleIDStr = session.getAttribute("RoleIDStr").toString();
	if (HdUtil.isNullOrEmpty(IDLM)||!EntryInfoBean.isPutEntity(Integer.parseInt(IDLM),RoleIDStr)) {
		response.sendRedirect(contextPath+"/error.jsp?");
		return;
	}	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link type="text/css" rel="stylesheet" href="js_css/bootstrap.min.css" />
		<script type="text/javascript" src="js_css/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="js_css/weeklyDiet.js"></script>
		<style>
			.container-fluid{
				background-color:rgb(248,248,255);
				
			}
			h4{
				margin-left:1%;
			}
		</style>
		<title>本周食谱</title>
	</head>
	<body>
		<h4 class="text-left text-primary" id="back"><a href="#"><button class="btn btn-info">返回首页</button></a></h4>
		<h1 class="text-center text-primary"><button class="btn btn-block btn-info"><span style="font-size:30px">每周食谱</span></button></h1>
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-1">
					<table>
						<tr height="40"></tr>
						<tr>
							<td style="vertical-align:middle;">
								<ul class="list-group">
									<li class="list-group-item"><button id="viewB" style="display:inline-block" class="view btn btn-info">早餐</button></li>
									<li class="list-group-item"><button id="viewL" style="display:inline-block" class="view btn btn-info">午餐</button></li>
									<li class="list-group-item"><button id="viewD" style="display:inline-block" class="view btn btn-info">晚餐</button></li>
									<li class="list-group-item"><button id="viewA" style="display:inline-block" class="view btn btn-info">加餐</button></li>
								</ul>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-md-10">
					<div>
						<table class="order text-center table table-bordered table-striped table-hover table-condensed" id="breakfast">
							<colgroup>
								<col style="">
							</colgroup>
							<tr class="danger"><td class="title" align="center" colspan="2" style="font-size:26px;font:bold">早餐列表</td></tr>
							<tr class="warning dayOfMonth"><td style='font-size:18px;font:bold' align='center'>日期</td></tr>
							<tr class="active dayOfWeek"><td style='font-size:18px;font:bold' align='center'>星期</td></tr>
						</table>
					</div>
					<div>
						<table class="order text-center table table-bordered table-striped table-hover table-condensed" id="lunch">
							<tr class="danger"><td class="title" align="center" colspan="7" style="font-size:26px;font:bold">午餐列表</td></tr>
							<tr class="warning dayOfMonth"><td style='font-size:18px;font:bold' align='center'>日期</td></tr>
							<tr class="active dayOfWeek"><td style='font-size:18px;font:bold' align='center'>星期</td></tr>
						</table>
					</div>
					<div>
						<table class="order text-center table table-bordered table-striped table-hover table-condensed" id="dinner">
							<tr class="danger"><td class="title" align="center" colspan="7" style="font-size:26px;font:bold">晚餐列表</td></tr>
							<tr class="warning dayOfMonth"><td style='font-size:18px;font:bold' align='center'>日期</td></tr>
							<tr class="active dayOfWeek"><td style='font-size:18px;font:bold' align='center'>星期</td></tr>
						</table>
					</div>
					<div>
						<table class="order text-center table table-bordered table-striped table-hover table-condensed" id="additional">
							<tr class="danger"><td align="center" colspan="2" style="font-size:26px;font:bold">加餐列表</td></tr>
							<tr class="warning"><td style='font-size:18px;font:bold' align='center'>日期</td><td id="additionalDayOfMonth">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
							<tr class="active"><td style='font-size:18px;font:bold' align='center'>星期</td><td id="additionalDayOfWeek">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="contextPath" value=<%=contextPath %>>
	</body>
</html>