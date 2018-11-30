package com.kingtechfin.wxthirdparty.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author MrChao
 * @since 2018-07-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_upload_news_wxs_list")
public class WxUploadNewsWxsList extends Model<WxUploadNewsWxsList> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String mediaId;
    private Integer newsIndex;
    private String author;
    private String title;
    private String digest;
    private String content;
    private String thumbName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createTime;

    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public WxUploadNewsWxsList(){}
    public WxUploadNewsWxsList(WxUploadNews wxUploadNews) {
        this.author = wxUploadNews.getAuthor();
        this.title = wxUploadNews.getTitle();
        this.digest = wxUploadNews.getDigest();
        this.content = wxUploadNews.getContent();
        this.thumbName = wxUploadNews.getThumbName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getNewsIndex() {
        return newsIndex;
    }

    public void setNewsIndex(Integer newsIndex) {
        this.newsIndex = newsIndex;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbName() {
        return thumbName;
    }

    public void setThumbName(String thumbName) {
        this.thumbName = thumbName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxUploadNewsWxsList{" +
        ", id=" + id +
        ", mediaId=" + mediaId +
        ", newsIndex=" + newsIndex +
        ", author=" + author +
        ", title=" + title +
        ", digest=" + digest +
        ", content=" + content +
        ", thumbName=" + thumbName +
        ", createTime=" + createTime +
        "}";
    }
}
