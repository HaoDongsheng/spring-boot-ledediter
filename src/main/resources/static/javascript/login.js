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
	            	{window.location="main";}
	            else{alert(data.resultMessage);}
	        },  
	        error: function() {  
	            alert("error");  
	          }  
	    });   
	});
});