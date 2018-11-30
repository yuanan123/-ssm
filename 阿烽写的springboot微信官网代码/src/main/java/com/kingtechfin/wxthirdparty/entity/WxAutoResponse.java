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
 * @since 2018-07-04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_auto_response")
public class WxAutoResponse extends Model<WxAutoResponse> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String authorizerAppid;
    /**
     * 自动回复消息类型：subscribe 关注自动回复消息类型；auto 自动回复消息类型；其他为关键字回复规则，匹配关键字，唯一索引；
     */
    private String msgType;
    /**
     * 关键字回复类型的关键字
     */
    private String msgKey;
    /**
     * 自动回复信息
     */
    private String responseMsg;

    @TableField(exist = false)
    private Long orgId;


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

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxAutoResponse{" +
        ", id=" + id +
        ", authorizerAppid=" + authorizerAppid +
        ", msgType=" + msgType +
        ", msgKey=" + msgKey +
        ", responseMsg=" + responseMsg +
        "}";
    }
}
