package com.wines.books.admin.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wines.books.admin.entity.Admin;
import com.wines.books.admin.service.AdminService;

public class AdminServlet extends BaseServlet {
	private AdminService adminService = new AdminService();
	
	/**
	 * 登录
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Admin formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = adminService.login(formAdmin);
		if(admin == null) {
			req.setAttribute("msg", "登录名或密码错误！");
			return "f:/adminjsps/login.jsp";
		}
		req.getSession().setAttribute("sessionAdmin", admin);
		return "r:/adminjsps/admin/index.jsp";
	}
}
