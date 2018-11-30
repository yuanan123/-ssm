package com.kingtechfin.wxthirdparty.controller;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxAutoResponse;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.service.WxAutoResponseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-07-04
 */
@RestController
@RequestMapping("/wx_auto_response")
public class WxAutoResponseController {
    @Autowired
    private WxAutoResponseService wxAutoResponseService;
    @PostMapping("/wx_set_auto_response")
    public ServerResult wxSetAutoResponse(@RequestBody WxAutoResponse wxAutoResponse) {

        boolean ret = true;

        if (wxAutoResponse.getId() == null) {
            ServerResult serverResult = checkAuth(wxAutoResponse.getOrgId());

            if (serverResult.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
                return serverResult;
            }

            String authAppid = (String)serverResult.getResult();
            wxAutoResponse.setAuthorizerAppid(authAppid);
            WxAutoResponse checkNull = wxAutoResponse.selectOne(new EntityWrapper<WxAutoResponse>()
                    .eq("authorizerAppid", authAppid)
                    .eq("msgType",wxAutoResponse.getMsgType()));
            if (checkNull != null) {
                return new ServerResult(20001, "自动回复规则名称重复", null);
            }
            ret = wxAutoResponse.insert();
        } else {
            ret = wxAutoResponse.updateById();
        }

        return new ServerResult(ret);
    }

    @PostMapping("/wx_get_auto_response")
    public ServerResult wxGetAutoResponse(@RequestBody WxAutoResponse wxAutoResponse) {

        ServerResult ret = checkAuth(wxAutoResponse.getOrgId());

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();

        String specialType [] = {"subscribe", "auto"};
        String msgType = wxAutoResponse.getMsgType();

        if (StringUtils.isEmpty(msgType)) {
            return new ServerResult(wxAutoResponseService.getByKeyList(authAppid));
        } else {
            if (StringUtils.equalsAny(msgType, specialType)) {
                return new ServerResult(wxAutoResponseService.getByMsgType( authAppid, msgType));
            } else {
                return new ServerResult(wxAutoResponseService.getByTypeOrKeyList(authAppid, msgType));
            }
        }
    }

    @PostMapping("/wx_del_auto_response")
    public ServerResult wxDelAutoResponse(@RequestBody WxAutoResponse wxAutoResponse) {
       return new ServerResult(wxAutoResponse.deleteById());
    }

    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }
}

