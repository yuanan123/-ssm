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
 * @since 2018-07-05
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_subscribe_user_info")
public class WxSubscribeUserInfo extends Model<WxSubscribeUserInfo> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String authorizerAppid;
    private String openId;
    private Integer subscribe;
    private String nickname;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String headImgUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long subscribeTime;
    private Integer groupId;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxSubscribeUserInfo{" +
        ", id=" + id +
        ", authorizerAppid=" + authorizerAppid +
        ", openId=" + openId +
        ", subscribe=" + subscribe +
        ", nickname=" + nickname +
        ", sex=" + sex +
        ", country=" + country +
        ", province=" + province +
        ", city=" + city +
        ", headImgUrl=" + headImgUrl +
        ", subscribeTime=" + subscribeTime +
        ", groupId=" + groupId +
        "}";
    }
}
