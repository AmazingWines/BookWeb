package com.wines.books.user.dao;

import java.awt.font.NumericShaper;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wines.books.user.entity.User;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 校验用户名是否注册
	 * @param loginname
	 * @return
	 * @throws Exception
	 */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}
	
	/**
	 * 校验Email是否注册
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public boolean ajaxValidateEmail(String email) throws SQLException {
		String sql = "select count(1) from t_user where email=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), email);
		return number.intValue() == 0;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException 
	 */
	public void add(User user) throws SQLException {
		String sql = "insert into t_user values(?,?,?,?,?,?)";
		Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(), user.getEmail(), 
				user.isStatus(), user.getActivationCode()};
		qr.update(sql, params);
	}
	
	/**
	 * 通过激活码查询用户
	 * @param code
	 * @return
	 * @throws SQLException 
	 */
	public User findUserByCode(String code) throws SQLException {
		String sql = "select * from t_user where activationCode=?";
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}
	
	/**
	 * 修改用户状态
	 * @param uid
	 * @param status
	 * @throws SQLException 
	 */
	public void updateStatus(String uid, boolean status) throws SQLException {
		String sql = "update t_user set status=? where uid=?";
		qr.update(sql, status, uid);
	}
	
	/**
	 * 通过用户名和密码查询用户
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws SQLException
	 */
	public User findUserByLoginnameAndLoginpass(String loginname, String loginpass) throws SQLException {
		String sql = "select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
	}
	
	/**
	 * 校验修改密码的原密码是否正确
	 * @param loginpass
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidatePassword(String loginpass) throws SQLException {
		String sql = "select count(1) from t_user where loginpass=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), loginpass);
		return number.intValue() != 0;
	}
	
	/**
	 * 按uid和原密码查询
	 * @param uid
	 * @param oldPassword
	 * @return
	 * @throws SQLException
	 */
	public boolean findUserByUidAndOldPass(String uid, String oldPassword) throws SQLException {
		String sql = "select count(*) from t_user where uid=? and loginpass=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), uid, oldPassword);
		return number.intValue() > 0;
	}
	
	/**
	 * 修改密码
	 * @param uid
	 * @param newPassword
	 * @throws SQLException
	 */
	public void updatePassword(String uid, String newPassword) throws SQLException {
		String sql = "update t_user set loginpass=? where uid=?";
		qr.update(sql, newPassword, uid);
	}
}
