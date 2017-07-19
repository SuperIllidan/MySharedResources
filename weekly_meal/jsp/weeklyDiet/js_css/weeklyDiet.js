/**
 * 
 */
$(function(){
	
	var weekArr = ["������","����һ","���ڶ�","������","������","������","������"];
	var date = new Date();
	var year = date.getFullYear();
	/**
	 * ��ȡ�Ӳ�ʳ�ף�����ӵ�table
	 */
	//��ĿĿ¼��
	var contextPath = $("#contextPath").val();
	
	//Ĭ����ʾ���
	$(".order").hide();
	$("#breakfast").show();
	//���ذ�ť
	$("#back").click(function(){
		window.location.href="../index.jsp";
	});
	
	//���ͼ��Ӳ�ѡ����ʾ��ť
	$("#viewB").click(function(){
		$(".order").hide();
		$("#breakfast").show();
	});
	$("#viewL").click(function(){
		$(".order").hide();
		$("#lunch").show();
	});
	$("#viewD").click(function(){
		$(".order").hide();
		$("#dinner").show();
	});
	$("#viewA").click(function(){
		$(".order").hide();
		$("#additional").show();
	});
	
	$.ajax({
		type:"POST",
		url:contextPath+"/WeeklyDietAdditionalShow",
		contentType:"application/json",
		dataType:"JSON",
		success:function(data){
			if(data.error){
				$("#additional").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
				return;
			}
			var dishesMap = data.additional.dishesMap;
			var datePoint = data.datePoint;
			var timePattern = year+"-"+datePoint+"T08:00:00+08:00";
			var dayNumber = new Date(Date.parse(timePattern)).getDay();
			var dayOfWeek = weekArr[dayNumber];
			$("#additionalDayOfMonth").text(datePoint).attr("style","font-size:18px");
			$("#additionalDayOfWeek").text(dayOfWeek).attr("style","font-size:18px");
			$.each(dishesMap,function(key,value){
				var type = key;
				$.each(value,function(k,v){
					var table = $("#additional");
					table.append("<tr class='success'><td style='vertical-align:middle;font-size:18px;font:bold'>"+type+"</td><td style='vertical-align:middle;font-size:16px'>"+v+"</td></tr>");
				});
			});
		},
		error:function(){
			$("#additional").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
			return;
		}
	});
	
	/**
	 * ��ȡ��������ʳ��
	 */
	$.ajax({
		type:"post",
		url:contextPath+"/WeeklyDietShow",
		contentType:"application/json",
		dataType:"JSON",
		success:function(data){
			if(data.error){
				$("#breakfast").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
				$("#lunch").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
				$("#dinner").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
				return;
			}
			var breakfast = data.breakfast;
			var lunch = data.lunch;
			var dinner = data.dinner;
			var breakfastMap = breakfast.dishesMap;
			var lunchMap = lunch.dishesMap;
			var dinnerMap = dinner.dishesMap;
			var dateArr = [];
			//��������
			//��ȡdateʱ��,��������ʱ��һ����ʳƷ���͹̶������Ա�����͵�ĳһ�༴��
			dateArr = Object.keys(breakfastMap["��ʳ"]);
			dateArr.sort(function(x,y){
				var xPattern = year+"-"+x;
				var yPattern = year+"-"+y;
				var xTimeStamp = new Date(Date.parse(xPattern)).getTime();
				var yTimeStamp = new Date(Date.parse(yPattern)).getTime();
				if(xTimeStamp<yTimeStamp){
					return -1;
				}
				if(xTimeStamp>yTimeStamp){
					return 1;
				}
				if(xTimeStamp=yTimeStamp){
					return 0;
				}
			});
			//��ʳ��
			$.each(breakfastMap,function(key,value){
				var type = key;
				$("#breakfast").append("<tr id='B"+type+"' class='success'><td style='vertical-align:middle;font-size:18px;font:bold'>"+type+"</td></tr>");
				for(var u=0;u<dateArr.length;u++){
					$("#B"+type).append("<td style='vertical-align:middle;font-size:16px'>"+value[dateArr[u]]+"</td>");
				}
			});
			$.each(lunchMap,function(key,value){
				var type = key;
				$("#lunch").append("<tr id='L"+type+"' class='success'><td style='vertical-align:middle;font-size:18px;font:bold'>"+type+"</td></tr>");
				for(var u=0;u<dateArr.length;u++){
					$("#L"+type).append("<td style='vertical-align:middle;font-size:16px'>"+value[dateArr[u]]+"</td>");
				}
			});
			$.each(dinnerMap,function(key,value){
				var type = key;
				$("#dinner").append("<tr id='d"+type+"' class='success'><td style='vertical-align:middle;font-size:18px;font:bold'>"+type+"</td></tr>");
				for(var u=0;u<dateArr.length;u++){
					$("#d"+type).append("<td style='vertical-align:middle;font-size:16px'>"+value[dateArr[u]]+"</td>");
				}
			});
			//�������
			$(".title").attr("colspan",dateArr.length+1+"");
			//�������ں�����
			for(var j=0;j<dateArr.length;j++){
				$(".dayOfMonth").append("<td style='vertical-align:middle;font-size:18px;font:bold'>"+dateArr[j]+"</td>");
				$(".dayOfWeek").append("<td style='vertical-align:middle;font-size:18px;font:bold'>"+weekArr[new Date(Date.parse(year+"-"+dateArr[j])).getDay()]+"</td>");
			}
		},
		error:function(){
			$("#breakfast").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
			$("#lunch").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
			$("#dinner").append("<tr><td rowspan='9' colspan='2' align='center' style='font-size:30px;'>"+data.error+"</td></tr");
			return;
		}
	});
			
});