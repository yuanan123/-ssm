package com.kingtechfin.wxthirdparty.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxHistoryNews;
import com.kingtechfin.wxthirdparty.entity.WxHistoryNewsReveiveUsers;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.service.WxHistoryNewsService;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMassMessageService;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @since 2018-07-16
 */
@RestController
@RequestMapping("/wx_his_news")
public class WxHistoryNewsController {
    @Autowired
    private WxHistoryNewsService wxHistoryNewsService;

    @Autowired
    private WxThirdServiceImpl wxThirdService;

    // 获取已发送图文消息
    @PostMapping("/wx_get_his_news_list")
    public ServerResult getHisNewsList(@RequestBody WxHistoryNews wxHistoryNews) {
        Long orgId = wxHistoryNews.getOrgId();

        if (orgId == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(orgId.longValue());

        String authAppid = (String) ret.getResult();
        wxHistoryNews.setAuthorizerAppid(authAppid);

        return new ServerResult(wxHistoryNewsService.getHisNewsList(wxHistoryNews));
    }

    // 删除已发送图文消息
    @PostMapping("/wx_del_his_news")
    @Transactional
    public ServerResult delHisNews(@RequestBody Map<String, String> reqMap) {
        String orgId = reqMap.get("orgId");
        String id = reqMap.get("id");
        String authAppid = reqMap.get("authAppid");
        String msgId = reqMap.get("msgId");
        if (orgId == null && id == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(Long.valueOf(orgId));
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        WxMpMassMessageService wxMpMassMessageService = getWxMpMassMessageService(String.valueOf(authAppid));
        try {
            wxMpMassMessageService.delete(Long.valueOf(msgId), Integer.valueOf(0));
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ServerResult(ServerStatusEnum.SERVICE_WX_SERVER_ERROR, e.getMessage());
        }

        WxHistoryNews wxHistoryNews = new WxHistoryNews();
        wxHistoryNews.setId(Long.valueOf(id));
        wxHistoryNews = wxHistoryNews.selectById();
        WxHistoryNewsReveiveUsers wxHistoryNewsReveiveUsers = new WxHistoryNewsReveiveUsers();
        wxHistoryNewsReveiveUsers.setMsgId(wxHistoryNews.getMsgId());
        wxHistoryNewsReveiveUsers.delete(new EntityWrapper(wxHistoryNewsReveiveUsers));
        wxHistoryNews.deleteById();

        return new ServerResult(ServerStatusEnum.SERVICE_OK);
    }
   /**
    * 私有部分
    * */

    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }

    // 获取授权公众号媒体服务对象
    private WxMpMassMessageService getWxMpMassMessageService(String authAppid) {
        return wxThirdService
                .getWxOpenComponentService()
                .getWxMpServiceByAppid(authAppid).getMassMessageService();
    }
}

