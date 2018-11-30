package com.kingtechfin.wxthirdparty.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kingtechfin.wxthirdparty.util.MyUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author MrChao
 * @since 2018-07-10
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_manager_msg")
public class WxManagerMsg extends Model<WxManagerMsg> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 收到消息ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long msgId;
    /**
     * 回复消息ID，多对一msgId
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sMsgId;
    /**
     * 授权公众号appid
     */
    private String authorizerAppid;
    /**
     * 发送人
     */
    private String fromUser;
    @TableField(exist = false)
    private String fromName;
    @TableField(exist = false)
    private String fromSex;
    @TableField(exist = false)
    private String fromCity;
    @TableField(exist = false)
    private String fromHeadImgUrl;

    /**
     * 接收人
     */
    private String toUser;
    /**
     * 传输消息类型，r为接受，s为发送
     */
    private String msgTransmitType;
    /**
     * 消息时间
     */
    private Long createTime;

    /**
     * 自定义时间格式
     */
    @TableField(exist = false)
    private String strCreateTime;
    /**
     * 消息类型，text，image，目前储存着两种
     */
    private String msgType;
    /**
     * 消息类型为text时，消息内容
     */
    private String content;
    /**
     * 消息类型为image时，图片url
     */
    private String picUrl;
    /**
     * msgTransmitType类型为r时是否收藏
     */
    private Integer favorite;
    /**
     * msgTransmitType类型为r时是否已回复
     */
    private Integer replied;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getsMsgId() {
        return sMsgId;
    }

    public void setsMsgId(Long sMsgId) {
        this.sMsgId = sMsgId;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsgTransmitType() {
        return msgTransmitType;
    }

    public void setMsgTransmitType(String msgTransmitType) {
        this.msgTransmitType = msgTransmitType;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public Integer getReplied() {
        return replied;
    }

    public void setReplied(Integer replied) {
        this.replied = replied;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromSex() {
        return fromSex;
    }

    public void setFromSex(String fromSex) {
        this.fromSex = fromSex;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromHeadImgUrl() {
        return fromHeadImgUrl;
    }

    public void setFromHeadImgUrl(String fromHeadImgUrl) {
        this.fromHeadImgUrl = fromHeadImgUrl;
    }


    public String getStrCreateTime() {
        if (this.createTime != null) {
            return MyUtil.getCustomizedDate("yyyyMMdd", this.createTime * 1000);
        } else {
            return null;
        }
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxManagerMsg{" +
        ", id=" + id +
        ", msgId=" + msgId +
        ", sMsgId=" + sMsgId +
        ", authorizerAppid=" + authorizerAppid +
        ", fromUser=" + fromUser +
        ", toUser=" + toUser +
        ", msgTransmitType=" + msgTransmitType +
        ", createTime=" + createTime +
        ", msgType=" + msgType +
        ", content=" + content +
        ", picUrl=" + picUrl +
        ", favorite=" + favorite +
        ", replied=" + replied +
        "}";
    }
}
