/**
 * uploadDiet
 */
$(function(){
	
	var contextPath = $("#contextPath").val();
	var fileInputs = $("form input:file");
	var threeMealsButton = $("#threeMealsButton");
	var additionalButton = $("#additionalButton");
	var threeMeals = $("#threeMeals form");
	var additional = $("#additional form");
	var canSubmit = "0";
	
	$("#back").click(function(){
		window.location.href="../index.jsp";
	});
	
	/**
	 * Ϊÿ��file input��change�¼�������У����ϴ��ļ��������Ƿ��ǹ涨������
	 */
	fileInputs.attr("canSubmit",canSubmit);
	fileInputs.each(function(){
		$(this).on("change",function(){
			var fileSize = this.files[0].size;
			var fileName = this.files[0].name;
			var fileType = fileName.substring(fileName.lastIndexOf(".")+1);
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
			var inputAttrName = $(this).attr("name");
			console.log("�ļ�����:"+fileName+",����:"+fileType+",��С:"+fileSize/1000+"Kb"+",����:"+inputAttrName);
			var kind = "";
			if(inputAttrName=="breakfast"||inputAttrName=="lunch"||inputAttrName=="dinner"){
				kind = "threeMeals";
			}else if(inputAttrName=="additional"){
				kind = "additional";
			}
			if(fileType!="xls"&&fileType!="xlsx"){
				alert("��ѡ����ȷ��Excel�ļ���");
				$(this).parent().attr("class","has-error");
				$(this).attr("canSubmit","0");
				return false;
			}
			if(fileName==null||fileName==""){
				alert("�ļ�����Ϊ�գ�");
				$(this).parent().attr("class","has-error");
				$(this).attr("canSubmit","0");
				return false;
			}
			if(kind=="threeMeals"){
				if(!(fileName=="���ģ��")&&!(fileName=="���ģ��")&&!(fileName=="���ģ��")){
					alert("�Ǽȶ�ģ���ļ������֤���ϴ���");
					$(this).parent().attr("class","has-error");
					$(this).attr("canSubmit","0");
					return false;
				}
			}
			if(kind=="additional"){
				if(!(fileName=="�Ӳ�ģ��")){
					alert("�Ǽȶ�ģ���ļ������֤���ϴ���");
					$(this).parent().attr("class","has-error");
					$(this).attr("canSubmit","0");
					return false;
				}
			}
			if(fileSize>1024*1024){
				alert("�ļ�̫��");
				$(this).parent().attr("class","has-error");
				$(this).attr("canSubmit","0");
				return false;
			}
			$(this).parent().attr("class","has-success");
			$(this).attr("canSubmit","1");
		});
	});
	
	/**
	 * �ϴ�����
	 */
	threeMealsButton.click(function(){
		var formData = new FormData();
		var breakfast = threeMeals.find("[name=breakfast]").get(0);
		var lunch = threeMeals.find("[name=lunch]").get(0);
		var dinner = threeMeals.find("[name=dinner]").get(0);
		console.log($("#threeMeals form").find("[name=breakfast]").get(0).value);
		if(breakfast.value!=""&&breakfast.value!=null){
			formData.append("breakfast",breakfast.files[0]);
		}
		if(lunch.value!=""&&lunch.value!=null){
			formData.append("lunch",lunch.files[0]);
		}
		if(dinner.value!=""&&dinner.value!=null){
			formData.append("dinner",dinner.files[0]);
		}
		if(breakfast.getAttribute("canSubmit")=="1"||lunch.getAttribute("canSubmit")=="1"||dinner.getAttribute("canSubmit")=="1"){
			$.ajax({
				url:contextPath+"/WeeklyDietUpload",
				type:"POST",
				data:formData,
				contentType:false,
				processData:false,
				success:function(data){
					eval(data);
				},
				complete:function(){
					window.location.reload(true);
				}
			});
		}else{
			alert("�ļ��б��У��в���������֤��");
			return false;
		}
	});
	
	additionalButton.click(function(){
		var formData = new FormData();
		var additionalMeal = additional.find("[name=additional]").get(0);
		console.log($("#additional form").find("[name=additional]").get(0).value);
		if(additionalMeal.value!=""&&additionalMeal.value!=null){
			formData.append("additional",additionalMeal.files[0]);
		}
		if(additionalMeal.getAttribute("canSubmit")=="1"){
			$.ajax({
				url:contextPath+"/WeeklyDietUploadAdditional",
				type:"POST",
				data:formData,
				contentType:false,
				processData:false,
				success:function(data){
					eval(data);
				},
				complete:function(){
					window.location.reload(true);
				}
			});
		}else{
			alert("�ļ��б��У��в���������֤��");
			return false;
		}
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});