/*
$().ready(function() {
	
	$("#login_form").validate({
		rules: {
			username: "required",
			password: {
				required: true,
				minlength: 5
			},
		},
		messages: {
			username: "请输入姓名",
			password: {
				required: "请输入密码",				
				minlength: jQuery.validator.format("密码不能小于{0}个字 符")
			},
		}
	});
	
	$("#register_form").validate({
		rules: {
			username: "required",
			password: {
				required: true,
				minlength: 5
			},
			rpassword: {
				equalTo: "#register_password"
			},
			email: {
				required: true,
				email: true
			}
		},
		messages: {
			username: "请输入姓名",
			password: {
				required: "请输入密码",
				minlength: jQuery.validator.format("密码不能小于{0}个字 符")
			},
			rpassword: {
				equalTo: "两次密码不一样"
			},
			email: {
				required: "请输入邮箱",
				email: "请输入有效邮箱"
			}
		}
	});
	
	$("#forgot_form").validate({
		rules: {			
			email: {
				required: true,
				email: true
			}
		},
		messages: {			
			email: {
				required: "请输入邮箱",
				email: "请输入有效邮箱"
			}
		}
	});
	
});
*/
$(function() {
	$("#login_sign_up").click(function() {
		$("#register_form").css("display", "block");
		$("#login_form").css("display", "none");
		$("#forgot_form").css("display", "none");		
	});
	$("#login_forgot").click(function() {
		$("#register_form").css("display", "none");
		$("#login_form").css("display", "none");
		$("#forgot_form").css("display", "block");	
	});
	$("#register_sgin_in").click(function() {
		$("#register_form").css("display", "none");
		$("#login_form").css("display", "block");
		$("#forgot_form").css("display", "none");		
	});
	$("#forgot_sgin_in").click(function() {
		$("#register_form").css("display", "none");
		$("#login_form").css("display", "block");
		$("#forgot_form").css("display", "none");		
	});
	$("#forgot_sgin_up").click(function() {
		$("#register_form").css("display", "block");
		$("#login_form").css("display", "none");
		$("#forgot_form").css("display", "none");		
	});
	
	var loginInfo = localStorage.getItem("loginInfo");
	if(loginInfo != "" && loginInfo != null)
		{
		var loginJson = JSON.parse(loginInfo);
		$("#login_username").val(loginJson.adminname); 
		$("#login_password").val(loginJson.adminpwd); 
		var remember = $('#remember').prop("checked","checked");
		}
	
	 $(document).keydown(function (event) {
	        if (event.keyCode == 13) {
	          $('#sign_in').triggerHandler('click');
	        }
	      });
	
	$("#sign_in").click(function() {
		var adminName = $("#login_username").val(); 
		var adminPwd = $("#login_password").val(); 
	   $.ajax(  
	    {  
	        url:"/login",  
	        data:{
	        	adminname:adminName,
	        	adminpwd:adminPwd
				},  
	        type:"post",  
	        dataType:"json",  
	        success:function(data)  
	        {  
	            if(data.result=="success")
	            	{
	            		var remember = $('#remember').prop("checked");
	            		if(remember)
	            			{
	            				var loginInfo={
            						adminname:adminName,
            			        	adminpwd:adminPwd
	            				};
	            				
	            				localStorage.setItem("loginInfo",JSON.stringify(loginInfo));
	            			}
	            		else {
	            			localStorage.setItem("loginInfo","");
						}
	            		
	            		window.location.href="main";
	            		localStorage.setItem("adminInfo",JSON.stringify(data.adminInfo));
	            		sessionStorage.setItem('grpsinfo', null);
	            		sessionStorage.setItem('selectgrpid',0);
	            		//var grpsinfo= JSON.parse(sessionStorage.getItem('grpsinfo'));
	            	}
	            else{alertMessage(1, "警告", data.resultMessage);}
	        }
	    });   
	});
});