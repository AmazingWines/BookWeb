package com.wines.books.admin.service;

import java.sql.SQLException;

import com.wines.books.admin.dao.AdminDao;
import com.wines.books.admin.entity.Admin;

public class AdminService {
	private AdminDao adminDao = new AdminDao();
	
	/**
	 * 登录
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin) {
		try {
			return adminDao.findByAdmin(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
