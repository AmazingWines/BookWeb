package com.wines.books.admin.admin.book.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wines.books.book.entity.Book;
import com.wines.books.book.service.BookService;
import com.wines.books.category.entity.Category;
import com.wines.books.category.service.CategoryService;
import com.wines.books.page.PageBean;

public class AdminBookServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService();
	
	/**
	 * 查询所有父分类（里面携带着子分类）
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1、调用service得到所有父分类
		 * 2、保存到request中，转发到left.jsp
		 */
		List<Category> parents = categoryService.findAllParent();
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/book/left.jsp";
	}
	
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
		//获取所有一级分类
		req.setAttribute("parents", categoryService.findAllParents());
		//获取当前图书所属的一级分类下的所有二级分类
		req.setAttribute("children", categoryService.findChildrenByParent(book.getCategory().getParent().getCid()));
		return "f:/adminjsps/admin/book/desc.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 添加图书准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addBookPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Category> parents = categoryService.findAllParents();
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	/**
	 * ajax通过父分类查询所有子分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("pid");
		List<Category> children = categoryService.findChildrenByParent(cid);
		//转换成json
		String json = toJson(children);
		resp.getWriter().print(json);
		return null;
	}
	
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0;i < categoryList.size();i ++) {
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 修改图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map map = req.getParameterMap();
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		bookService.updateBook(book);
		req.setAttribute("msg", "修改图书成功！");
		return "f:/adminjsps/msg.jsp";
	}
	
	/**
	 * 删除图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		String savePath = this.getServletContext().getRealPath("/");
		new File(savePath, book.getImage_b()).delete();
		new File(savePath, book.getImage_w()).delete();
		bookService.delete(bid);
		req.setAttribute("msg", "删除图书成功！");
		return "f:/adminjsps/msg.jsp";
	}
}
