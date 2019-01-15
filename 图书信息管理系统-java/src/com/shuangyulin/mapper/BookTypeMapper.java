package com.shuangyulin.mapper;


import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.shuangyulin.po.BookType;

public interface BookTypeMapper {
	
	public BookType findBookTypeById(int bookTypeId) throws Exception;
	public ArrayList<BookType> findBookTypeList(@Param("startIndex")int startIndex,@Param("pageSize") int pageSize) throws Exception;
	public ArrayList<BookType> findAllBookTypeList() throws Exception;
	public int findBookTypeCount() throws Exception;
	public int insertBookType(BookType bookType) throws Exception;
	public void deleteBookType(int bookTypeId) throws Exception;
	public void updateBookType(BookType bookType) throws Exception;
	
}
