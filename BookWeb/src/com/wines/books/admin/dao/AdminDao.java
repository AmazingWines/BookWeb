package com.wines.books.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.wines.books.admin.entity.Admin;

import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 登录查询管理员
	 * @param adminname
	 * @param adminpwd
	 * @return
	 * @throws SQLException 
	 */
	public Admin findByAdmin(String adminname, String adminpwd) throws SQLException {
		String sql = "select * from t_admin where adminname=? and adminpwd=?";
		return qr.query(sql, new BeanHandler<Admin>(Admin.class), adminname, adminpwd);
	}
}
