package com.kingtechfin.wxthirdparty.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxVipcnAuthInfo;
import com.kingtechfin.wxthirdparty.service.WxVipcnAuthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-06-30
 */
@Controller
@RequestMapping("/wx_vipcn_auth_info")
public class WxVipcnAuthInfoController {
    @Autowired
    private WxVipcnAuthInfoService wxVipcnAuthInfoService;

    @PostMapping("/wx_add_or_update_info")
    public ServerResult wxAddInfo(@RequestBody WxVipcnAuthInfo wxVipcnAuthInfo) {
        return new ServerResult(wxVipcnAuthInfoService.insertOrUpdate(wxVipcnAuthInfo));
    }

    @PostMapping("/wx_get_info")
    public ServerResult wxGetInfo(@RequestBody WxVipcnAuthInfo wxVipcnAuthInfo) {
        return new ServerResult(wxVipcnAuthInfoService.selectOne(new EntityWrapper<WxVipcnAuthInfo>(wxVipcnAuthInfo)));
    }
}

