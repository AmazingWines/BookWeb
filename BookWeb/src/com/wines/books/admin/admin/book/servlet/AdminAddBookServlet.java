package com.wines.books.admin.admin.book.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;

import com.wines.books.book.entity.Book;
import com.wines.books.book.service.BookService;
import com.wines.books.category.entity.Category;
import com.wines.books.category.service.CategoryService;

public class AdminAddBookServlet extends HttpServlet {
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		/*
		 * 1、commons-fileupload的上传三步
		 */
		//创建工具
		FileItemFactory factory = new DiskFileItemFactory();
		//创建解析器对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80 * 1024);//设置单个上传的文件大小上限为80KB
		//解析request得到List<FileItem>
		List<FileItem> fileItemList = null;
		try {
			fileItemList = sfu.parseRequest(req);
		} catch (FileUploadException e) {
			error("上传的文件大小超过了80KB！", req, resp);
			return;
		}
		
		/*
		 * 2、把List<FileItem>封装到Book对象中
		 */
		//首先把“普通表单字段”放到一个Map中，再把Map转换成Book和Category对象，再建立二者关系
		Map<String, Object> map = new HashMap<String, Object>();
		for(FileItem fileItem : fileItemList) {
			if(fileItem.isFormField()) {//如果是普通表单字段
				map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
			}
		}
		//把Map中大部分数据封装到Book对象中
		Book book = CommonUtils.toBean(map, Book.class);
		//把Map中的cid封装到Category中
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		
		/*
		 * 3、把上传的图片保存起来
		 *  >获取文件名，截取之
		 *  >给文件名添加前缀，使用uuid前缀，为避免文件名同名现象
		 *  >校验文件的扩展名，只能是jpg
		 *  >校验图片的尺寸，只能是350*350
		 *  >指定图片的保存路径，这需要使用ServletContext#getRealPath()
		 *  >保存之
		 *  >把图片的路径设置给Book对象
		 */
		//获取文件名
		FileItem fileItem = fileItemList.get(1);//获取大图
		String fileName = fileItem.getName();
		//截取文件名，因为大部分浏览器上传的是绝对路径
		int index = fileName.lastIndexOf("\\");
		if(index != -1) {
			fileName.substring(index + 1); 
		}
		//给文件名添加uuid前缀，避免文件名同名现象
		fileName = CommonUtils.uuid() + "_" + fileName;
		//检验文件名称的扩展名
		if(!fileName.toLowerCase().endsWith(".jpg")) {
			error("上传的图片类型必须是JPG格式！", req, resp);
			return;
		}
		//校验图片的尺寸
		//保存上传的图片，需要把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIo
		//获取真实路径
		String savePath = this.getServletContext().getRealPath("/book_img");
		//创建目标文件
		File destFile = new File(savePath, fileName);
		//保存文件
		try {
			fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//开始校验图片尺寸
		//使用文件路径创建ImageIcon
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		//通过ImageIcon得到Image对象
		Image image = icon.getImage();
		//获取宽高来进行校验
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			error("上传的图片尺寸大小超过了350*350！", req, resp);
			destFile.delete();//删除图片
			return;
		}
		//把图片的路径设置给Book对象
		book.setImage_w("book_img/" + fileName);
		
		
		/*fileItem = fileItemList.get(2);
		fileName = fileItem.getFieldName();
		index = fileName.lastIndexOf("\\");
		if(index != -1) {
			fileName.substring(index + 1);
		}
		fileName = CommonUtils.uuid() + "_" + fileName;
		if(!fileName.toLowerCase().endsWith(".jpg")) {
			error("", req, resp);
			return;
		}
		savePath = this.getServletContext().getRealPath("/book_img");
		destFile = new File(savePath, fileName);
		try {
			fileItem.write(destFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		icon = new ImageIcon(destFile.getAbsolutePath());
		image = icon.getImage();
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			error("", req, resp);
			destFile.delete();
			return;
		}*/
		
		
		// 获取文件名
		fileItem = fileItemList.get(2);//获取小图
		fileName = fileItem.getName();
		// 截取文件名，因为部分浏览器上传的绝对路径
		index = fileName.lastIndexOf("\\");
		if(index != -1) {
			fileName = fileName.substring(index + 1);
		}
		// 给文件名添加uuid前缀，避免文件同名现象
		fileName = CommonUtils.uuid() + "_" + fileName;
		// 校验文件名称的扩展名
		if(!fileName.toLowerCase().endsWith(".jpg")) {
			error("上传的图片类型必须是JPG格式！", req, resp);
			return;
		}
		// 校验图片的尺寸
		// 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
		/*
		 * 保存图片：
		 * 1. 获取真实路径
		 */
		savePath = this.getServletContext().getRealPath("/book_img");
		/*
		 * 2. 创建目标文件
		 */
		destFile = new File(savePath, fileName);
		/*
		 * 3. 保存文件
		 */
		try {
			fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 校验尺寸
		// 1. 使用文件路径创建ImageIcon
		icon = new ImageIcon(destFile.getAbsolutePath());
		// 2. 通过ImageIcon得到Image对象
		image = icon.getImage();
		// 3. 获取宽高来进行校验
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			error("上传的图片尺寸大小超过了350*350！", req, resp);
			destFile.delete();//删除图片
			return;
		}
		// 把图片的路径设置给book对象
		book.setImage_b("book_img/" + fileName);
		
		
		book.setBid(CommonUtils.uuid());
		bookService.addBook(book);
		req.setAttribute("msg", "添加图书成功！");
		req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, resp);
	}
	
	/**
	 * 保存错误信息，转发到add.jsp
	 * @param msg
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void error(String msg, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("msg", msg);
		req.setAttribute("parents", categoryService.findAllParents());
		req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
	}
}
