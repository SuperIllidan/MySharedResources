/**
 * 
 */
$(function(){
	
	var weekArr = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
	var date = new Date();
	var year = date.getFullYear();
	/**
	 * 获取加餐食谱，并添加到table
	 */
	//项目目录根
	var contextPath = $("#contextPath").val();
	
	//默认显示早餐
	$(".order").hide();
	$("#breakfast").show();
	//返回按钮
	$("#back").click(function(){
		window.location.href="../index.jsp";
	});
	
	//三餐及加餐选择显示按钮
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
	 * 获取本周三餐食谱
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
			//处理三餐
			//获取date时间,由于三餐时间一样，食品类型固定，所以遍历早餐的某一类即可
			dateArr = Object.keys(breakfastMap["主食"]);
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
			//放食谱
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
			//标题居中
			$(".title").attr("colspan",dateArr.length+1+"");
			//设置日期和星期
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