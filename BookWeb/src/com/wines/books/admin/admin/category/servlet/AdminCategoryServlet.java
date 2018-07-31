package com.wines.books.admin.admin.category.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.wines.books.book.service.BookService;
import com.wines.books.category.entity.Category;
import com.wines.books.category.service.CategoryService;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService();
	
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("parents", categoryService.findAllParent());
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	/**
	 * 添加父分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());
		categoryService.addCategory(parent);
		return findAllCategory(req, resp);
	}
	
	/**
	 * 添加二级分类准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addchildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = req.getParameter("pid");
		List<Category> parents = categoryService.findAllParents();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/add2.jsp";
	}
	
	/**
	 * 添加二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addchild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryService.addCategory(child);
		return findAllCategory(req, resp);
	}
	
	/**
	 * 修改一级分类准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	
	/**
	 * 修改一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAllCategory(req, resp);
	}
	
	/**
	 * 修改二级分类准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findAllParents());
		return "f:/adminjsps/admin/category/edit2.jsp";
	}
	
	/**
	 * 修改二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryService.edit(child);
		return findAllCategory(req, resp);
	}
	
	/**
	 * 删除一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("cid");
		int count = categoryService.findChildrenCountByParent(cid);
		if(count > 0) {
			req.setAttribute("msg", "该一级分类下有二级分类，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.deleteParent(cid);
			return findAllCategory(req, resp);
		}
	}
	
	/**
	 * 删除二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("cid");
		int count = bookService.findBookCountByChildren(cid);
		if(count > 0) {
			req.setAttribute("msg", "该二级分类下有图书，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.deleteParent(cid);
			return findAllCategory(req, resp);
		}
	}
}
