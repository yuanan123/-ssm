package com.shuangyulin.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shuangyulin.mapper.BookTypeMapper;
import com.shuangyulin.po.BookType;

@Service @Transactional
public class BookTypeService {

	@Resource BookTypeMapper bookTypeMapper;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

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

    /*添加BookType信息*/
    public void AddBookType(BookType bookType) throws Exception {
    	int bookTypeId = bookTypeMapper.insertBookType(bookType);
    	System.out.println(bookTypeId);
    }

    /*查询BookType信息*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BookType> QueryBookTypeInfo(int currentPage) throws Exception{
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	return bookTypeMapper.findBookTypeList(startIndex,this.PAGE_SIZE);
    	 
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BookType> QueryBookTypeInfo() throws Exception { 
    	return bookTypeMapper.findAllBookTypeList();
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BookType> QueryAllBookTypeInfo() throws Exception {
        return bookTypeMapper.findAllBookTypeList();
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() throws Exception { 
        recordNumber = bookTypeMapper.findBookTypeCount();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public BookType GetBookTypeByBookTypeId(int bookTypeId) throws Exception {
        return bookTypeMapper.findBookTypeById(bookTypeId);
    }

    /*更新BookType信息*/
    public void UpdateBookType(BookType bookType) throws Exception {
    	bookTypeMapper.updateBookType(bookType);
    }

    /*删除BookType信息*/
    public void DeleteBookType (int bookTypeId) throws Exception {
        bookTypeMapper.deleteBookType(bookTypeId);
    }

}
