package com.kingtechfin.wxthirdparty.controller;


import com.kingtechfin.wxthirdparty.Result.ExceptionResult;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxManagerMsg;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.service.WxManagerMsgService;
import com.kingtechfin.wxthirdparty.util.MyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-07-10
 */
@RestController
@RequestMapping("/wx_manager_msg")
public class WxManagerMsgController {
    @Autowired
    private WxManagerMsgService wxManagerMsgService;

    @RequestMapping("/wx_get_msg_list")
    public ServerResult wxGetRevmsgList(@RequestBody Map<String, Object> reqMap) {
        String orgId =        (String)reqMap.get("orgId");
        String msgType =      (String)reqMap.get("msgType");
        String openId =       (String)reqMap.get("openId");
        String favorite =     (String)reqMap.get("favorite");
        String filterText =   (String)reqMap.get("filterText");
        String filterTime =   (String)reqMap.get("filterTime");
        ServerResult ret = checkAuth(Long.valueOf(orgId));

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();

        WxManagerMsg wxManagerMsg = new WxManagerMsg();
        wxManagerMsg.setAuthorizerAppid(authAppid);
        if (StringUtils.isNotEmpty(msgType)) {
            wxManagerMsg.setMsgTransmitType(msgType);
        }

        if (StringUtils.isNotEmpty(openId)) {
            wxManagerMsg.setFromUser(openId);
            wxManagerMsg.setToUser(openId);
        }

        if (StringUtils.isNotEmpty(favorite)) {
            wxManagerMsg.setFavorite(Integer.valueOf(favorite));
        }

        if (StringUtils.isNotEmpty(filterText)) {
            wxManagerMsg.setContent(filterText);
        }

        if (StringUtils.isNotEmpty(filterTime)) {
            wxManagerMsg.setCreateTime(MyUtil.get10CurrentTime() - 24*60*60*Long.valueOf(filterTime));
        } else {
            wxManagerMsg.setCreateTime(MyUtil.get10CurrentTime() - 24*60*60*Long.valueOf(5));
        }

        return new ServerResult(wxManagerMsgService.getMsgList(wxManagerMsg));
    }

    @RequestMapping("/wx_favorite_revmsg")
    public ServerResult wxFavoriteRevmsg(@RequestBody WxManagerMsg wxManagerMsg) {
        Integer favorite = 1;
        if (wxManagerMsg.getId() == null || wxManagerMsg.getFavorite() == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        if (wxManagerMsg.getFavorite() != 1) {
            favorite = 0;
        }

        wxManagerMsg.setFavorite(favorite);

        return new ServerResult(wxManagerMsg.updateById());
    }

    @RequestMapping("/wx_res_revmsg")
    public ServerResult wxResRevmsg(@RequestBody WxManagerMsg wxManagerMsg) {
        if (wxManagerMsg.getId() == null || wxManagerMsg.getContent() == null) {

            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        boolean ret = true;
        try {
            wxManagerMsgService.responseMsg(wxManagerMsg);
        } catch (ExceptionResult e) {
            return e.getServerResult();
        }

        return new ServerResult();
    }

    @RequestMapping("/wx_del_revmsg")
    public ServerResult wxDelRevmsg(@RequestBody WxManagerMsg wxManagerMsg) {
        if (wxManagerMsg.getId() == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        return new ServerResult(wxManagerMsg.deleteById());
    }

    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }
}

