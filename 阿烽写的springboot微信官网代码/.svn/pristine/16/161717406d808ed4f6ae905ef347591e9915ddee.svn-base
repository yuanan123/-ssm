package com.kingtechfin.wxthirdparty.service;

import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxMenu;
import com.baomidou.mybatisplus.service.IService;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MrChao
 * @since 2018-06-23
 */
public interface WxMenuService extends IService<WxMenu> {
    public ServerResult getWxMenu(String authAppid);
    public ServerResult addWxMenu(WxMenu wxMenu);
    public ServerResult delWxMenu(WxMenu wxMenu);
    public ServerResult pushWxMenu(String authAppid, WxMpMenuService wxMpMenuService);
}
