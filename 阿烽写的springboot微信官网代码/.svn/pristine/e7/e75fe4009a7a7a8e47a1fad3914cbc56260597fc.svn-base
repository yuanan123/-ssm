package com.kingtechfin.wxthirdparty.thirdman;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.entity.WxThirdpartyInfo;
import com.kingtechfin.wxthirdparty.entity.WxVipcnAuthInfo;
import com.kingtechfin.wxthirdparty.msghandle.*;
import com.kingtechfin.wxthirdparty.service.WxThirdpartyInfoService;
import com.kingtechfin.wxthirdparty.service.WxVipcnAuthInfoService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class WxThirdServiceImpl extends WxOpenServiceImpl {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String redirectUrl;

    @Autowired
    WxThirdpartyInfoService wxThirdpartyInfoService;

    @Autowired
    private WxVipcnAuthInfoService wxVipcnAuthInfoService;

    @Autowired
    private LogHandler logHandler;
    @Autowired
    protected NullHandler nullHandler;
    @Autowired
    private MenuHandler menuHandler;
    @Autowired
    private MsgHandler msgHandler;
    @Autowired
    private SubscribeHandler subscribeHandler;
    @Autowired
    private UnsubscribeHandler unsubscribeHandler;

    private WxOpenMessageRouter wxOpenMessageRouter;

    @PostConstruct
    public void init() {
        WxThirdpartyInfo wxThirdpartyInfo = wxThirdpartyInfoService.selectOne(new EntityWrapper<WxThirdpartyInfo>());

        if (wxThirdpartyInfo != null) {
            WxOpenConfigStorage wxOpenConfigStorage = null;
            if (getWxOpenConfigStorage() != null)
            {
                wxOpenConfigStorage = getWxOpenConfigStorage();
            } else {
                wxOpenConfigStorage = new WxOpenInMemoryConfigStorage();
            }
            wxOpenConfigStorage.setComponentAppId(wxThirdpartyInfo.getAppid());
            wxOpenConfigStorage.setComponentAppSecret(wxThirdpartyInfo.getAppsecret());
            wxOpenConfigStorage.setComponentToken(wxThirdpartyInfo.getToken());
            wxOpenConfigStorage.setComponentAesKey(wxThirdpartyInfo.getAesKey());
            wxOpenConfigStorage.setComponentVerifyTicket(wxThirdpartyInfo.getVerifyTicket());
            redirectUrl = wxThirdpartyInfo.getRedirectUrl();
            setWxOpenConfigStorage(wxOpenConfigStorage);
        } else {
            setWxOpenConfigStorage(new WxOpenInMemoryConfigStorage());
        }

        List<WxVipcnAuthInfo> wxVipcnAuthInfoList = wxVipcnAuthInfoService.selectList(new EntityWrapper<WxVipcnAuthInfo>());
        for (WxVipcnAuthInfo wxVipcnAuthInfo : wxVipcnAuthInfoList) {
            getWxOpenConfigStorage().setAuthorizerRefreshToken(wxVipcnAuthInfo.getAuthorizerAppid(), wxVipcnAuthInfo.getAuthorizerRefreshToken());
        }

        wxOpenMessageRouter = new WxOpenMessageRouter(this);
        // 记录所有事件的日志 （异步执行）
        wxOpenMessageRouter.rule().handler(this.logHandler).next();

        // 自定义菜单事件
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE).handler(this.subscribeHandler).end();

        // 取消关注事件
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE).handler(this.unsubscribeHandler).end();

        // 文字信息
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(this.msgHandler).end();

        // 图片信息
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.IMAGE).handler(this.msgHandler).end();

    }

    public WxOpenMessageRouter getWxOpenMessageRouter(){
        return wxOpenMessageRouter;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String route(WxOpenXmlMessage wxMessage) {
        if (StringUtils.equalsIgnoreCase(wxMessage.getInfoType(), "unauthorized")) {
            String appid = wxMessage.getAuthorizerAppid();
            WxVipcnAuthInfo wxVipcnAuthInfo = new WxVipcnAuthInfo();
            wxVipcnAuthInfo.setAuthorizerAppid(appid);
            return wxVipcnAuthInfoService.delete(new EntityWrapper<WxVipcnAuthInfo>(wxVipcnAuthInfo)) ? "success" : "";
        }else if (StringUtils.equalsIgnoreCase(wxMessage.getInfoType(), "component_verify_ticket")) {
            WxThirdpartyInfo wxThirdpartyInfo = new WxThirdpartyInfo();
            wxThirdpartyInfo.setAppid(wxMessage.getAppId());
            wxThirdpartyInfo.setVerifyTicket(wxMessage.getComponentVerifyTicket());
            wxThirdpartyInfoService.update(wxThirdpartyInfo, new EntityWrapper<WxThirdpartyInfo>().eq("appid", wxMessage.getAppId()));
        }

        return "success";
    }
}