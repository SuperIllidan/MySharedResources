/**
 * how to use it : first step:var xmlHttp = createAjax();
 * 				   second step: xmlHttp.sendData("post/get","xx.jsp?...",true/false,function(xmlHttp){if(xmlHttp.responseText!=""){//do something}});
 */
'use strict'
function createAjax(){
	var xmlHttp = false;
	if(window.XMLHttpRequest){
		xmlHttp = new XMLHttpRequest();
		if(xmlHttp.overrideMimeType){
			xmlHttp.overrideMimeType("text/html");
		}
	}
	else if(window.ActiveXObject){
		var MSXML = ['MSXML2.XMLHTTP5.0','Microsoft.XMLHTTP','MSXML2.HTTP4.0','MSXML2.HTTP3.0','MSXML2.XMLHTTP'];
		for(var i = 0;i<MSXML.length;i++){
			try {
				xmlHttp = new ActiveXObject(MSXML[i]);
				break;
			} catch (e) {
				// TODO: handle exception
				
			}
		}
		this.sendData = function(methodType,url,asynchronousFlag,fun){
			if(asychronousFlag){
				xmlHttp.onreadystatechange=function(){
					if(xmlHttp.readyState===4 && xmlHttp.status===200){
						fun(xmlHttp);
					}
				}
				if(methodType==="post"){
					xmlHttp.open("post",url,true);
					xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
					xmlHttp.send(null);
				}
				else{
					xmlHttp.open("get",url,true);
					xmlHttp.send(null);
				}
			}
			else{
				if(methodType==="post"){
					xmlHttp.open("post",url,false);
					xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
					xmlHttp.send(null);
				}
				else{
					xmlHttp.open("get",url,false);
					xmlHttp.send(null);
				}
			}
			return xmlHttp;
		}
	}
}




