package com.kingtechfin.wxthirdparty.msghandle;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.entity.WxMenu;
import com.kingtechfin.wxthirdparty.service.impl.WxMenuServiceImpl;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.MenuButtonType;

/**
 * <p>
 *
 * </p>
 *
 * @author MrChao
 * @since 2018-07-03
 */
@Component
public class MenuHandler extends AbstractHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {



    if (MenuButtonType.VIEW.equals(wxMessage.getEvent())) {
      return null;
    }

    WxMenu wxMenu = new WxMenu();
    wxMenu.setMenuKey(wxMessage.getEventKey());
    wxMenu = wxMenu.selectOne(new EntityWrapper(wxMenu));

    String msg = wxMenu.getMenuValue();

    if (msg == null) {
      msg = "功能正在完善，请期待";
    }

    return WxMpXmlOutMessage.TEXT().content(msg)
        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
        .build();
  }

}
