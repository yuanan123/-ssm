package com.shuangyulin.po;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Book {
    /*图书名称*/
	@NotEmpty(message="图书名称不能为空")  
    private String bookName;
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /*图书所在类别*/
    private BookType bookType;
    public BookType getBookType() {
        return bookType;
    }
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    /*图书价格*/
    @NotNull(message="必须输入价格") 
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*库存*/
    @NotNull(message="必须输入库存")  
    private Integer count;
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

    /*出版社*/
    private String publish;
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }
    
    /*出版日期*/
    @NotEmpty(message="必须输入日期")  
    private String publishDate;
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    

    /*图书条形码*/
    @NotEmpty(message="图书条形码不能为空") 
    private String barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /*图书图片*/
    private String photoBook;
    public String getPhotoBook() {
        return photoBook;
    }
    public void setPhotoBook(String photoBook) {
        this.photoBook = photoBook;
    }

}