package com.wines.books.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.wines.books.category.entity.Category;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 把一个map中的数据映射到category中
	 * @param map
	 * @return
	 */
	private Category toCategory(Map<String, Object> map) {
		/*
		 * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category {cid:xx, cname:xx, parent:xx, desc:xx}
		 */
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String) map.get("pid");//如果是一级父分类，那么pid是null
		if(pid != null) {//如果父分类pid不为空
			/*
			 * 使用一个父分类对象来拦截pid
			 * 再把父分类设置给category（爸爸领儿子的例子）
			 */
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	/**
	 * 把多个map（List<Map>）映射成多个Category（List<Category>）
	 * @param mapList
	 * @return
	 */
	private List<Category> toCategoryList(List<Map<String, Object>> mapList) {
		List<Category> categoryList = new ArrayList<Category>();
		for(Map<String, Object> map : mapList) {
			Category category = toCategory(map);
			categoryList.add(category);
		}
		return categoryList;
	}
	
	/**
	 * 返回所有父分类（一级分类）
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAllParentByPid() throws SQLException {
		/*
		 * 1、查询出所有的一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		List<Category> parents =  toCategoryList(mapList);
		
		/*
		 * 循环遍历所有的一级分类，为每一个一级分类加载它的二级分类
		 */
		for(Category parent : parents) {
			//查询出当前父分类的所有子分类
			List<Category> children = findChildrenByParent(parent.getCid());
			//设置给父分类
			parent.setChildren(children);
		}
		return parents;
	}
	
	/**
	 * 通过父分类查询子分类
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findChildrenByParent(String pid) throws SQLException {
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		return toCategoryList(mapList);
	}
	
	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException 
	 */
	public void addCategory(Category category) throws SQLException {
		/*
		 * 通用的添加分类的方法
		 */
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		String pid = null;//一级分类
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(), category.getCname(), pid, category.getDesc()};
		qr.update(sql, params);
	}
	
	/**
	 * 查询所有父分类（不加载子分类）
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findAllParent() throws SQLException {
		/*
		 * 1、查询出所有的一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		return toCategoryList(mapList);
	}
	
	/**
	 * 通用的加载分类
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(), cid));
	}
	
	/**
	 * 通用的修改分类
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?, pid=?, `desc`=? where cid=?";
		String pid = null;
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCname(), pid, category.getDesc(), category.getCid()};
		qr.update(sql, params);
	}
	
	/**
	 * 通过父分类查询其子分类的个数
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number count = (Number) qr.query(sql, new ScalarHandler(), pid);
		return count == null ? 0 : count.intValue();
	}
	
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException
	 */
	public void deleteParent(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
	}
}
