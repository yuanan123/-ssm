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
 * @since 2018-07-03
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_vipcn_auth_info")
public class WxVipcnAuthInfo extends Model<WxVipcnAuthInfo> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 机构ID号
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;
    /**
     * 授权公众号appid
     */
    private String authorizerAppid;
    private String authorizerRefreshToken;
    private String nickName;
    private String userName;
    private String principalName;
    private String signature;
    private String headImg;
    private Integer serviceType;
    private Integer verifyType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(Integer verifyType) {
        this.verifyType = verifyType;
    }

    @Override
    protected Serializable pkVal() {
        return this.orgId;
    }

    @Override
    public String toString() {
        return "WxVipcnAuthInfo{" +
        ", id=" + id +
        ", orgId=" + orgId +
        ", authorizerAppid=" + authorizerAppid +
        ", authorizerRefreshToken=" + authorizerRefreshToken +
        ", nickName=" + nickName +
        ", userName=" + userName +
        ", principalName=" + principalName +
        ", signature=" + signature +
        ", headImg=" + headImg +
        ", serviceType=" + serviceType +
        ", verifyType=" + verifyType +
        "}";
    }
}
