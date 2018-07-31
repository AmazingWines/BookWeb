package com.wines.books.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.wines.books.book.entity.Book;
import com.wines.books.category.entity.Category;
import com.wines.books.page.Expression;
import com.wines.books.page.PageBean;
import com.wines.books.page.PageConstants;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 按bid查询
	 * @param bid
	 * @return
	 * @throws SQLException
	 */
	public Book findByBid(String bid) throws SQLException {
		String sql = "SELECT * FROM t_book b,t_category c WHERE b.cid=c.cid AND b.bid=?";
		//一行记录中，包含了很多book的属性，还有一个cid属性
		Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
		//把Map中除了cid以外的其他属性映射到book对象中
		Book book = CommonUtils.toBean(map, Book.class);
		//把Map中的cid属性映射到category对象中，即这个对象只有cid属性
		Category category = CommonUtils.toBean(map, Category.class);
		//二者建立关系
		book.setCategory(category);
		
		if(map.get("pid") != null) {
			Category parent = new Category();
			parent.setCid((String) map.get("pid"));
			category.setParent(parent);
		}
		return book;
	}
	
	/**
	 * 按分类查询
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCategory(String cid, int pc) throws SQLException {
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(new Expression("cid", "=", cid));
		return findByCriteria(expList, pc);
	}
	
	/**
	 * 按书名查询
	 * @param bname
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByBname(String bname, int pc) throws SQLException {
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(new Expression("bname", "like", "%" + bname + "%"));
		return findByCriteria(expList, pc);
	}
	
	/**
	 * 按作者查询
	 * @param author
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByAuthor(String author, int pc) throws SQLException {
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(new Expression("author", "like", "%" + author + "%"));
		return findByCriteria(expList, pc);
	}
	
	/**
	 * 按出版社查询
	 * @param press
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByPress(String press, int pc) throws SQLException {
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(new Expression("press", "like", "%" + press + "%"));
		return findByCriteria(expList, pc);
	}
	
	/**
	 * 多条件组合查询
	 * @param criteria
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCombination(Book criteria, int pc) throws SQLException {
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(new Expression("bname", "like", "%" + criteria.getBname() + "%"));
		expList.add(new Expression("author", "like", "%" + criteria.getAuthor() + "%"));
		expList.add(new Expression("press", "like", "%" + criteria.getPress() + "%"));
		return findByCriteria(expList, pc);
	}
	
	/**
	 * 通用的查询方法
	 * @param expList
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	private PageBean<Book> findByCriteria(List<Expression> expList, int pc) throws SQLException {
		/*
		 * 1、得到ps
		 * 2、得到tr
		 * 3、得到beanList
		 * 4、得到pageBean，返回
		 */
		/*
		 * 1、得到ps
		 */
		int ps = PageConstants.BOOK_PAGE_SIZE;
		/*
		 * 2、通过expList来生成where子句
		 */
		StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();//它是对应SQL中问号的值
		for(Expression exp : expList) {
			/*
			 * 添加一个条件
			 * 1、以and开头
			 * 2、条件的名称
			 * 3、条件的运算符，可以是=、!=、>、<...is null，is null没有值
			 * 4、如果条件不是is null，再追加问号，然后再向params中添加与问号对应的值
			 */
			whereSql.append(" and ").append(exp.getName()).append(" ").append(exp.getOperator()).append(" ").append("?");
			//例如：where 1=1 and bid = ?
			params.add(exp.getValue());
		}
		/*
		 * 3、总记录数
		 */
		String sql = "select count(*) from t_book" + whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
		int tr = number.intValue();//得到总记录数
		/*
		 * 4、得到beanList，即当前页记录
		 */
		sql = "select * from t_book" + whereSql + " order by orderBy limit ?,?";
		params.add((pc - 1) * ps);//limit的第一个参数是当前页首行记录的下标
		params.add(ps);//第二个参数是每页记录数
		List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class), params.toArray());
		/*
		 * 5、创建PageBean，设置参数
		 */
		PageBean<Book> pageBean = new PageBean<Book>();
		pageBean.setBeanList(beanList);
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setTr(tr);
		
		return pageBean;
	}
	
	/**
	 * 查询指定二级分类下的图书数量
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public int findBookCountByChildren(String cid) throws SQLException {
		String sql = "select count(*) from t_book where cid=?";
		Number count = (Number) qr.query(sql, new ScalarHandler(), cid);
		return count == null ? 0 : count.intValue();
	}
	
	/**
	 * 添加图书
	 * @param book
	 * @throws SQLException
	 */
	public void addBook(Book book) throws SQLException {
		String sql = "insert into t_book(bid,bname,author,price,currPrice," +
				"discount,press,publishtime,edition,pageNum,wordNum,printtime," +
				"booksize,paper,cid,image_w,image_b)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), book.getCategory().getCid(),
				book.getImage_w(),book.getImage_b()};
		qr.update(sql, params);
	}
	
	/**
	 * 修改图书
	 * @param book
	 * @throws SQLException
	 */
	public void updateBook(Book book) throws SQLException {
		String sql = "update t_book set bname=?,author=?,price=?,currPrice=?," +
				"discount=?,press=?,publishtime=?,edition=?,pageNum=?,wordNum=?," +
				"printtime=?,booksize=?,paper=?,cid=? where bid=?";
		Object[] params = {book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), 
				book.getCategory().getCid(),book.getBid()};
		qr.update(sql, params);
	}
	
	/**
	 * 删除图书
	 * @param bid
	 * @throws SQLException
	 */
	public void delete(String bid) throws SQLException {
		String sql = "delete * from t_book where bid=?";
		qr.update(sql, bid);
	}
}
