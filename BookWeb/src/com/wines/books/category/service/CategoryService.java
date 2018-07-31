package com.wines.books.category.service;

import java.sql.SQLException;
import java.util.List;

import com.wines.books.category.dao.CategoryDao;
import com.wines.books.category.entity.Category;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	
	/**
	 * 查询所有父分类（里面携带着子分类）
	 * @return
	 */
	public List<Category> findAllParent() {
		try {
			return categoryDao.findAllParentByPid();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加分类
	 * @param category
	 */
	public void addCategory(Category category) {
		try {
			categoryDao.addCategory(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询所有父分类（不加载子分类）
	 * @return
	 */
	public List<Category> findAllParents() {
		try {
			return categoryDao.findAllParent();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 通用的加载分类
	 * @param cid
	 * @return
	 */
	public Category load(String cid) {
		try {
			return categoryDao.load(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 通用的修改分类
	 * @param category
	 */
	public void edit(Category category) {
		try {
			categoryDao.edit(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 通过父分类查询其子分类的个数
	 * @param pid
	 * @return
	 */
	public int findChildrenCountByParent(String pid) {
		try {
			return categoryDao.findChildrenCountByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除分类
	 * @param cid
	 */
	public void deleteParent(String cid) {
		try {
			categoryDao.deleteParent(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ajax通过父分类查询所有子分类
	 * @param pid
	 * @return
	 */
	public List<Category> findChildrenByParent(String pid) {
		try {
			return categoryDao.findChildrenByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
