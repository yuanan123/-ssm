package com.kingtechfin.wxthirdparty.service;

import com.kingtechfin.wxthirdparty.Result.ExceptionResult;
import com.kingtechfin.wxthirdparty.entity.WxSubscribeUserInfo;
import com.baomidou.mybatisplus.service.IService;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-05
 */
public interface WxSubscribeUserInfoService extends IService<WxSubscribeUserInfo> {
    public List<WxSubscribeUserInfo> RefreshSubscribeList(WxMpService wxMpService) throws ExceptionResult;
    public WxSubscribeUserInfo RefreshSubscribeOne(String openAppid, WxMpService wxMpService) throws ExceptionResult;
}
