package com.shuangyulin.mapper;


import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.shuangyulin.po.Admin;
import com.shuangyulin.po.BookType;

public interface AdminMapper {
 
	public Admin findAdminByUserName(String username) throws Exception;
	
	public void changePassword(Admin admin) throws Exception;
	
	
	
}
