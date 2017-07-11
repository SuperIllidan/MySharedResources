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
		<title>Ԥ��ȡ�������</title>
	</head>
	<body>
		<div class="container-fluid">
			<h4 class="text-left text-primary" id="back"><a href="#">������ҳ</a></h4>
			<h4 class="text-right text-primary" id="refresh"><a href="#">ˢ��ҳ��</a></h4>
			<h1 class="text-center text-primary">�������Ԥ��ȡ</h1>
			<div class="row">
				<div class="col-md-3" id="agentListDiv">
					<p>��ѡ�����б�</p>
					<form id="agentForm" method="post">
						<ul id="agentList"></ul>
						<input type="hidden" name="list" value="list">
					</form>	
				</div>
				<div class="col-md-6" id="selectAndShowDiv">
					<label>��Ϣ��д��</label><br>
					<label for="projectName">���뱾�γ�ȡ���ƣ�<input type="text" id="projectName" name="projectName" placeholder="��Ŀ����"></label><span id="projectNameRight"></span><br>
					&nbsp;<br>
					<label for="agentNums">���뱾�γ�ȡ������<input type="text" id="agentNums" name="agentNums" placeholder="��ȡ����"></label><span id="agentNumsRight"></span><br>
					����������������������������������������������������������������������������������<br>
					<label>��ȡ�����</label>
					<ul id="selectResultList"></ul>
				</div>
				<div class="col-md-3" id="doneSelectionDiv">
					<p>�ѳ�ȡ��Ŀ�б�</p>
					<form id="resultForm" method="post">
						<ul id="doneProject"></ul>
						<input type="hidden" name="result" value="result">
					</form>
				</div>
			</div>
			<dir class="row">
				<div class="col-md-2">
					<button class="btn btn-block btn-danger" title="ѡ�л��������ɾ��" id="deleteAgent">ɾ��</button>
				</div>
				<div class="col-md-1">
					<button style="display:none;"></button>
				</div>
				<div class="col-md-6">
					<button class="btn btn-block btn-primary" id="randomSelect" title="�����ȡ">��ʼ��ȡ</button>
				</div>
				<div class="col-md-1">
					<button style="display:none;"></button>
				</div>
				<div class="col-md-2">
					<button class="btn btn-block btn-danger" title="ѡ��һ����Ŀ�����ɾ��" id="deleteResult">ɾ��</button>
				</div>
			</dir>
			<div id="addAgentDiv">
				<div class="row">
					<form id="addAgentForm" >
						<div class="col-md-2">
							<p class="text-primary text-center">��λ����</p></br>
							<input type="text" name="agentName">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">��λ��ַ</p></br>
							<input type="text" name="agentAddress">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">��ϵ��</p></br>
							<input type="text" name="agentContact">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">ְ��</p></br>
							<input type="text" name="contactPosition">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">�ƶ��绰</p></br>
							<input type="text" name="contactMobile">
						</div>
						<div class="col-md-2">
							<p class="text-primary text-center">�̶��绰</p></br>
							<input type="text" name="contactTelephone">
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-4">
						<button style="display:none"></button>
					</div>
					<div class="col-md-4">
						<button class="btn btn-block btn-primary" id="addAgent">���</button>
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