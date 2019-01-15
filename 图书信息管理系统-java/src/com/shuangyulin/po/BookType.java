package com.shuangyulin.po;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty; 

public class BookType {
    /*图书类别*/
    private Integer bookTypeId;
    public Integer getBookTypeId() {
        return bookTypeId;
    }
    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    /*类别名称*/
    @NotEmpty(message="图书类型不能为空")  
    private String bookTypeName;
    public String getBookTypeName() {
        return bookTypeName;
    }
    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    /*可借阅天数*/
    @NotNull(message="必须输入借阅天数")  
    private Integer days;
    public Integer getDays() {
        return days;
    }
    public void setDays(Integer days) {
        this.days = days;
    }

}