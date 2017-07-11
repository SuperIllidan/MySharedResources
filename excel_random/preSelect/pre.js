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
			 * 加载备选机构列表
			 */
			function agentListXhr() {
				$.get(contextPath+"/PreRandomSelectAgentList",function(data){
					$("#agentList").html(data);
				});	
			}
			agentListXhr();
			/**
			 * 加载已抽取过的项目名称
			 */
			function resultListXhr() {
				$.get(contextPath+"/PreRandomSelectDoneProject",function(data){
					$("#doneProject").html(data);
				});	
			}
			resultListXhr();
			//判断输入的计划编号是否已经存在
			projectNameInput.change(function(){
				projectName = projectNameInput.val();
				if(projectName==""){
					alert("项目名不能为空！");
					return false;
				}
				if(!checkNameAjax){
					return false;
				}else{
					checkNameAjax = false;
					$.get(contextPath+"/PreRandomSelectPlanIdCheck",
							{"projectName":encodeURI(projectName)},function(data){
								if(data==0){
									$("#projectNameRight").css("color","red").text("此项目已经抽取过代理机构！");
									validProjectName = false;
								}else if(data==1){
									$("#projectNameRight").css("color","blue").text("可以为此计划编号抽取代理机构!");
									validProjectName = true;
								}
								checkNameAjax = true;
							});
				}
			});
			
			//判断输入的数量是否超过代理机构的最大数量
			agentNumsInput.change(function(){
				agentNums = agentNumsInput.val();
				if(isNaN(agentNums)){
					$("#agentNumsRight").text("请输入一个有效数字！");
					return false;
				}else if(agentNums==0){
					$("#agentNumsRight").text("输入不能为零！");
					return false;
				}
				if(!checkNumAjax){
					return false;
				}else{
					checkNumAjax = false;
					$.get(contextPath+"/PreRandomSelectAgentNumsCheck",
							{"agentNums":agentNums},function(data){
								if(data==0){
									$("#agentNumsRight").css("color","red").text("您输入的数量超过了最大数量！");
									validAgentNums = false;
								}else if(data==1){
									$("#agentNumsRight").css("color","blue").text("有效数字");
									validAgentNums = true;
								}
								checkNumAjax = true;
							});
				}
			});
			
			randomSelectButton.click(function(){
				if(!projectName||projectName==""){
					alert("计划编号不能为空!");
					return false;
				}
				if(!agentNums||agentNums==""){
					alert("抽取数量不能为空！");
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
					alert("请至少选择一项！");
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
								alert("删除成功！");
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
					alert("请至少选择一项！");
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
								alert("删除成功！");
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
					alert("单位名称不能为空！");
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
								alert("添加成功！");
								$("#addAgentForm input").val("");
							}
							addAgentAjax = true;
						}
					});
				}
			});
		});