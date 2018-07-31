package com.wines.books.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;

import com.wines.books.cart.dao.CartItemDao;
import com.wines.books.cart.entity.CartItem;

public class CartItemService {
	private CartItemDao cartItemDao = new CartItemDao();
	
	/**
	 * 我的购物车
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid) {
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加购物车条目
	 * @param cartItem
	 */
	public void addCartItem(CartItem cartItem) {
		try {
			/*
			 * 1、查询这个条目是否存在
			 */
			CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(), cartItem.getBook().getBid());
			if(_cartItem == null) {//如果没有，那么添加条目
				cartItem.setCartItemId(CommonUtils.uuid());
				cartItemDao.addCartItem(cartItem);
			} else {//如果有，修改数量
				//用原有的数量相加新的数量
				int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
				cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 批量删除
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改购物车条目的数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId, int quantity) {
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载多个CartItem
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds) {
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
