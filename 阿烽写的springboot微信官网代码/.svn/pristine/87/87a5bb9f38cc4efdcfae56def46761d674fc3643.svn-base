package com.kingtechfin.wxthirdparty.controller.common;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxVipcnAuthInfo;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;

public class CheckAuth {
    public ServerResult getAuthAppid(Long orgId) {
        if (orgId == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        WxVipcnAuthInfo wxVipcnAuthInfo = new WxVipcnAuthInfo();
        wxVipcnAuthInfo.setOrgId(orgId);
        wxVipcnAuthInfo = wxVipcnAuthInfo.selectOne(new EntityWrapper(wxVipcnAuthInfo));
        String authAppid = wxVipcnAuthInfo != null ? wxVipcnAuthInfo.getAuthorizerAppid() : null;

        if (authAppid == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        return new ServerResult(authAppid);
    }
}
