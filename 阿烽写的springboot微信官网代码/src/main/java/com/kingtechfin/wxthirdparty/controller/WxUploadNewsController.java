package com.kingtechfin.wxthirdparty.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.*;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.util.MyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-07-13
 */
@RestController
@RequestMapping("/wx_news")
public class WxUploadNewsController {
    @Value("${wechat.media.upload-path}")
    private String uploadPath;

    //处理图文素材上传
    @PostMapping("/wx_news_upload")
    public ServerResult uploadNews(@RequestBody WxUploadNews wxUploadNews) {
        ServerResult ret = checkAuth(wxUploadNews.getOrgId());
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }
        String authAppid = (String) ret.getResult();
        wxUploadNews.setAuthorizerAppid(authAppid);
        wxUploadNews.setCreateTime(MyUtil.get10CurrentTime());
        wxUploadNews.insert();
        return new ServerResult(wxUploadNews);
    }

    //处理获取图文素材列表
    @PostMapping("/wx_get_news_list")
    public ServerResult getNewsList(@RequestBody WxUploadNews wxUploadNews) {
        ServerResult ret = checkAuth(wxUploadNews.getOrgId());

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        wxUploadNews.setAuthorizerAppid(authAppid);
        return new ServerResult(wxUploadNews.selectList(new EntityWrapper(wxUploadNews).orderBy("createTime", false)));
    }

    //处理修改图文素材
    @PostMapping("/wx_update_news")
    public ServerResult updateNewsById(@RequestBody WxUploadNews wxUploadNews) {

        ServerResult ret = checkOrgidAndId(wxUploadNews);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        return new ServerResult(wxUploadNews.updateById());
    }

    //处理删除图文素材
    @PostMapping("/wx_del_news")
    public ServerResult detNewsById(@RequestBody WxUploadNews wxUploadNews) {

        ServerResult ret = checkOrgidAndId(wxUploadNews);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }
        return new ServerResult(wxUploadNews.deleteById());
    }

    /**
     * 私有部分
     */
    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }

    private ServerResult checkOrgidAndId(WxUploadNews wxUploadNews) {
        Long orgId = wxUploadNews.getOrgId();
        Long id = wxUploadNews.getId();

        if (orgId == null && id == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(orgId);

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        return new ServerResult();
    }
}

