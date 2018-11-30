package com.kingtechfin.wxthirdparty.controller;

import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.config.Test_WxMpProperties;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxMenu;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.service.WxMenuService;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-06-23
 */
@RestController
@RequestMapping("/wx_menu")
public class WxMenuController {
    @Autowired
    private WxMenuService wxMenuService;
    @Autowired
    private WxThirdServiceImpl wxThirdService;

    // test begin
    @Autowired
    WxMpService wxMpService;
    @Autowired
    Test_WxMpProperties wxMpProperties;
    // test end

    @PostMapping("/wx_get_menu")
    public ServerResult wxGetMenu(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        ServerResult ret = checkAuth(orgId);

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();

        return wxMenuService.getWxMenu(authAppid);
    }

    @PostMapping("/wx_add_menu")
    public ServerResult wxAddMenu(@RequestBody WxMenu wxMenu) {
        ServerResult ret = checkAuth(wxMenu.getOrgId());

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        wxMenu.setAuthorizerAppid(authAppid);
        return wxMenuService.addWxMenu(wxMenu);
    }

    @PostMapping("/wx_update_menu")
    public ServerResult wx_update_Menu(@RequestBody WxMenu wxMenu) {
        if (wxMenu.getId() == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }
        return new ServerResult(wxMenuService.updateById(wxMenu));
    }

    @PostMapping("/wx_del_menu")
    public ServerResult wxDelMenu(@RequestBody WxMenu wxMenu) {
        if (wxMenu.getId() == null && wxMenu.getMenuId() == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }
        return wxMenuService.delWxMenu(wxMenu);
    }

    @PostMapping("/test_wx_mp_push_menu")
    public ServerResult testWxMpPushMenu(@RequestBody Map<String, String> reqMap) {
        return wxMenuService.pushWxMenu(
                wxMpProperties.getAppId(),
                wxMpService.getMenuService()
        );
    }

    @PostMapping("/wx_push_menu")
    public ServerResult wxPushMenu(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        ServerResult ret = checkAuth(orgId);

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        return wxMenuService.pushWxMenu(
                authAppid,
                wxThirdService.getWxOpenComponentService().getWxMpServiceByAppid(authAppid).getMenuService()
        );
    }

    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }

}

