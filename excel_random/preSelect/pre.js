/**
 * 
 */
$(function(){
			var contextPath = $("#contextPath").val();
			var validProjectName = false;
			var validAgentNums = false;
			var projectNameInput = $("#projectName");
			var agentNumsInput = $("#agentNums");
			var randomSelectButton = $("#randomSelect");
			var deleteAgentButton = $("#deleteAgent");
			var deleteResultButton = $("#deleteResult");
			var addAgentButton = $("#addAgent");
			var projectName = "";
			var agentNums = 0;
			var checkNameAjax = true;
			var checkNumAjax = true;
			var randomSelectAjax = true;
			var deleteAgentAjax = true;
			var deleteResultAjax = true;
			var addAgentAjax = true;
			/**
			 * ���ر�ѡ�����б�
			 */
			function agentListXhr() {
				$.get(contextPath+"/PreRandomSelectAgentList",function(data){
					$("#agentList").html(data);
				});	
			}
			agentListXhr();
			/**
			 * �����ѳ�ȡ������Ŀ����
			 */
			function resultListXhr() {
				$.get(contextPath+"/PreRandomSelectDoneProject",function(data){
					$("#doneProject").html(data);
				});	
			}
			resultListXhr();
			//�ж�����ļƻ�����Ƿ��Ѿ�����
			projectNameInput.change(function(){
				projectName = projectNameInput.val();
				if(projectName==""){
					alert("��Ŀ������Ϊ�գ�");
					return false;
				}
				if(!checkNameAjax){
					return false;
				}else{
					checkNameAjax = false;
					$.get(contextPath+"/PreRandomSelectPlanIdCheck",
							{"projectName":encodeURI(projectName)},function(data){
								if(data==0){
									$("#projectNameRight").css("color","red").text("����Ŀ�Ѿ���ȡ�����������");
									validProjectName = false;
								}else if(data==1){
									$("#projectNameRight").css("color","blue").text("����Ϊ�˼ƻ���ų�ȡ�������!");
									validProjectName = true;
								}
								checkNameAjax = true;
							});
				}
			});
			
			//�ж�����������Ƿ񳬹�����������������
			agentNumsInput.change(function(){
				agentNums = agentNumsInput.val();
				if(isNaN(agentNums)){
					$("#agentNumsRight").text("������һ����Ч���֣�");
					return false;
				}else if(agentNums==0){
					$("#agentNumsRight").text("���벻��Ϊ�㣡");
					return false;
				}
				if(!checkNumAjax){
					return false;
				}else{
					checkNumAjax = false;
					$.get(contextPath+"/PreRandomSelectAgentNumsCheck",
							{"agentNums":agentNums},function(data){
								if(data==0){
									$("#agentNumsRight").css("color","red").text("��������������������������");
									validAgentNums = false;
								}else if(data==1){
									$("#agentNumsRight").css("color","blue").text("��Ч����");
									validAgentNums = true;
								}
								checkNumAjax = true;
							});
				}
			});
			
			randomSelectButton.click(function(){
				if(!projectName||projectName==""){
					alert("�ƻ���Ų���Ϊ��!");
					return false;
				}
				if(!agentNums||agentNums==""){
					alert("��ȡ��������Ϊ�գ�");
					return false;
				}
				if(!randomSelectAjax){
					return false;
				}else if(validProjectName&&validAgentNums){
						randomSelectAjax = false;
						$.get(contextPath+"/PreRandomSelectAgentServlet",{"projectName":encodeURI(projectName),"agentNums":agentNums},function(data){
							$("#selectResultList").html(data);
							randomSelectAjax = true;
							resultListXhr();
						});
				}
				
			});
			
			randomSelectButton.css("cursor","pointer");
			
			$("#back").click(function(){
				window.location.href="../index.jsp";
			});
			$("#refresh").click(function(){
				window.location.reload();
			});
			deleteAgentButton.click(function (){
				var checkboxes = $("#agentForm input:checkbox:checked");
				if(checkboxes.length==0){
					alert("������ѡ��һ�");
					return false;
				}
				if(!deleteAgentAjax){
					return false;
				}else{
					deleteAgentAjax = false;
					$.ajax({
						type:"post",
						url:contextPath+"/PreRandomSelectAlterFile",
						data:$("#agentForm").serialize(),
						success:function(data){
							if(data==1){
								agentListXhr();
								alert("ɾ���ɹ���");
							}
							deleteAgentAjax = true;
						}
					});
				}
				
				checkboxes.each(function(){
					$(this).parent().remove();
				});
			});
			
			deleteResultButton.click(function (){
				var checkboxes = $("#resultForm input:checkbox:checked");
				if(checkboxes.length==0){
					alert("������ѡ��һ�");
					return false;
				}
				if(!deleteResultAjax){
					return false;
				}else{
					deleteResultAjax = false;
					$.ajax({
						type:"post",
						url:contextPath+"/PreRandomSelectAlterFile",
						data:$("#resultForm").serialize(),
						success:function(data){
							if(data==1){
								resultListXhr();
								alert("ɾ���ɹ���");
							}
							deleteResultAjax = true;
						}
					});
				}
				
				checkboxes.each(function(){
					$(this).parent().remove();
				});
			});
			
			addAgentButton.click(function(){
				var agentName = $("#addAgentForm input[name='agentName']").val();
				var data = $("#addAgentForm").serialize();
				if(agentName==null||agentName.trim()==""){
					alert("��λ���Ʋ���Ϊ�գ�");
					return false;
				}
				if(!addAgentAjax){
					return false;
				}else{
					addAgentAjax = false;
					$.ajax({
						type:"post",
						url:contextPath+"/PreRandomSelectAddAgent",
						data:data,
						success:function(data){
							if(data==1){
								agentListXhr();
								alert("��ӳɹ���");
								$("#addAgentForm input").val("");
							}
							addAgentAjax = true;
						}
					});
				}
			});
		});