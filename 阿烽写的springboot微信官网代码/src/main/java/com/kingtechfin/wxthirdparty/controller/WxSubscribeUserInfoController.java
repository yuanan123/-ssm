package com.kingtechfin.wxthirdparty.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ExceptionResult;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxSubscribeUserInfo;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.service.WxSubscribeUserInfoService;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-07-05
 */
@RestController
@RequestMapping("/wx_manager_group")
public class WxSubscribeUserInfoController {
    @Autowired
    private WxThirdServiceImpl wxThirdService;

    @Autowired
    private WxSubscribeUserInfoService wxSubscribeUserInfoService;

    @PostMapping("/wx_get_subscribe_list")
    public ServerResult wxGetSubscribeList(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        ServerResult ret = checkAuth(orgId);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }
        String authAppid = (String) ret.getResult();
        WxSubscribeUserInfo wxSubscribeUserInfo = new WxSubscribeUserInfo();
        wxSubscribeUserInfo.setAuthorizerAppid(authAppid);
        wxSubscribeUserInfo.setSubscribe(Integer.valueOf((String.valueOf(reqMap.get("subscribe")))));
        return new ServerResult(wxSubscribeUserInfo.selectList(new EntityWrapper(wxSubscribeUserInfo)));
    }

    @PostMapping("/wx_refresh_subscribe_list")
    public ServerResult wxRefreshSubscribeList(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        ServerResult ret = checkAuth(orgId);

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }
        String authAppid = (String) ret.getResult();

        try {
            return new ServerResult(
                    wxSubscribeUserInfoService.RefreshSubscribeList(
                            wxThirdService .getWxOpenComponentService().getWxMpServiceByAppid(authAppid)
                    )
            );
        } catch (ExceptionResult exceptionResult) {
            return exceptionResult.getServerResult();
        }
    }

    /**
     * 私有部分
     * */
    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }
}

