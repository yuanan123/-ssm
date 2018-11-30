package com.kingtechfin.wxthirdparty.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxMenu;
import com.kingtechfin.wxthirdparty.globalenum.MysqlStatusMap;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.mapper.WxMenuMapper;
import com.kingtechfin.wxthirdparty.service.WxMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kingtechfin.wxthirdparty.util.MyUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MrChao
 * @since 2018-06-29
 */
@Service
public class WxMenuServiceImpl extends ServiceImpl<WxMenuMapper, WxMenu> implements WxMenuService {
    @Transactional
    public ServerResult getWxMenu(String authAppid) {
        WxMenu wxMenu = new WxMenu();
        wxMenu.setAuthorizerAppid(authAppid);
        wxMenu.setParentMenuId(0L);
        List<WxMenu> wxMenuList = selectList(new EntityWrapper(wxMenu).orderBy(true, "addTime", true));
        for (WxMenu wxFirstMenu : wxMenuList) {
            WxMenu wxChildMenu = new WxMenu();
            wxChildMenu.setParentMenuId(wxFirstMenu.getMenuId());
            List<WxMenu> wxChildMenuList = selectList(new EntityWrapper(wxChildMenu).orderBy(true, "addTime", true));
            wxFirstMenu.setChildMenu(wxChildMenuList);
        }

        return new ServerResult(wxMenuList);
    }

    @Transactional
    public ServerResult addWxMenu(WxMenu wxMenu) {
        wxMenu.setMenuKey(IdWorker.get32UUID());
        wxMenu.setMenuId(IdWorker.getId());
        wxMenu.setAddTime(MyUtil.getCurrentTime());
        return new ServerResult(wxMenu.insert());
    }

    @Transactional
    public ServerResult delWxMenu(WxMenu wxMenu) {
        WxMenu wxParentMenu = selectOne(new EntityWrapper(wxMenu));
        if (wxParentMenu == null) {
            return new ServerResult(true);
        }

        // 删除子菜单
        WxMenu wxChildMenu = new WxMenu();
        wxChildMenu.setParentMenuId(wxParentMenu.getMenuId());
        delete(new EntityWrapper(wxChildMenu));
        // 删除自己
        delete(new EntityWrapper(wxParentMenu));

        return new ServerResult(true);
    }

    @Transactional
    public ServerResult pushWxMenu(String authAppid, WxMpMenuService wxMpMenuService) {
        String ret = null;
        WxMenu wxMenu = new WxMenu();
        wxMenu.setAuthorizerAppid(authAppid);
        List<WxMenu> wxMenus = (List<WxMenu>)getWxMenu(authAppid).getResult();
        //当菜单数为0时，驳回推送
        if (wxMenus.size() == 0) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_MENU_ZERO);
        }
        try {
            ret = wxMpMenuService.menuCreate(buildWxMenu(wxMenus));
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ServerResult(e.getError().getErrorCode(), e.getError().getErrorMsg(), e.getError().toString());
        }
        return new ServerResult(ret);
    }

    private me.chanjar.weixin.common.bean.menu.WxMenu buildWxMenu(List<WxMenu> wxMenus) {
        me.chanjar.weixin.common.bean.menu.WxMenu menu = new me.chanjar.weixin.common.bean.menu.WxMenu();
        for (WxMenu wxMenu : wxMenus) {
            WxMenuButton wxMenuButton = buildMenuButton(wxMenu);
            if (!wxMenu.getChildMenu().isEmpty()) {
                wxMenuButton.setType(null);
                wxMenuButton.setKey(null);
                wxMenuButton.setUrl(null);
                for (WxMenu child : wxMenu.getChildMenu()) {
                    wxMenuButton.getSubButtons().add(buildMenuButton(child));
                }
            }
            menu.getButtons().add(wxMenuButton);
        }
        return menu;
    }

    private WxMenuButton buildMenuButton(WxMenu wxMenu) {
        WxMenuButton wxMenuButton = new WxMenuButton();
        wxMenuButton.setName(wxMenu.getMenuName());
        if (wxMenu.getMenuType().equals(WxConsts.MenuButtonType.CLICK)) {
            wxMenuButton.setType(WxConsts.MenuButtonType.CLICK);
            wxMenuButton.setKey(wxMenu.getMenuKey());
        } else if (wxMenu.getMenuType().equals(WxConsts.MenuButtonType.VIEW)) {
            wxMenuButton.setType(WxConsts.MenuButtonType.VIEW);
            wxMenuButton.setUrl(wxMenu.getMenuUrl());
        }
        return wxMenuButton;
    }
}
