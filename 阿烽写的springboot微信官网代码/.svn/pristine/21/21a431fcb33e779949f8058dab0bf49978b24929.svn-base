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
@TableName("wx_thirdparty_info")
public class WxThirdpartyInfo extends Model<WxThirdpartyInfo> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 微信appid，微信开放平台获取
     */
    private String appid;
    /**
     * 微信app秘钥，微信开放平台获取
     */
    private String appsecret;
    /**
     * 微信第三方设置token
     */
    private String token;
    /**
     * 微信第三方设置加解密
     */
    private String aesKey;
    /**
     * 微信授权后回调URI
     */
    private String redirectUrl;
    private String verifyTicket;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getVerifyTicket() {
        return verifyTicket;
    }

    public void setVerifyTicket(String verifyTicket) {
        this.verifyTicket = verifyTicket;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxThirdpartyInfo{" +
        ", id=" + id +
        ", appid=" + appid +
        ", appsecret=" + appsecret +
        ", token=" + token +
        ", aesKey=" + aesKey +
        ", redirectUrl=" + redirectUrl +
        ", verifyTicket=" + verifyTicket +
        "}";
    }
}
