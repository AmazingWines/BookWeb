package com.wines.books.book.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.wines.books.book.entity.Book;
import com.wines.books.book.service.BookService;
import com.wines.books.page.PageBean;

public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	
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
	 * 加载图书，按bid查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");//获取超链接中的参数bid
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
	
	/**
	 * 按分类查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3、获取查询条件
		 */
		String cid = req.getParameter("cid");
		/*
		 * 4、调用service，得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/*
		 * 5、给PageBean设置url，保存PageBean，转发到jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 按书名查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3、获取查询条件
		 */
		String bname = req.getParameter("bname");
		/*
		 * 4、调用service，得到PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		/*
		 * 5、给PageBean设置url，保存PageBean，转发到jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 按作者查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3、获取查询条件
		 */
		String author = req.getParameter("author");
		/*
		 * 4、调用service，得到PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 5、给PageBean设置url，保存PageBean，转发到jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 按出版社查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3、获取查询条件
		 */
		String press = req.getParameter("press");
		/*
		 * 4、调用service，得到PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 5、给PageBean设置url，保存PageBean，转发到jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 多条件组合查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3、获取查询条件
		 */
		Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/*
		 * 4、调用service，得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/*
		 * 5、给PageBean设置url，保存PageBean，转发到jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
}
