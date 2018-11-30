package com.kingtechfin.wxthirdparty.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxVipcnAuthInfo;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author mrchao
 */
@RestController
@RequestMapping("/wx_auth")
public class WxAuthController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxThirdServiceImpl wxOpenService;

    // 测试页面URL
    @GetMapping("/wx_goto_auth_url_show")
    public String gotoPreAuthUrlShow() {
        return "<a href='wx_goto_auth_url'>go</a>";
    }

    // 页面跳转微信授权页面URL
    @GetMapping("/wx_goto_auth_url")
    public void gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response) {
        String host = request.getHeader("host");
        String url = "http://" + host + wxOpenService.getRedirectUrl();
        try {
            url = wxOpenService.getWxOpenComponentService().getPreAuthUrl(url);
            response.sendRedirect(url);
        } catch (WxErrorException | IOException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }

    // 页面跳转微信授权页面URL
    @PostMapping("/wx_goto_auth_url_two")
    public String gotoPreAuthUrlTwo(@RequestParam("host") String host) throws WxErrorException {
        String url = "http://" + host + wxOpenService.getRedirectUrl();
        try {
            url = wxOpenService.getWxOpenComponentService().getPreAuthUrl(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw e;
        }
        return url;

    }


    // 测试微信授权成功跳转指定URL
    @GetMapping("/wx_jump")
    public WxOpenQueryAuthResult jump(@RequestParam("auth_code") String authorizationCode) {
        try {
            WxOpenQueryAuthResult queryAuthResult = wxOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
            logger.info("getQueryAuth", queryAuthResult);
            return queryAuthResult;
        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }

    // 微信跳转页面后访问后台授权是否成功
    @PostMapping("/wx_query_auth")
    public ServerResult queryAuth(@RequestBody Map<String, Object> reqMap) {
        String orgId = (String)reqMap.get("orgId");
        String authCode = (String) reqMap.get("authCode");
        if (orgId == null || StringUtils.isEmpty(authCode)) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        String appid = null;
        String authorizerRefreshToken = null;

        // 根据授权码询问授权appid
        try {
            WxOpenQueryAuthResult queryAuthResult = wxOpenService.getWxOpenComponentService().getQueryAuth(authCode);
            if (queryAuthResult == null) {
                return new ServerResult(ServerStatusEnum.SERVICE_WX_QUERY_AUTH_FAILED);
            }
            appid = queryAuthResult.getAuthorizationInfo().getAuthorizerAppid();
            authorizerRefreshToken = queryAuthResult.getAuthorizationInfo().getAuthorizerRefreshToken();
            logger.info("getQueryAuth", queryAuthResult);
        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }

        WxVipcnAuthInfo wxVipcnAuthInfo = new WxVipcnAuthInfo();
        wxVipcnAuthInfo.setOrgId(Long.valueOf(orgId));
        WxVipcnAuthInfo tempOne = wxVipcnAuthInfo.selectOne(new EntityWrapper(wxVipcnAuthInfo));
        if (tempOne != null) {
            wxVipcnAuthInfo.setId(tempOne.getId());
        }
        wxVipcnAuthInfo.setAuthorizerAppid(appid);
        wxVipcnAuthInfo.setAuthorizerRefreshToken(authorizerRefreshToken);

        WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = null;
        try {
            wxOpenAuthorizerInfoResult = getVipcndetails(appid);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ServerResult(ServerStatusEnum.SERVICE_FAILED, e.getMessage());
        }

        wxVipcnAuthInfo.setNickName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getNickName());
        wxVipcnAuthInfo.setUserName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getUserName());
        wxVipcnAuthInfo.setPrincipalName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getPrincipalName());
        wxVipcnAuthInfo.setSignature(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getSignature());
        wxVipcnAuthInfo.setHeadImg(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getHeadImg());
        wxVipcnAuthInfo.setServiceType(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getServiceTypeInfo());
        wxVipcnAuthInfo.setVerifyType(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo());

        // 机构代码与授权appid管理写入数据库
        return new ServerResult(wxVipcnAuthInfo.insertOrUpdate());

    }

    @PostMapping("/wx_get_vipcn_info")
    public ServerResult getVipcnInfo(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        return getVipcnInfo(orgId);
    }

    @PostMapping("/wx_refresh_vipcn_details")
    public ServerResult refreshVipcndetails(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        ServerResult ret = getVipcnInfo(orgId);

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        WxVipcnAuthInfo wxVipcnAuthInfo = (WxVipcnAuthInfo)ret.getResult();

        WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = null;
        try {
            wxOpenAuthorizerInfoResult = getVipcndetails(wxVipcnAuthInfo.getAuthorizerAppid());
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ServerResult(ServerStatusEnum.SERVICE_FAILED, e.getMessage());
        }

        wxVipcnAuthInfo.setNickName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getNickName());
        wxVipcnAuthInfo.setUserName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getUserName());
        wxVipcnAuthInfo.setPrincipalName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getPrincipalName());
        wxVipcnAuthInfo.setSignature(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getSignature());
        wxVipcnAuthInfo.setHeadImg(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getHeadImg());
        wxVipcnAuthInfo.setServiceType(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getServiceTypeInfo());
        wxVipcnAuthInfo.setVerifyType(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo());

        wxVipcnAuthInfo.updateById();

        return new ServerResult(wxVipcnAuthInfo);


    }

    @PostMapping("/wx_get_vipcn_list")
    public ServerResult wxGetVipcnList() {
        WxVipcnAuthInfo wxVipcnAuthInfo = new WxVipcnAuthInfo();
        return new ServerResult(wxVipcnAuthInfo.selectList(new EntityWrapper()));
    }

    private ServerResult getVipcnInfo(Long orgId) {

        if (orgId == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        WxVipcnAuthInfo wxVipcnAuthInfo = new WxVipcnAuthInfo();
        wxVipcnAuthInfo.setOrgId(orgId);
        wxVipcnAuthInfo = wxVipcnAuthInfo.selectOne(new EntityWrapper<>(wxVipcnAuthInfo));

        if (wxVipcnAuthInfo == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        return new ServerResult(wxVipcnAuthInfo);
    }

    private WxOpenAuthorizerInfoResult getVipcndetails(String authAppid) throws WxErrorException {
        try {
            WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = wxOpenService.getWxOpenComponentService().getAuthorizerInfo(authAppid);
            return wxOpenAuthorizerInfoResult;
        } catch (WxErrorException e) {
            logger.error("getVipcndetails", e);
            throw e;
        }
    }

}
