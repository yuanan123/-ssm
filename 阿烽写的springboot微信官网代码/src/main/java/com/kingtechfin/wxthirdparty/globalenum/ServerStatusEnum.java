package com.kingtechfin.wxthirdparty.globalenum;

/**
 * Created by mrchao on 2018/5/18.
 */
public enum ServerStatusEnum {
    SERVICE_OK(0, "服务正常"),
    SERVICE_FAILED(-1, "服务调用失败"),
    SERVICE_PARAM_NULL(10001, "参数空值"),
    SERVICE_DUPLICATE_KEY(20001,"自动回复规则名称重复"),
    SERVICE_WX_QUERY_AUTH_FAILED(10002, "获取微信公众号授权失败"),
    SERVICE_WX_NO_AUTH(10003, "微信公众号未授权"),
    SERVICE_WX_NO_DATA(10004, "数据不存在"),
    SERVICE_WX_PARAM_ERROR(10005, "参数不正确"),
    SERVICE_WX_SERVER_ERROR(10006, "微信公众号服务错误"),
    SERVICE_WX_NEWS_HAVE_MEDIA(10007,"微信新闻存在该图片，不能删除"),
    SERVICE_WX_MENU_ZERO(10008,"请创建至少一个菜单再进行推送"),

    SERVICE_END(99999, "枚举结束");

    private int status;
    private String msg;

    ServerStatusEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

}
