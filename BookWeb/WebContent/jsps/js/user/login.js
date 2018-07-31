$(function() {
	/*
	 * 1、让登录按钮在得到焦点和失去焦点时切换图片
	 */
	$("#submit").hover(
		function() {
			$("#submit").attr("src", "/BookWeb/images/login2.jpg");
		},
		function() {
			$("#submit").attr("src", "/BookWeb/images/login1.jpg");
		}
	);
	
	/*
	 * 2、输入框得到焦点时隐藏错误信息
	 */
	$(".input").focus(function() {
		var inputName = $(this).attr("name");
		$("#" + inputName + "Error").css("display", "none");
	});
	
	/*
	 * 3、输入框失去焦点时进行校验
	 */
	$(".input").blur(function() {
		var inputName = $(this).attr("name");
		invokeValidate(inputName);
	});
	
	/*
	 * 4、表单校验
	 */
	$("#submit").submit(function(){
		$("#msg").text("");
		var bool = true;
		$(".input").each(function() {
			var inputName = $(this).attr("name");
			if(!invokeValidate(inputName)) {
				bool = false;
			}
		});
		return bool;
	});
});

/*
 * 得到哪个input的名称，就调用相应的validate方法
 */
function invokeValidate(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName =  "validate" + inputName;
	eval(functionName + "()");
}

/*
 * 校验登录名
 */
function validateLoginname() {
	var bool = true;
	$("#loginnameError").css("display", "none");
	var value = $("#loginname").val();
	if(!value) {
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("用户名不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("用户名长度必须在3~20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验密码
 */
function validateLoginpass() {
	var bool = true;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	if(!value) {
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("密码不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("密码长度必须在3~20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验验证码
 */
function validateVerifyCode() {
	var bool = true;
	$("#verifyCodeError").css("display", "none");
	var value = $("#verifyCode").val();
	if(!value) {
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("验证码不能为空！");
		bool = false;
	} else if(value.length != 4) {
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("验证码长度必须为4！");
		bool = false;
	} else {
		$.ajax({
			cache:false,
			async:false,
			type:"POST",
			dataType:"json",
			data:{method:"ajaxValidateVerifyCode", verifyCode:value},
			url:"/BookWeb/UserServlet",
			success:function(flag) {
				if(!flag) {
					$("#verifyCodeError").css("display", "");
					$("#verifyCodeError").text("验证码错误！");
					bool = false;
				}
			}
		});
	}
	return bool;
}
