package com.shuangyulin.mapper;


import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.shuangyulin.po.Admin;
import com.shuangyulin.po.Book;
import com.shuangyulin.po.BookType;

public interface BookMapper {
 
	/*添加图书信息*/
	public void addBook(Book book) throws Exception;
	
	/*
	public ArrayList<Book> queryBook(@Param("bookName") String bookName,@Param("bookType") BookType bookType,@Param("barcode") String barcode,
			@Param("publishDate") String publishDate,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;
	
	public int queryBookCount(@Param("bookName") String bookName,@Param("bookType") BookType bookType,@Param("barcode") String barcode,
			@Param("publishDate") String publishDate) throws Exception;
	*/
	
	
	
	public ArrayList<Book> queryBook(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;
	
	public ArrayList<Book> queryBookList(@Param("where") String where) throws Exception;
	
	public int queryBookCount(@Param("where") String where) throws Exception;
	
	
	
	/*查询某个图书信息*/
	public Book getBook(String barcode) throws Exception;
	
	
	/*更新图书信息*/
	public void updateBook(Book book) throws Exception;
	
	
	/*删除图书信息*/
	public void deleteBook(String barcode) throws Exception;
	
	
}
