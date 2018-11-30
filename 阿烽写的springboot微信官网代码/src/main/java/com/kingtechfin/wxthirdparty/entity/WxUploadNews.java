package com.kingtechfin.wxthirdparty.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kingtechfin.wxthirdparty.util.MyUtil;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author MrChao
 * @since 2018-07-13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_upload_news")
public class WxUploadNews extends Model<WxUploadNews> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String authorizerAppid;
    private String author;
    private String title;
    private String digest;
    private String content;
    private String thumbName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)


    private Long createTime;

    @TableField(exist = false)
    private Long orgId;

    public String getStrCreateTime() {
        if (this.createTime != null) {
            return MyUtil.getCustomizedDate("yyyyMMdd", this.createTime*1000);
        }
        return null;
    }

    @TableField(exist = false)
    private String strCreateTime;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
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
        return "WxUploadNews{" +
        ", id=" + id +
        ", authorizerAppid=" + authorizerAppid +
        ", author=" + author +
        ", title=" + title +
        ", digest=" + digest +
        ", content=" + content +
        ", thumbName=" + thumbName +
        ", createTime=" + createTime +
        "}";
    }
}
