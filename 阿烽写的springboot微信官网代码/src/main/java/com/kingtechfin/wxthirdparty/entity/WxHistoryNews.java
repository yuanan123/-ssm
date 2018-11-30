package com.kingtechfin.wxthirdparty.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kingtechfin.wxthirdparty.util.MyUtil;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author MrChao
 * @since 2018-07-16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_history_news")
public class WxHistoryNews extends Model<WxHistoryNews> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String authorizerAppid;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long msgId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long msgDataId;
    private String mediaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createTime;
    private Integer errcode;
    private String errmsg;
    @TableField(exist = false)
    private WxUploadNewsToWxs news;
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;
    /**
     * 自定义时间格式
     */
    @TableField(exist = false)
    private String strCreateTime;

    public String getStrCreateTime() {
        if (this.createTime != null) {
            return MyUtil.getCustomizedDate("yyyyMMdd", this.createTime*1000);
        }
        return null;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public WxUploadNewsToWxs getNews() {
        return news;
    }

    public void setNews(WxUploadNewsToWxs news) {
        this.news = news;
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

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getMsgDataId() {
        return msgDataId;
    }

    public void setMsgDataId(Long msgDataId) {
        this.msgDataId = msgDataId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxHistoryNews{" +
        ", id=" + id +
        ", authorizerAppid=" + authorizerAppid +
        ", msgId=" + msgId +
        ", msgDataId=" + msgDataId +
        ", mediaId=" + mediaId +
        ", createTime=" + createTime +
        ", errcode=" + errcode +
        ", errmsg=" + errmsg +
        "}";
    }
}
