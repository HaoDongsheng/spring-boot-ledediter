$(function(){
				
	$( ".modal" ).draggable({ handle: ".modal-header" });
	
//	adminpermission();
	
	mainClick();
	
	var adminInfo = JSON.parse(localStorage.getItem("adminInfo"));	
	
	$('#dropdownUser_name').text(adminInfo.adminname);
});

//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

function getNowFormatDatetime() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate +" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+":"+date.getMilliseconds();
    return currentdate;
}

function alertMessage(messageType, title, message) {	
	var icon='fa fa-check';
	var type='success';
	switch (messageType) {
	case 0:
		var icon='fa fa-check';
		var type='success';
		break;
	case 1:
		var icon='fa fa-exclamation';
		var type='warning';
		break;
	case 2:
		var icon='fa fa-warning';
		var type='danger';
		break;
	
	}
	
	$.notify({		
		icon: icon,
		title: "<strong>"+title+"</strong> ",				
		message: message,		
	},{
		allow_dismiss: true,
		type: type,
		delay: 3000,
		placement: {
			from: "bottom",
			align: "right"
		},
		animate: {
			enter: 'animated fadeInRight',
			exit: 'animated fadeOutRight'
		}
	});
}

function mainClick()
{
	$('.bk-clr-one').click(function() {
		window.location.href="advManger";
	})
	
	$('.bk-clr-two').click(function() {
		window.location.href="auditManger";
	})
	
	$('.bk-clr-three').click(function() {
		window.location.href="infoList";
	})
	
	$('.bk-clr-four').click(function() {
		window.location.href="historyManger";
	})
	
	$('.bk-clr-five').click(function() {
		window.location.href="groupManger";
	})
	
	$('.bk-clr-six').click(function() {
		window.location.href="userManger";
	})
	
	$('.bk-clr-seven').click(function() {
		window.location.href="taxiManger";
	})
	
	$('.bk-clr-eight').click(function() {
		window.location.href="projectManger";
	})
}

function adminpermission()
{	
	var adminInfo = JSON.parse(localStorage.getItem("adminInfo"));		
	
	if(adminInfo.issuperuser!=1)
		{
		$('#projectManger').css("display","none");
		$('.bk-clr-seven').parent().css("display","none");
		var adminpermission = adminInfo.adminpermission;
		for(var i=0;i<adminpermission.length;i++)
			{
				switch(i)
				{
					case 0:{
//						if(adminpermission[i]=='0')
//							{
//								$('#advManger').css("display","none");
//								$('.bk-clr-one').parent().css("display","none");
//							}
//						else
//							{
//								$('#advManger').css("display","inline");
//								$('.bk-clr-one').parent().css("display","inline");
//							}
						
						$('#advManger').css("display","inline");
						$('.bk-clr-one').parent().css("display","inline");
					};break;
					case 1:{
//						if(adminpermission[i]=='0')
//						{
//							$('#auditManger').css("display","none");
//							$('.bk-clr-two').parent().css("display","none");
//						}
//						else
//						{
//							$('#auditManger').css("display","inline");
//							$('.bk-clr-two').parent().css("display","inline");
//						}
						$('#auditManger').css("display","inline");
						$('.bk-clr-two').parent().css("display","inline");
					};break;
					case 2:{
//						if(adminpermission[i]=='0')
//						{
//							$('#infoList').css("display","none");
//							$('.bk-clr-three').parent().css("display","none");
//						}
//						else
//						{
//							$('#infoList').css("display","inline");
//							$('.bk-clr-three').parent().css("display","inline");
//						}
						$('#infoList').css("display","inline");
						$('.bk-clr-three').parent().css("display","inline");
					};break;
					case 3:{
						if(adminpermission[i]=='0')
						{
							$('#groupManger').css("display","none");
							$('.bk-clr-four').parent().css("display","none");
						}
					else
						{
							$('#groupManger').css("display","inline");
							$('.bk-clr-four').parent().css("display","inline");
						}
					};break;
					case 4:{
						if(adminpermission[i]=='0')
						{
							$('#userManger').css("display","none");
							$('.bk-clr-five').parent().css("display","none");
						}
					else
						{
							$('#userManger').css("display","inline");
							$('.bk-clr-five').parent().css("display","inline");
						}
					};break;
					case 5:{
						if(adminpermission[i]=='0')
						{
							$('#taxiManger').css("display","none");
							$('.bk-clr-six').parent().css("display","none");
						}
					else
						{
							$('#taxiManger').css("display","inline");
							$('.bk-clr-six').parent().css("display","inline");
						}
					};break;
				}
			}
		}
	else
		{
			$('#projectManger').css("display","inline");
			$('.bk-clr-seven').parent().css("display","inline");
		}
}
//16进制6位转日期字符串
function hexlife2String(life) {
	life=life.toString();
	if(life==0 || life=="0")
		{
		return "无生命周期";
		}
	else {
		var y1 = parseInt(life.substring(0,2),16);
		var m1 = parseInt(life.substring(2,4),16);
		var d1 = parseInt(life.substring(4,6),16);
		var y2 = parseInt(life.substring(6,8),16);
		var m2 = parseInt(life.substring(8,10),16);
		var d2 = parseInt(life.substring(10,12),16);
		
		return "20"+y1+"年"+m1+"月"+d1+"日 --- 20"+y2+"年"+m2+"月"+d2+"日";	
	}
	
}
//反转义
function GJ_FanZhuanYi(oldData) {
	var newData="";
	newData+="7E";
	for (var i = 2; i < oldData.length - 2; i+=2) {
		if (oldData.substring(i,i+2) == "7D" || oldData.substring(i,i+2) == "7d") {
			i+=2;
			if (oldData.substring(i,i+2) == "01") {
				newData+="7D";
			} else if (oldData.substring(i,i+2) == "02") {
				newData+="7E";
			} else {
				newData+="7D";
				i-=2;
			}
		} else {
			newData+=oldData.substring(i,i+2);			
		}
	}
	newData+="7E";
	return newData;
}
//获取真正秘钥
function getCodeKey(str)
{
	let result = ''
      // 转字符串
      str += ''
      for (let i = 0; i < str.length; i++) {
        let bit = parseInt(str[i], 16).toString(2)
        // 转字符串
        bit.toString()
        // 补零
        while (bit.length < 4) {
          bit = '0' + bit
        }
        result += bit
      }
      return result	 
}

var ws;
var commandSN=0;
var receiveMap = {};

function getSN()
{
	commandSN = commandSN + 1;
	return parseInt(commandSN);
}
//通过URL请求服务端（chat为项目名称）
var url = "ws://localhost:8015/websocket";

function intiWebSocket()
{			
	 if ('WebSocket' in window) {
         ws = new WebSocket(url);
     } else if ('MozWebSocket' in window) {
         ws = new MozWebSocket(url);
     } else {
         alert('WebSocket is not supported by this browser.');
         return;
     }
	 
     ws.onopen=function(){
        console.log("webSocket通道建立成功！！！");     
     };

     //监听服务器发送过来的所有信息
     ws.onmessage = function(event) {
    	 if(event.data!=null)
    		 {
    		 var JsonReturn = JSON.parse(event.data);
    		 var command = JsonReturn.command;
    		 var commandSN = JsonReturn.commandSN;
    		 receiveMap[commandSN]=JsonReturn;    		
    		 }
    	 console.log(event.data);
     };
     
     ws.onclose = function()
     { 
        // 关闭 websocket
    	 console.log("连接已关闭..."); 
     };
     
     // 异常
     ws.onerror = function (event) {
    	 console.log("连接异常...");  
    	 alert("串口插件没有开启或异常,建议检查");
     };        
}

//将消息发送给后台服务器
function wssend(sendData) {
    ws.send(sendData);
    console.log(sendData);
}
	
//将消息发送给后台服务器
function sendDataCallback(JsonObj,callback) {    
    wssend(JSON.stringify(JsonObj));
    callback(JsonObj);    
}

//将消息发送给后台服务器
function sendinfoDataCallback(SN,JsonArray,i,callback) {   
		
	if(JsonArray.length > i)
		{
	    var JsonObj={
				command:"sendSerialData",
				commandSN:SN,
				sendData:JsonArray[i]
			};
	    wssend(JSON.stringify(JsonObj));
	    if (typeof callback === "function") {
	    	callback(SN,JsonArray,i);
	        }
		}
	else
		{
		$('#progress').css('height','0px');    	
		var SN = getSN();
        var JsonObj={
    			command:"closeSerialPort",
				commandSN:SN
    		};	        
        wssend(JSON.stringify(JsonObj));
		}
}