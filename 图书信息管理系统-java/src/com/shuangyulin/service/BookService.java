package com.shuangyulin.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shuangyulin.mapper.BookMapper;
import com.shuangyulin.po.Book;
import com.shuangyulin.po.BookType;

@Service @Transactional
public class BookService {

	@Resource BookMapper bookMapper;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 5;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加Book信息*/
    public void addBook(Book book) throws Exception {
    	bookMapper.addBook(book); 
    }

    /*查询Book信息*/
    public ArrayList<Book> queryBook(String bookName,BookType bookType,String barcode,String publishDate,int currentPage) throws Exception { 
    	String where = "where 1=1";
    	if(!bookName.equals("")) where = where + " and t_book.bookName like '%" + bookName + "%'";
    	if(null != bookType && bookType.getBookTypeId() != null && bookType.getBookTypeId()!=0) where += " and t_book.bookType=" + bookType.getBookTypeId();
    	if(!barcode.equals("")) where = where + " and t_book.barcode like '%" + barcode + "%'";
    	if(!publishDate.equals("")) where = where + " and t_book.publishDate like '%" + publishDate + "%'"; 
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	return bookMapper.queryBook(where, startIndex, this.PAGE_SIZE);
    	
    }


    //查询图书信息
    public ArrayList<Book> queryBook(String bookName,BookType bookType,String barcode,String publishDate) throws Exception { 
    	String where = "where 1=1";
    	if(!bookName.equals("")) where = where + " and t_book.bookName like '%" + bookName + "%'";
    	if(null != bookType && bookType.getBookTypeId() != null && bookType.getBookTypeId()!=0) where += " and t_book.bookType=" + bookType.getBookTypeId();
    	if(!barcode.equals("")) where = where + " and t_book.barcode like '%" + barcode + "%'";
    	if(!publishDate.equals("")) where = where + " and t_book.publishDate like '%" + publishDate + "%'"; 
    	return bookMapper.queryBookList(where);
    }

    
    public ArrayList<Book> queryAllBookInfo() throws Exception { 
        return bookMapper.queryBookList("where 1=1");
    }

    /*计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String bookName,BookType bookType,String barcode,String publishDate) throws Exception {
    	String where = "where 1=1";
    	if(!bookName.equals("")) where = where + " and t_book.bookName like '%" + bookName + "%'";
    	if(null != bookType && bookType.getBookTypeId() != null && bookType.getBookTypeId()!=0) where += " and t_book.bookType=" + bookType.getBookTypeId();
    	if(!barcode.equals("")) where = where + " and t_book.barcode like '%" + barcode + "%'";
    	if(!publishDate.equals("")) where = where + " and t_book.publishDate like '%" + publishDate + "%'"; 
    	
        recordNumber = bookMapper.queryBookCount(where);
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    public Book getBookByBarcode(String barcode) throws Exception {
        Book book = bookMapper.getBook(barcode); 
        return book;
    }

    /*更新Book信息*/
    public void updateBook(Book book) throws Exception {
        bookMapper.updateBook(book);
    }

    /*删除Book信息*/
    public void deleteBook (String barcode) throws Exception {
        bookMapper.deleteBook(barcode);
    }

}
