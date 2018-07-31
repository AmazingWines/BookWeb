$(function() {
	/*
	 * 1、表单校验
	 */
	$("#submit").submit(function() {
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
	
	/*
	 * 2、输入框失去焦点时进行校验
	 */
	$(".input").blur(function() {
		var inputName = $(this).attr("name");
		invokeValidate(inputName);
	});
});

/*
 * 得到哪个input的名称，就调用相应的validate方法
 */
function invokeValidate(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");
}

/*
 * 校验原密码
 */
function validateLoginpass() {
	var bool = true;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	if(!value) {
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("原密码不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("原密码长度必须在3~20之间！");
		bool = false;
	} else {
		$.ajax({
			cache:false,
			async:false,
			type:"POST",
			dataType:"json",
			data:{method: "ajaxValidatePassword", loginpass: value},
			url:"/BookWeb/UserServlet",
			success:function(flag) {
				if(!flag) {
					$("#loginpassError").css("display", "");
					$("#loginpassError").text("输入的密码不正确！");
					bool = false;
				}
			}
		});
	}
	return bool;
}

/*
 * 校验新密码
 */
function validateNewloginpass() {
	var bool = true;
	$("#newloginpassError").css("display", "none");
	var value = $("#newloginpass").val();
	if(!value) {
		$("#newloginpassError").css("display", "");
		$("#newloginpassError").text("新密码不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {
		$("#newloginpassError").css("display", "");
		$("#newloginpassError").text("新密码长度必须在3~20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验确认密码
 */
function validateReloginpass() {
	var bool = true;
	$("#reloginpassError").css("display", "none");
	var value = $("#reloginpass").val();
	if(!value) {//非空校验
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("确认密码不能为空！");
		bool = false;
	} else if(value != $("#newloginpass").val()) {
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("两次密码输入不一致！");
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
	if(!value) {//非空校验
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("验证码不能为空！");
		bool = false;
	} else if(value.length != 4) {//长度不为4就是错误的
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("验证码长度必须为4！");
		bool = false;
	} else {
		$.ajax({
			cache:false,
			async:false,
			type:"POST",
			dataType:"json",
			data:{method: "ajaxValidateVerifyCode", verifyCode: value},
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
