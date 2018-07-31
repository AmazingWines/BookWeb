$(function() {
	/**
	 * 1、得到所有的错误信息，循环遍历，再调用一个方法确定是否显示错误信息
	 */
	$(".errorClass").each(function() {
		showError($(this));//遍历每个元素，使用每个元素调用showError方法
	});
	
	/**
	 * 2、切换注册按钮的图片
	 */
	$("#submitBtn").hover(
		function() {
			$("#submitBtn").attr("src", "/BookWeb/images/regist2.jpg");
		},
		function() {
			$("#submitBtn").attr("src", "/BookWeb/images/regist1.jpg");
		}
	);
	
	/**
	 * 3、输入框得到焦点隐藏错误信息
	 */
	$(".inputClass").focus(function() {
		var labelId = $(this).attr("id") + "Error";//通过输入框找到对应的label的id
		$("#" + labelId).text("");//清空label的内容
		showError($("#" + labelId));//隐藏没有信息的label
	});
	
	/**
	 * 4、输入框失去焦点进行校验
	 */
	$(".inputClass").blur(function() {
		var id = $(this).attr("id");//获取当前输入框的id
		var funName = "validate" + id.substring(0,1).toUpperCase() + id.substring(1) + "()";//得到对应的校验函数名
		eval(funName);//执行函数调用
	});
	
	/**
	 * 5、表单提交时进行校验
	 */
	$("#registForm").submit(function() {
		var bool = true;//表示校验通过
		if(!validateLoginname()) {
			bool = false;
		}
		if(!validateLoginpass()) {
			bool = false;
		}
		if(!validateReloginpass()) {
			bool = false;
		}
		if(!validateEmail()) {
			bool = false;
		}
		if(!validateVerifyCode()) {
			bool = false;
		}
		return bool;
	});
});

/**
 * 登录名校验
 */
function validateLoginname() {
	var id = "loginname";
	var value = $("#" + id).val();//获取输入框内容
	/*
	 * 1、非空校验
	 */
	if(!value) {
		/*
		 * 获取相应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("用户名不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2、长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		$("#" + id + "Error").text("用户名长度必须在3~20之间！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 3、是否注册校验
	 */
	$.ajax({
		url:"/BookWeb/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateLoginname", loginname:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果是异步，那么不会等服务器返回，这个函数就向下运行了
		cache:false,
		success:function(result) {
			if(!result) {
				$("#" + id + "Error").text("用户名已被注册！");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	return true;
}

/**
 * 登录密码校验
 */
function validateLoginpass() {
	var id = "loginpass";
	var value = $("#" + id).val();
	/*
	 * 1、非空校验
	 */
	if(!value) {
		$("#" + id + "Error").text("密码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2、长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		$("#" + id + "Error").text("密码长度必须在3~20之间！");
		showError($("#" + id + "Error"));
		return false;
	}
	return true;
}

/**
 * 确认密码校验
 */
function validateReloginpass() {
	var id = "reloginpass";
	var value = $("#" + id).val();
	/*
	 * 1、非空校验
	 */
	if(!value) {
		$("#" + id + "Error").text("确认密码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2、两次输入是否一致校验
	 */
	if(value != $("#loginpass").val()) {
		$("#" + id + "Error").text("两次密码输入不一致！");
		showError($("#" + id + "Error"));
		return false;
	}
	return true;
}

/**
 * Email校验
 */
function validateEmail() {
	var id = "email";
	var value = $("#" + id).val();
	/*
	 * 1、非空校验
	 */
	if(!value) {
		$("#" + id + "Error").text("Email不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2、Email格式校验
	 */
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
		$("#" + id + "Error").text("错误的email格式！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 3、是否注册校验
	 */
	$.ajax({
		url:"/BookWeb/UserServlet",
		data:{method:"ajaxValidateEmail", email:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result) {
			if(!result) {
				$("#" + id + "Error").text("Email已被注册！");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	return true;
}

/**
 * 验证码校验
 */
function validateVerifyCode() {
	var id = "verifyCode";
	var value = $("#" + id).val();
	/*
	 * 1、非空校验
	 */
	if(!value) {
		$("#" + id + "Error").text("验证码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2、长度校验
	 */
	if(value.length != 4) {
		$("#" + id + "Error").text("验证码长度必须为4");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 3、是否正确校验
	 */
	$.ajax({
		url:"/BookWeb/UserServlet",
		data:{method:"ajaxValidateVerifyCode", verifyCode:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result) {
			if(!result) {
				$("#" + id + "Error").text("验证码错误！");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	return true;
}

/**
 * 判断当前元素是否存在内容，如果存在显示，不存在不显示
 * @param ele
 */
function showError(ele) {
	var text = ele.text();
	if(!text) {
		ele.css("display", "none");//隐藏元素
	} else {
		ele.css("display", "");//显示元素
	}
}

/**
 * 换一张验证码
 */
function _hyz() {
	/**
	 * 1、获取<img>元素
	 * 2、重新设置它的src
	 * 3、使用毫秒来添加参数
	 */
	$("#imgVerifyCode").attr("src", "/BookWeb/VerifyCodeServlet?a=" + new Date().getTime());
}