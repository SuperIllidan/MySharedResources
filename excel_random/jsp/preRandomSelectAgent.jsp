<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%String contextpath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link rel="stylesheet" href="preSelect/bootstrap.min.css" />
		<link rel="stylesheet" href="preSelect/pre.css" />
		<script type="text/javascript" src="preSelect/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="preSelect/pre.js" ></script>
		<title>预抽取代理机构</title>
	</head>
	<body>
		<div class="container-fluid">
			<h4 class="text-left text-primary" id="back"><a href="#">返回首页</a></h4>
			<h4 class="text-right text-primary" id="refresh"><a href="#">刷新页面</a></h4>
			<h1 class="text-center text-primary">代理机构预抽取</h1>
			<div class="row">
				<div class="col-md-3" id="agentListDiv">
					<p>备选机构列表：</p>
					<form id="agentForm" method="post">
						<ul id="agentList"></ul>
						<input type="hidden" name="list" value="list">
					</form>	
				</div>
				<div class="col-md-6" id="selectAndShowDiv">
					<label>信息填写：</label><br>
					<label for="projectName">输入本次抽取名称：<input type="text" id="projectName" name="projectName" placeholder="项目名称"></label><span id="projectNameRight"></span><br>
					&nbsp;<br>
					<label for="agentNums">输入本次抽取数量：<input type="text" id="agentNums" name="agentNums" placeholder="抽取数量"></label><span id="agentNumsRight"></span><br>
					—————————————————————————————————————————<br>
					<label>抽取结果：</label>
					<ul id="selectResultList"></ul>
				</div>
				<div class="col-md-3" id="doneSelectionDiv">
					<p>已抽取项目列表：</p>
					<form id="resultForm" method="post">
						<ul id="doneProject"></ul>
						<input type="hidden" name="result" value="result">
					</form>
				</div>
			</div>
			<dir class="row">
				<div class="col-md-2">
					<button class="btn btn-block btn-danger" title="选中机构，点击删除" id="deleteAgent">删除</button>
				</div>
				<div class="col-md-1">
					<button style="display:none;"></button>
				</div>
				<div class="col-md-6">
					<button class="btn btn-block btn-primary" id="randomSelect" title="点击抽取">开始抽取</button>
				</div>
				<div class="col-md-1">
					<button style="display:none;"></button>
				</div>
				<div class="col-md-2">
					<button class="btn btn-block btn-danger" title="选中一个项目，点击删除" id="deleteResult">删除</button>
				</div>
			</dir>
			<div id="addAgentDiv">
				<div class="row">
					<form id="addAgentForm" >
						<div class="col-md-2">
							<p class="text-primary text-center">单位名称</p></br>
							<input type="text" name="agentName">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">单位地址</p></br>
							<input type="text" name="agentAddress">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">联系人</p></br>
							<input type="text" name="agentContact">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">职务</p></br>
							<input type="text" name="contactPosition">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">移动电话</p></br>
							<input type="text" name="contactMobile">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">固定电话</p></br>
							<input type="text" name="contactTelephone">
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-4">
						<button style="display:none"></button>
					</div>
					<div class="col-md-4">
						<button class="btn btn-block btn-primary" id="addAgent">添加</button>
					</div>
					<div class="col-md-4">
						<button style="display:none"></button>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="contextPath" value=<%=contextpath %>>
	</body>
</html>