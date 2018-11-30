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
 * @since 2018-07-14
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_upload_news_to_wxs")
public class WxUploadNewsToWxs extends Model<WxUploadNewsToWxs> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String mediaId;
    private String authorizerAppid;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createTime;
    @TableField(exist = false)
    private Long orgId;
    @TableField(exist = false)
    private List<WxUploadNewsWxsList> wxUploadNewsWxsLists;

    public String getStrCreateTime() {
        if (this.createTime != null) {
            return MyUtil.getCustomizedDate("yyyyMMdd", this.createTime*1000);
        }
        return null;
    }

    /**
     * 自定义时间格式
     */
    @TableField(exist = false)
    private String strCreateTime;



    public List<WxUploadNewsWxsList> getWxUploadNewsWxsLists() {
        return wxUploadNewsWxsLists;
    }

    public void setWxUploadNewsWxsLists(List<WxUploadNewsWxsList> wxUploadNewsWxsLists) {
        this.wxUploadNewsWxsLists = wxUploadNewsWxsLists;
    }

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

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
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
        return "WxUploadNewsToWxs{" +
        ", id=" + id +
        ", mediaId=" + mediaId +
        ", authorizerAppid=" + authorizerAppid +
        ", createTime=" + createTime +
        "}";
    }
}
