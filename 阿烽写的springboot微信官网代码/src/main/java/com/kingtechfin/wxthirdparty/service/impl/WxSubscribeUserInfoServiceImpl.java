package com.kingtechfin.wxthirdparty.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ExceptionResult;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxSubscribeUserInfo;
import com.kingtechfin.wxthirdparty.mapper.WxSubscribeUserInfoMapper;
import com.kingtechfin.wxthirdparty.service.WxSubscribeUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-05
 */
@Service
public class WxSubscribeUserInfoServiceImpl extends ServiceImpl<WxSubscribeUserInfoMapper, WxSubscribeUserInfo> implements WxSubscribeUserInfoService {
    @Transactional
    public List<WxSubscribeUserInfo> RefreshSubscribeList(WxMpService wxMpService) throws ExceptionResult {
        String authAppid = wxMpService.getWxMpConfigStorage().getAppId();
        try {
            List<WxMpUser> wxMpUserList = wxMpService.getUserService().userInfoList(wxMpService.getUserService().userList(null).getOpenids());
            List<WxSubscribeUserInfo> wxSubscribeUserInfoList = new ArrayList<>();
            for (WxMpUser wxMpUser : wxMpUserList) {
                WxSubscribeUserInfo wxSubscribeUserInfo = new WxSubscribeUserInfo();
                wxSubscribeUserInfo.setAuthorizerAppid(authAppid);
                wxSubscribeUserInfo.setOpenId(wxMpUser.getOpenId());
                wxSubscribeUserInfo.setSubscribe(wxMpUser.getSubscribe() ? 1 : 0);
                wxSubscribeUserInfo.setNickname(wxMpUser.getNickname());
                wxSubscribeUserInfo.setSex(wxMpUser.getSexDesc());
                wxSubscribeUserInfo.setCountry(wxMpUser.getCountry());
                wxSubscribeUserInfo.setProvince(wxMpUser.getProvince());
                wxSubscribeUserInfo.setCity(wxMpUser.getCity());
                wxSubscribeUserInfo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
                wxSubscribeUserInfo.setSubscribeTime(wxMpUser.getSubscribeTime());
                wxSubscribeUserInfo.setGroupId(wxMpUser.getGroupId());
                wxSubscribeUserInfoList.add(wxSubscribeUserInfo);
            }
            delete(new EntityWrapper().eq("authorizerAppid", authAppid));
            insertBatch(wxSubscribeUserInfoList);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new ExceptionResult(e.getMessage());
        }

        return selectList(new EntityWrapper().eq("authorizerAppid", authAppid));
    }

    public WxSubscribeUserInfo RefreshSubscribeOne(String openAppid, WxMpService wxMpService) throws ExceptionResult {
        String authAppid = wxMpService.getWxMpConfigStorage().getAppId();
        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openAppid);
            WxSubscribeUserInfo wxSubscribeUserInfo = new WxSubscribeUserInfo();
            wxSubscribeUserInfo.setAuthorizerAppid(authAppid);
            wxSubscribeUserInfo.setOpenId(wxMpUser.getOpenId());
            wxSubscribeUserInfo.setSubscribe(wxMpUser.getSubscribe() ? 1 : 0);
            wxSubscribeUserInfo.setNickname(wxMpUser.getNickname());
            wxSubscribeUserInfo.setSex(wxMpUser.getSexDesc());
            wxSubscribeUserInfo.setCountry(wxMpUser.getCountry());
            wxSubscribeUserInfo.setProvince(wxMpUser.getProvince());
            wxSubscribeUserInfo.setCity(wxMpUser.getCity());
            wxSubscribeUserInfo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
            wxSubscribeUserInfo.setSubscribeTime(wxMpUser.getSubscribeTime());
            wxSubscribeUserInfo.setGroupId(wxMpUser.getGroupId());
            delete(new EntityWrapper().eq("openId", openAppid));
            insert(wxSubscribeUserInfo);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new ExceptionResult(e.getMessage());
        }

        return selectOne(new EntityWrapper().eq("openId", openAppid));
    }
}
