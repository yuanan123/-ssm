package com.kingtechfin.wxthirdparty.msghandle;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.builder.TextBuilder;
import com.kingtechfin.wxthirdparty.entity.WxAutoResponse;
import com.kingtechfin.wxthirdparty.entity.WxSubscribeUserInfo;
import com.kingtechfin.wxthirdparty.service.WxAutoResponseService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author MrChao
 * @since 2018-07-03
 */
@Component
public class SubscribeHandler extends AbstractHandler {
  @Autowired
  WxAutoResponseService wxAutoResponseService;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) throws WxErrorException {

    this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

    // 获取微信用户基本信息
    WxMpUser userWxInfo = wxMpService.getUserService()
        .userInfo(wxMessage.getFromUser(), null);
    if (userWxInfo != null) {
      WxSubscribeUserInfo wxSubscribeUserInfo = new WxSubscribeUserInfo();
      wxSubscribeUserInfo.setAuthorizerAppid(wxMpService.getWxMpConfigStorage().getAppId());
      wxSubscribeUserInfo.setSubscribe(userWxInfo.getSubscribe() ? 1 : 0) ;
      wxSubscribeUserInfo.setOpenId(userWxInfo.getOpenId());
      wxSubscribeUserInfo.setNickname(userWxInfo.getNickname());
      wxSubscribeUserInfo.setSex(userWxInfo.getSexDesc());
      wxSubscribeUserInfo.setCountry(userWxInfo.getCountry());
      wxSubscribeUserInfo.setProvince(userWxInfo.getProvince());
      wxSubscribeUserInfo.setCity(userWxInfo.getCity());
      wxSubscribeUserInfo.setHeadImgUrl(userWxInfo.getHeadImgUrl());;
      wxSubscribeUserInfo.setSubscribeTime(userWxInfo.getSubscribeTime());
      wxSubscribeUserInfo.setGroupId(userWxInfo.getGroupId());
      boolean ret = wxSubscribeUserInfo.update(new EntityWrapper().eq("openId", wxSubscribeUserInfo.getOpenId()));
      if (!ret) {
        wxSubscribeUserInfo.insert();
      }
      this.logger.info(wxSubscribeUserInfo.toString());
    }

    WxAutoResponse wxAutoResponse = wxAutoResponseService.getByMsgType(wxMpService.getWxMpConfigStorage().getAppId(),"subscribe");
    String retMsg = wxAutoResponse != null ? wxAutoResponse.getResponseMsg() : "";

    try {
      return new TextBuilder().build(retMsg, wxMessage, wxMpService);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    return null;
  }
}
