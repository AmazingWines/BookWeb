/**
 * 换一张验证码
 */
function _change() {
	$("#vCode").attr("src", "/BookWeb/VerifyCodeServlet?" + new Date().getTime());
}