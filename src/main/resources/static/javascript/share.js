$(function(){
		
	adminpermission();
	
	mainClick();
});

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
		window.location.href="groupManger";
	})
	
	$('.bk-clr-five').click(function() {
		window.location.href="userManger";
	})
	
	$('.bk-clr-six').click(function() {
		window.location.href="taxiManger";
	})
	
	$('.bk-clr-seven').click(function() {
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
						if(adminpermission[i]=='0')
							{
								$('#advManger').css("display","none");
								$('.bk-clr-one').parent().css("display","none");
							}
						else
							{
								$('#advManger').css("display","inline");
								$('.bk-clr-one').parent().css("display","inline");
							}
					};break;
					case 1:{
						if(adminpermission[i]=='0')
						{
							$('#auditManger').css("display","none");
							$('.bk-clr-two').parent().css("display","none");
						}
					else
						{
							$('#auditManger').css("display","inline");
							$('.bk-clr-two').parent().css("display","inline");
						}
					};break;
					case 2:{
						if(adminpermission[i]=='0')
						{
							$('#infoList').css("display","none");
							$('.bk-clr-three').parent().css("display","none");
						}
					else
						{
							$('#infoList').css("display","inline");
							$('.bk-clr-three').parent().css("display","inline");
						}
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
					case 4:{
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