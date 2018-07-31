package com.wines.books.cart.entity;

import java.math.BigDecimal;

import com.wines.books.book.entity.Book;
import com.wines.books.user.entity.User;

public class CartItem {
	private String cartItemId;//主键
	private int quantity;//数量
	private Book book;//条目对应的图书
	private User user;//所属用户
	
	//添加小计
	public double getSubtotal() {
		/*
		 * 为什么java进行double关于钱的运算不准确，例如：2.0-1.1（结果为0.8999999999），因为二进制计算1/10不准确，好比十进制计算1/3
		 * 使用BigDecimal不会有误差
		 * 要求必须使用String类型的构造器，这样才准确
		 */
		BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");
		BigDecimal b2 = new BigDecimal(quantity + "");
		BigDecimal b3 = b1.multiply(b2);
		return b3.doubleValue();
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
