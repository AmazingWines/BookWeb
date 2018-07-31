package com.wines.books.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

import com.wines.books.user.dao.UserDao;
import com.wines.books.user.entity.User;
import com.wines.books.user.service.exception.UserException;

public class UserService {
	private UserDao userDao = new UserDao();
	
	/**
	 * 用户名校验
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Email校验
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email) {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 校验修改密码的原密码是否正确
	 * @param loginpass
	 * @return
	 */
	public boolean ajaxValidatePassword(String loginpass) {
		try {
			return userDao.ajaxValidatePassword(loginpass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改密码
	 * @param uid
	 * @param oldPassword
	 * @param newPassword
	 * @throws UserException
	 */
	public void updatePassword(String uid, String oldPassword, String newPassword) throws UserException {
		try {
			/*
			 * 1、校验原密码
			 */
			boolean bool = userDao.findUserByUidAndOldPass(uid, oldPassword);
			if(!bool) {
				throw new UserException("原密码错误！");
			}
			/*
			 * 2、修改密码
			 */
			userDao.updatePassword(uid, newPassword);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 注册功能
	 * @param user
	 */
	public void regist(User user) {
		/*
		 * 1、数据的补全
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		/*
		 * 2、向数据库插入
		 */
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		/*
		 * 3、发邮件
		 */
		
		/*
		 * 把配置文件加载到properties中
		 */
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		/*
		 * 登录邮件服务器，得到session
		 
		String host = prop.getProperty("host");
		String name = prop.getProperty("username");
		String pass = prop.getProperty("password");
		Session session = MailUtils.createSession(host, name, pass);
		
		
		 * 创建Mail对象
		 
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		//MessageFormat.format方法会把第一个参数中的{0}，使用第二个参数来替换
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		Mail mail = new Mail(from, to, subject, content);
		
		
		 * 发送邮件
		 
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}*/
		
		/*
		 * 构建授权信息，用于进行SMTP进行身份验证
		 */
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 用户名、密码
				String username = prop.getProperty("mail.user");
				String password = prop.getProperty("mail.password");
				return new PasswordAuthentication(username, password);
			}
		};
		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(prop, authenticator);

		//获取邮件标题
		String subject = prop.getProperty("subject");
		//MessageFormat.format方法会把第一个参数中的{0}，使用第二个参数来替换
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);
		try {
			// 设置发件人
			InternetAddress from = new InternetAddress(prop.getProperty("mail.user"));
			message.setFrom(from);
			// 设置收件人
			InternetAddress to = new InternetAddress(user.getEmail());
			message.setRecipient(Message.RecipientType.TO, to);

			//设置标题
			message.setSubject(subject);
			// 设置邮件的内容体
			message.setContent(content, "text/html;charset=UTF-8");

			// 发送邮件
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		/*
		 * 构建授权信息，用于进行SMTP身份验证
		 
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				//用户名、密码
				String username = prop.getProperty("mail.user");
				String password = prop.getProperty("mail.password");
				return new PasswordAuthentication(username, password);
			}
		};
		//使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(prop, authenticator);
		
		//获取邮件标题
		String subject = prop.getProperty("subject");
		//获取邮件正文
		//MessageFormat.format方法会把第一个参数中的{0}，使用第二个参数来替换
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		//创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);
		try {
			//设置发件人
			InternetAddress from = new InternetAddress(prop.getProperty("mail.user"));
			message.setFrom(from);
			//设置收件人
			InternetAddress to = new InternetAddress(user.getEmail());
			message.setRecipient(Message.RecipientType.TO, to);
			
			//设置标题
			message.setSubject(subject);
			//设置邮件的内容体
			message.setContent(content, "text/html;charset=UTF-8");
			
			//发送邮件
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 激活功能
	 * @param code
	 * @throws UserException 
	 */
	public void activation(String code) throws UserException {
		/*
		 * 1、通过激活码查询用户
		 * 2、如果User为null，说明是无效激活码，抛出异常，给出异常信息（无效激活码！）
		 * 3、查看用户状态是否为true，如果为true，抛出异常，给出异常信息（您已经激活过了，请不要二次激活！）
		 * 4、修改用户状态为true
		 */
		try {
			User user = userDao.findUserByCode(code);
			if(user == null) throw new UserException("无效的激活码！");
			if(user.isStatus()) throw new UserException("您已经激活过了，请不要二次激活！");
			userDao.updateStatus(user.getUid(), true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 登录功能
	 * @param user
	 * @return
	 */
	public User login(User user) {
		try {
			return userDao.findUserByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
