package com.shuangyulin.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
 
import com.shuangyulin.po.Book;
import com.shuangyulin.po.BookType;
import com.shuangyulin.service.BookService;
import com.shuangyulin.service.BookTypeService;
import com.shuangyulin.utils.ExportExcelUtil;
import com.shuangyulin.utils.UserException;


//图书管理控制层
@Controller
@RequestMapping("/Book")
public class BookController extends BaseController {

	//注入业务层对象
	@Resource BookService bookService;
	@Resource BookTypeService bookTypeService;
	 
	@InitBinder("bookType")
	public void initBinderBookType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("bookType.");
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Book());
		/*查询所有的BookType信息*/
        List<BookType> bookTypeList = bookTypeService.QueryAllBookTypeInfo();
        request.setAttribute("bookTypeList", bookTypeList);
		return "Book_add";
	}
	 
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Validated Book book, BindingResult br,
			Model model, HttpServletRequest request) throws Exception { 
		if (br.hasErrors()) {
			model.addAttribute(book);
			/*查询所有的BookType信息*/
	        List<BookType> bookTypeList = bookTypeService.QueryAllBookTypeInfo();
	        request.setAttribute("bookTypeList", bookTypeList);
			return "Book_add";
		} 
		if(bookService.getBookByBarcode(book.getBarcode()) != null) {
			throw new UserException("图书条形码已经存在！"); 
		} 
		book.setPhotoBook(this.handlePhotoFileUpload(request, "photoBookFile"));  
        bookService.addBook(book);
		request.setAttribute("message", java.net.URLEncoder.encode("图书添加成功!", "GBK"));
		return "message"; 
	}
	
	
	
	
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String list(String barcode,@ModelAttribute BookType bookType,String bookName,String publishDate,Integer currentPage, Model model, HttpServletRequest request) throws Exception {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (bookName == null) bookName = "";
		if (barcode == null) barcode = "";
		if (publishDate == null) publishDate = "";
		List<Book> bookList = bookService.queryBook(bookName, bookType, barcode,publishDate, currentPage);
	    /*计算总的页数和总的记录数*/
	    bookService.queryTotalPageAndRecordNumber(bookName, bookType, barcode,publishDate);
	    /*获取到总的页码数目*/
	    int totalPage = bookService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = bookService.getRecordNumber();
	    request.setAttribute("bookList",  bookList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("bookName", bookName);
	    request.setAttribute("bookType", bookType);
	    List<BookType> bookTypeList = bookTypeService.QueryAllBookTypeInfo();
	    request.setAttribute("bookTypeList", bookTypeList);
	    request.setAttribute("barcode", barcode);
	    request.setAttribute("publishDate", publishDate);
	     
		return "Book_query_result"; 
	}
	

	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String barcode,@ModelAttribute BookType bookType,String bookName,String publishDate,Integer currentPage, Model model, HttpServletRequest request) throws Exception {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (bookName == null) bookName = "";
		if (barcode == null) barcode = "";
		if (publishDate == null) publishDate = "";
		List<Book> bookList = bookService.queryBook(bookName, bookType, barcode,publishDate, currentPage);
	    /*计算总的页数和总的记录数*/
	    bookService.queryTotalPageAndRecordNumber(bookName, bookType, barcode,publishDate);
	    /*获取到总的页码数目*/
	    int totalPage = bookService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = bookService.getRecordNumber();
	    request.setAttribute("bookList",  bookList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("bookName", bookName);
	    request.setAttribute("bookType", bookType);
	    List<BookType> bookTypeList = bookTypeService.QueryAllBookTypeInfo();
	    request.setAttribute("bookTypeList", bookTypeList);
	    request.setAttribute("barcode", barcode);
	    request.setAttribute("publishDate", publishDate);
	     
		return "Book_frontquery_result"; 
	}
	
	
	
	
	@RequestMapping(value="/{barcode}/update",method=RequestMethod.GET)
	public String update(@PathVariable String barcode,Model model,HttpServletRequest request) throws Exception {
		/*根据主键barcode获取Book对象*/
        Book book = bookService.getBookByBarcode(barcode);

        List<BookType> bookTypeList = bookTypeService.QueryAllBookTypeInfo();
        request.setAttribute("bookTypeList", bookTypeList);
        request.setAttribute("book",  book);
        return "Book_modify";
		 
	}
	
	@RequestMapping(value="/{barcode}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String barcode,Model model,HttpServletRequest request) throws Exception {
		/*根据主键barcode获取Book对象*/
        Book book = bookService.getBookByBarcode(barcode);

        List<BookType> bookTypeList = bookTypeService.QueryAllBookTypeInfo();
        request.setAttribute("bookTypeList", bookTypeList);
        request.setAttribute("book",  book);
        return "Book_frontshow";
		 
	}
	
	

	@RequestMapping(value = "/{barcode}/update", method = RequestMethod.POST)
	public String update(@Validated Book book, BindingResult br,
			Model model, HttpServletRequest request) throws Exception {
		if (br.hasErrors()) { 
			model.addAttribute(book);
			/*查询所有的BookType信息*/
	        List<BookType> bookTypeList = bookTypeService.QueryAllBookTypeInfo();
	        request.setAttribute("bookTypeList", bookTypeList);
			return "Book_modify";
		}
		
		String photoBookFileName = this.handlePhotoFileUpload(request, "photoBookFile");
		if(!photoBookFileName.equals("upload/NoImage.jpg"))book.setPhotoBook(photoBookFileName); 
        
		try {
			BookType bookType = bookTypeService.GetBookTypeByBookTypeId(book.getBookType().getBookTypeId());
			book.setBookType(bookType);
			bookService.updateBook(book);
			request.setAttribute("message", java.net.URLEncoder.encode(
					"图书更新成功!", "GBK"));
			return "message";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", java.net.URLEncoder.encode("图书更新失败!","GBK"));
			return "error";
		} 
	}
	
	
	@RequestMapping(value="/{barcode}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String barcode,HttpServletRequest request) throws UnsupportedEncodingException {
		  try { 
			  bookService.deleteBook(barcode);
	           
	            request.setAttribute("message", java.net.URLEncoder.encode(
						"图书删除成功!", "GBK"));
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", java.net.URLEncoder
						.encode("图书删除失败!","GBK"));
				return "error";
	        }
	}
	
	
	

	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String OutToExcel(String barcode,@ModelAttribute BookType bookType,String bookName,String publishDate, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		 if(bookName == null) bookName = "";
	     if(publishDate == null) publishDate = "";
	     List<Book> bookList = bookService.queryBook(bookName,bookType,barcode,publishDate);
	        ExportExcelUtil ex = new ExportExcelUtil();
	        String title = "Book信息记录"; 
	        String[] headers = { "图书条形码","图书名称","图书所在类别","图书价格","库存","出版社","出版日期","图书图片"};
	        List<String[]> dataset = new ArrayList<String[]>(); 
	        for(int i=0;i<bookList.size();i++) {
	        	Book book = bookList.get(i); 
	        	dataset.add(new String[]{book.getBarcode(),book.getBookName(),book.getBookType().getBookTypeName(),
	book.getPrice() + "",book.getCount() + "",book.getPublish(),book.getPublishDate(),book.getPhotoBook()});
	        }
	        /*
	        OutputStream out = null;
			try {
				out = new FileOutputStream("C://output.xls");
				ex.exportExcel(title,headers, dataset, out);
			    out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
			 
			OutputStream out = null;//创建一个输出流对象 
			try {  
				out = response.getOutputStream();//
				response.setHeader("Content-disposition","attachment; filename="+"Book.xls");//filename是下载的xls的名，建议最好用英文 
				response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
				response.setHeader("Pragma","No-cache");//设置头 
				response.setHeader("Cache-Control","no-cache");//设置头 
				response.setDateHeader("Expires", 0);//设置日期头  
				String rootPath = request.getSession().getServletContext().getRealPath("/");
				ex.exportExcel(rootPath,title,headers, dataset, out);
				out.flush();
			} catch (IOException e) { 
				e.printStackTrace(); 
			}finally{
				try{
					if(out!=null){ 
						out.close(); 
					}
				}catch(IOException e){ 
					e.printStackTrace(); 
				} 
			}
			return null;
	}
	
	
	
	
	
	
	
	
	
}
