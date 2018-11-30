package com.kingtechfin.wxthirdparty.entity;

import com.baomidou.mybatisplus.activerecord.Model;
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
 * @since 2018-07-12
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_upload_media")
public class WxUploadMedia extends Model<WxUploadMedia> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String authorizerAppid;
    private String localName;
    private String serviceName;
    private String mediaType;
    private String mediaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createTime;
    private String wxUrl;


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

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxUploadMedia{" +
        ", id=" + id +
        ", authorizerAppid=" + authorizerAppid +
        ", localName=" + localName +
        ", serviceName=" + serviceName +
        ", mediaType=" + mediaType +
        ", mediaId=" + mediaId +
        ", createTime=" + createTime +
        ", wxUrl=" + wxUrl +
        "}";
    }
}
