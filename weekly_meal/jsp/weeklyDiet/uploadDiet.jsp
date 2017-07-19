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
		<link type="text/css" rel="stylesheet" href="js_css/uploadDiet.css" />
		<script type="text/javascript" src="js_css/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="js_css/uploadDiet.js"></script>
		<title>�ϴ�ʳ��</title>
	</head>
	<body>
	<div class="container-fluid">
		<h4 class="text-left text-primary" id="back"><a href="#"><button class="btn btn-info">������ҳ</button></a></h4>
		<h2 class="text-center text-primary">�ϴ�ʳ��</h2>
		<div class="row">
			<div id="threeMeals" class="col-md-6">
				<form role="form" action="" enctype="multipart/form-data" method="POST">
					<fieldset>
						<legend>������ѡ������������</legend>
						<div class=""><p>���:<input class="form-control" type="file" name="breakfast"></p></div>
						<div class=""><p>���:<input class="form-control" type="file" name="lunch"></p></div>
						<div class=""><p>���:<input class="form-control" type="file" name="dinner"></p></div>
					</fieldset>
				</form>
			</div>
			<div id="additional" class="col-md-6">
				<form role="form" action="${pageContext.request.contextPath }/WeeklyDietUploadAdditional" enctype="multipart/form-data" method="POST">
					<fieldset>
						<legend>��ѡ��Ӳ�</legend>
						<div class=""><p>�Ӳ�:<input class="form-control" type="file" name="additional"></p></div>
					</fieldset>
				</form>
			</div>
		</div>
		<div class="row" id="twoButtonsDiv">
			<div class="row">
				<div class="col-md-6" id="threeMealsDiv">
					<div class="row">
						<div class="col-md-4">
							<button style="display:none"></button>
						</div>
						<div class="col-md-4">
							<button id="threeMealsButton" class="btn btn-block btn-primary">�ϴ�������˵�</button>
						</div>
						<div class="col-md-4" id="special">
							<button style="display:none"></button>
						</div>
					</div>
				</div>
				<div class="col-md-6" id="additionalDiv">
					<div class="row">
						<div class="col-md-4">
							<button style="display:none"></button>
						</div>
						<div class="col-md-4">
							<button id="additionalButton" class="btn btn-block btn-primary">�ϴ��ӲͲ˵�</button>
						</div>
						<div class="col-md-4">
							<button style="display:none"></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="contextPath" value=<%=contextPath %>>
	</body>
</html>