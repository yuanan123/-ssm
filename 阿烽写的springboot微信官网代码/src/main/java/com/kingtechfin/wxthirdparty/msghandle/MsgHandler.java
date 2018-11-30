package com.kingtechfin.wxthirdparty.msghandle;

import com.kingtechfin.wxthirdparty.builder.TextBuilder;
import com.kingtechfin.wxthirdparty.entity.WxAutoResponse;
import com.kingtechfin.wxthirdparty.entity.WxManagerMsg;
import com.kingtechfin.wxthirdparty.service.WxAutoResponseService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * <p>
 *
 * </p>
 *
 * @author MrChao
 * @since 2018-07-03
 */
@Component
public class MsgHandler extends AbstractHandler {
  @Autowired
  private WxAutoResponseService wxAutoResponseService;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxService,
                                  WxSessionManager sessionManager) {

    if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
      WxManagerMsg wxManagerMsg = new WxManagerMsg();
      wxManagerMsg.setAuthorizerAppid(wxService.getWxMpConfigStorage().getAppId());
      wxManagerMsg.setMsgId(wxMessage.getMsgId());
      wxManagerMsg.setCreateTime(wxMessage.getCreateTime());
      wxManagerMsg.setFromUser(wxMessage.getFromUser());
      wxManagerMsg.setToUser(wxMessage.getToUser());
      wxManagerMsg.setContent(wxMessage.getContent());
      wxManagerMsg.setPicUrl(wxMessage.getPicUrl());
      wxManagerMsg.setMsgType(wxMessage.getMsgType());
      wxManagerMsg.setMsgTransmitType("r");
      wxManagerMsg.insert();
    }

    String msg = wxMessage.getContent();
    WxAutoResponse wxAutoResponse = null;
    //查到相关的数据集合
    List<WxAutoResponse> byKeyLikeList = wxAutoResponseService.getByKeyLikeList(wxService.getWxMpConfigStorage().getAppId(), msg);
    //精准匹配关键字
    if (StringUtils.isNotEmpty(msg)) {
      for (WxAutoResponse war : byKeyLikeList) {
        String[] keyStrArr = war.getMsgKey().split(",");
        for (String key : keyStrArr) {
          if (key.equals(msg)) {
            return new TextBuilder().build(war.getResponseMsg(), wxMessage, wxService);
          }
        }
      }
    }

    if (wxAutoResponse == null) {
      wxAutoResponse = wxAutoResponseService.getByMsgType(wxService.getWxMpConfigStorage().getAppId(),"auto");
    }

    String content = wxAutoResponse != null ? wxAutoResponse.getResponseMsg() : "谢谢您的建议，稍后会给您回复！";

    return new TextBuilder().build(content, wxMessage, wxService);

  }

}
