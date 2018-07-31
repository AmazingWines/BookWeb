package com.wines.books.category.servlet;

import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wines.books.category.entity.Category;
import com.wines.books.category.service.CategoryService;

public class CategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	
	/**
	 * 查询所有父分类（里面携带着子分类）
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1、调用service得到所有父分类
		 * 2、保存到request中，转发到left.jsp
		 */
		List<Category> parents = categoryService.findAllParent();
		req.setAttribute("parents", parents);
		return "f:/jsps/left.jsp";
	}
}
