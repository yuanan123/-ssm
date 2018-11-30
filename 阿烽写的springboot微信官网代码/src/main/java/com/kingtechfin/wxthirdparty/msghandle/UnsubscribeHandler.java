package com.kingtechfin.wxthirdparty.msghandle;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.entity.WxSubscribeUserInfo;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
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
public class UnsubscribeHandler extends AbstractHandler {

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    String openId = wxMessage.getFromUser();
    this.logger.info("取消关注用户 OPENID: " + openId);
    WxSubscribeUserInfo wxSubscribeUserInfo = new WxSubscribeUserInfo();
    wxSubscribeUserInfo.setSubscribe(0);
    wxSubscribeUserInfo.update(new EntityWrapper().eq("openId", openId));
    return null;
  }

}
