package com.kingtechfin.wxthirdparty.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxHistoryNews;
import com.kingtechfin.wxthirdparty.entity.WxHistoryNewsReveiveUsers;
import com.kingtechfin.wxthirdparty.entity.WxSubscribeUserInfo;
import com.kingtechfin.wxthirdparty.entity.WxUploadNewsToWxs;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.service.WxHistoryNewsReveiveUsersService;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import com.kingtechfin.wxthirdparty.util.MyUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMassMessageService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx_publish_news")
public class WxPublishNewsController {

    @Autowired
    private WxThirdServiceImpl wxThirdService;

    @Autowired
    private WxHistoryNewsReveiveUsersService wxHistoryNewsReveiveUsersService;

    @PostMapping("/wx_preview_news")
    public ServerResult wxPreviewNew(@RequestBody Map<String, Object> reqMap) {
        String subUserId = String.valueOf(reqMap.get("subUserId"));
        if (subUserId == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }
        return previewNews(reqMap, Long.valueOf(subUserId));
    }

    @PostMapping("/wx_publish_news_to_all")
    public ServerResult wxPublishNewsToAll(@RequestBody Map<String, Object> reqMap) {
        return publishNewsToUser(reqMap, null);
    }

    @PostMapping("/wx_publish_news_to_users")
    public ServerResult wxPublishNewsToUsers(@RequestBody Map<String, Object> reqMap) {
        String strIds = (String) reqMap.get("ids");
        String[] strArrIds = strIds.split(",");
        Long[] longArrIds = new Long[strArrIds.length];
        for (int index = 0; index < strArrIds.length; index ++) {
            Long id = Long.parseLong(strArrIds[index]);
            longArrIds[index] = id;
        }
        List<Long> ids = Arrays.asList(longArrIds);
        return publishNewsToUser(reqMap, ids);
    }


    /**
     * 私有部分
     * */

    // 获取授权公众号媒体服务对象
    private WxMpMassMessageService getWxMpMassMessageService(String authAppid) {
        return wxThirdService
                .getWxOpenComponentService()
                .getWxMpServiceByAppid(authAppid).getMassMessageService();
    }

    // 获取消息素材
    private WxUploadNewsToWxs getWxUploadNewsToWxs(String authAppid, Long id) {
        WxUploadNewsToWxs wxUploadNewsToWxs = new WxUploadNewsToWxs();
        wxUploadNewsToWxs.setId(Long.valueOf(id));
        wxUploadNewsToWxs.setAuthorizerAppid(authAppid);
        return wxUploadNewsToWxs.selectById();
    }

    // 获取订阅用户
    private List<WxSubscribeUserInfo> getWxSubscribeUserInfoList(String authAppid,  List<Long> ids) {
        WxSubscribeUserInfo wxSubscribeUserInfo = new WxSubscribeUserInfo();
        wxSubscribeUserInfo.setAuthorizerAppid(authAppid);
        EntityWrapper entityWrapper = new EntityWrapper(wxSubscribeUserInfo);
        entityWrapper.eq("subscribe", 1);

        if (ids != null) {
            entityWrapper.in("id", ids);
        }

        return wxSubscribeUserInfo.selectList(entityWrapper);
    }

    private ServerResult previewNews(Map<String, Object> reqMap, Long id) {
        String orgId = String.valueOf(reqMap.get("orgId"));
        String mediaId =  String.valueOf(reqMap.get("mediaId"));

        if (orgId == null || mediaId == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(Long.valueOf(orgId));

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();

        // 获取授权公众号媒体服务对象
        WxMpMassMessageService wxMpMassMessageService = getWxMpMassMessageService(authAppid);
        if (wxMpMassMessageService == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        // 获取消息素材
        WxUploadNewsToWxs wxUploadNewsToWxs = getWxUploadNewsToWxs(authAppid, Long.valueOf(mediaId));
        if (wxUploadNewsToWxs == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA);
        }

        // 获取订阅用户列表
        List<WxSubscribeUserInfo> wxSubscribeUserInfoList = getWxSubscribeUserInfoList(authAppid, Arrays.asList(id));
        if (wxSubscribeUserInfoList == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }
        WxMpMassPreviewMessage wxMpMassPreviewMessage = new WxMpMassPreviewMessage();
        wxMpMassPreviewMessage.setToWxUserOpenid(wxSubscribeUserInfoList.get(0).getOpenId());
        wxMpMassPreviewMessage.setMediaId(wxUploadNewsToWxs.getMediaId());
        wxMpMassPreviewMessage.setMsgType("mpnews");

        WxMpMassSendResult wxMpMassSendResult = null;
        try {
            wxMpMassSendResult = wxMpMassMessageService.massMessagePreview(wxMpMassPreviewMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ServerResult(ServerStatusEnum.SERVICE_WX_SERVER_ERROR, e.getMessage());
        }
        return new ServerResult(wxMpMassSendResult);
    }

    @Transactional
    protected ServerResult publishNewsToUser(Map<String, Object> reqMap, List<Long> ids) {
        String orgId = String.valueOf(reqMap.get("orgId"));
        String mediaId =  String.valueOf(reqMap.get("mediaId"));

        if (orgId == null || mediaId == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(Long.valueOf(orgId));

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();

        // 获取授权公众号媒体服务对象
        WxMpMassMessageService wxMpMassMessageService = getWxMpMassMessageService(authAppid);
        if (wxMpMassMessageService == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        // 获取消息素材
        WxUploadNewsToWxs wxUploadNewsToWxs = getWxUploadNewsToWxs(authAppid, Long.valueOf(mediaId));
        if (wxUploadNewsToWxs == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA);
        }

        // 获取订阅用户列表
        List<WxSubscribeUserInfo> wxSubscribeUserInfoList = getWxSubscribeUserInfoList(authAppid, ids);
        if (wxSubscribeUserInfoList == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        // 创建消息对象
        WxMpMassOpenIdsMessage wxMpMassOpenIdsMessage = new WxMpMassOpenIdsMessage();
        wxMpMassOpenIdsMessage.setMsgType("mpnews");
        wxMpMassOpenIdsMessage.setMediaId(wxUploadNewsToWxs.getMediaId());


        for (WxSubscribeUserInfo item : wxSubscribeUserInfoList) {
            wxMpMassOpenIdsMessage.addUser(item.getOpenId());
        }

        // 群发消息
        WxMpMassSendResult wxMpMassSendResult = null;
        WxHistoryNews wxHistoryNews = null;

        //TODO: 正式群发接口 ---- 打开后删除 下列的test
        try {
             wxMpMassSendResult = wxMpMassMessageService.massOpenIdsMessageSend(wxMpMassOpenIdsMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ServerResult(ServerStatusEnum.SERVICE_WX_SERVER_ERROR, e.getMessage());
        }
        // test begin
        //wxMpMassSendResult = new WxMpMassSendResult();
        //wxMpMassSendResult.setMsgId(IdWorker.getIdStr());
        //wxMpMassSendResult.setErrorCode("0");
        //wxMpMassSendResult.setErrorMsg("ok");
        //wxMpMassSendResult.setMsgDataId("0");
        // test end

        if (wxMpMassSendResult.getMsgId() != null) {
            List<WxHistoryNewsReveiveUsers> wxHistoryNewsReveiveUsersList = new ArrayList<>();
            for (WxSubscribeUserInfo item : wxSubscribeUserInfoList) {
                WxHistoryNewsReveiveUsers wxHistoryNewsReveiveUsers = new WxHistoryNewsReveiveUsers();
                wxHistoryNewsReveiveUsers.setSubUserId(item.getId());
                wxHistoryNewsReveiveUsers.setMsgId(Long.valueOf(wxMpMassSendResult.getMsgId()));
                wxHistoryNewsReveiveUsersList.add(wxHistoryNewsReveiveUsers);
            }

            wxHistoryNewsReveiveUsersService.insertBatch(wxHistoryNewsReveiveUsersList);

            wxHistoryNews = new WxHistoryNews();
            wxHistoryNews.setAuthorizerAppid(authAppid);
            wxHistoryNews.setMediaId(wxUploadNewsToWxs.getMediaId());
            wxHistoryNews.setErrcode(Integer.valueOf(wxMpMassSendResult.getErrorCode()));
            wxHistoryNews.setErrmsg(wxMpMassSendResult.getErrorMsg());
            wxHistoryNews.setMsgId(Long.valueOf(wxMpMassSendResult.getMsgId()));
            wxHistoryNews.setMsgDataId(Long.valueOf(wxMpMassSendResult.getMsgDataId()));
            wxHistoryNews.setCreateTime(MyUtil.get10CurrentTime());
            wxHistoryNews.insert();
        }

        return new ServerResult(wxHistoryNews);
    }

    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }
}
