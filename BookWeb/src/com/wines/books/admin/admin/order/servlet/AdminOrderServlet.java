package com.wines.books.admin.admin.order.servlet;

import cn.itcast.servlet.BaseServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wines.books.order.entity.Order;
import com.wines.books.order.service.OrderService;
import com.wines.books.page.PageBean;
import com.wines.books.user.entity.User;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	
	/**
	 * 获取当前页码
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch (RuntimeException e) {}
		}
		return pc;
	}
	
	/**
	 * 截取url，页面中的分页导航需要使用它作为超链接的目标
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/BookWeb/BookServlet?method=findByCategory&cid=xxx&pc=xxx
	 * /BookWeb/BookServlet + method=findByCategory&cid=xxx&pc=xxx
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURL() + "?" +  req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉
		 */
		int index = url.lastIndexOf("&pc=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	
	/**
	 * 所有订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String allOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1、得到pc
		 */
		int pc = getPc(req);
		/*
		 * 2、得到url
		 */
		String url = getUrl(req);
		/*
		 * 4、调用service，得到PageBean
		 */
		PageBean<Order> pb = orderService.findAll(pc);
		/*
		 * 5、给PageBean设置url，保存PageBean，转发到adminjsps/admin/order/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * 根据状态查询订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1、得到pc
		 */
		int pc = getPc(req);
		/*
		 * 2、得到url
		 */
		String url = getUrl(req);
		/*
		 * 3、获取状态
		 */
		int status = Integer.parseInt(req.getParameter("status"));
		/*
		 * 4、调用service，得到PageBean
		 */
		PageBean<Order> pb = orderService.findByStatus(status, pc);
		/*
		 * 5、给PageBean设置url，保存PageBean，转发到adminjsps/admin/order/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * 加载订单详细
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loadOrderDesc(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.loadOrderDesc(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");//btn说明用户点击哪个超链接来访问本方法
		req.setAttribute("btn", btn);
		return "f:/adminjsps/admin/order/desc.jsp";
	}
	
	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		int status = orderService.findStatus(oid);
		/*
		 * 校验订单状态
		 */
		if(status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "订单状态不对，不能取消！");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);//修改订单状态为取消（5）
		req.setAttribute("code", "success");
		req.setAttribute("msg", "取消订单成功！");
		return "f:/adminjsps/msg.jsp";
	}
	
	/**
	 * 订单发货
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deliver(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		int status = orderService.findStatus(oid);
		/*
		 * 校验订单状态
		 */
		if(status != 2) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "订单状态不对，不能发货！");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 3);//修改订单状态为发货（3）
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已发货，请及时关注物流信息！");
		return "f:/adminjsps/msg.jsp";
	}
}
