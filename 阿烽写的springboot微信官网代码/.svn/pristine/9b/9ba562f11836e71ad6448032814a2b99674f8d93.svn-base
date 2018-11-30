package com.kingtechfin.wxthirdparty.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxUploadMedia;
import com.kingtechfin.wxthirdparty.entity.WxUploadNews;
import com.kingtechfin.wxthirdparty.entity.WxUploadNewsToWxs;
import com.kingtechfin.wxthirdparty.entity.WxUploadNewsWxsList;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.service.WxUploadNewsToWxsService;
import com.kingtechfin.wxthirdparty.service.WxUploadNewsWxsListService;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import com.kingtechfin.wxthirdparty.util.MyUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialArticleUpdate;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-07-14
 */
@RestController
@RequestMapping("/wx_news_wxs")
public class WxUploadNewsToWxsController {
    @Value("${wechat.media.upload-path}")
    private String uploadPath;

    @Autowired
    private WxThirdServiceImpl wxThirdService;

    @Autowired
    private WxUploadNewsWxsListService wxUploadNewsWxsListService;

    @Autowired
    private WxUploadNewsToWxsService wxUploadNewsToWxsService;

    // 发布图文素材到微信公众号服务器
    @PostMapping("/wx_upload_news_to_wxs")
    @Transactional
    public ServerResult uploadNewsToWxsById(@RequestBody Map<String, Object> reqMap) {
        String orgId = String.valueOf(reqMap.get("orgId"));
        String idsStr = (String) reqMap.get("ids");
        String[] split = idsStr.split(",");

        List<String> strIds = Arrays.asList(split);

        if (orgId == null || strIds == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        if (strIds.size() > 8 || strIds.size() == 0) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_PARAM_ERROR);
        }

        List<Long> ids = new ArrayList<>();
        for (String strId : strIds) {
            ids.add(Long.valueOf(strId));
        }

        ServerResult ret = checkAuth(Long.valueOf(orgId));

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        // 获取授权公众号媒体服务对象
        WxMpMaterialService wxMpMaterialService = wxThirdService
                .getWxOpenComponentService()
                .getWxMpServiceByAppid(authAppid).getMaterialService();
        if (wxMpMaterialService == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        WxUploadNews wxUploadNews = new WxUploadNews();
        wxUploadNews.setAuthorizerAppid(authAppid);
        List<WxUploadNews> wxUploadNewsList = wxUploadNews.selectList(new EntityWrapper(wxUploadNews).in("id", ids));
        // 这里已经获取到所有本地单个素材，需要组长素材发送到微信服务器
        return uploadNewsToWxs(authAppid, wxMpMaterialService, wxUploadNewsList, ids);
    }

    // 从本地服务器返回已发布到微信公众号服务器的图文素材
    @PostMapping("/wx_get_news_list_wxs")
    public ServerResult getNewsWxsList(@RequestBody WxUploadNewsToWxs wxUploadNewsToWxs) {
        Long orgId = wxUploadNewsToWxs.getOrgId();
        ServerResult ret = checkAuth(orgId);

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        wxUploadNewsToWxs.setAuthorizerAppid(authAppid);
        return new ServerResult(wxUploadNewsToWxsService.getUploadNewsList(wxUploadNewsToWxs));
    }

    // 从本地服务器返回已发布到微信公众号服务器的图文素材
    @PostMapping("/wx_update_news_list_wxs")
    public ServerResult updateNewsWxsList(@RequestBody WxUploadNewsWxsList wxUploadNewsWxsList) {
        Long orgId = wxUploadNewsWxsList.getOrgId();
        ServerResult ret = checkAuth(orgId);

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();

        // 获取授权公众号媒体服务对象
        WxMpMaterialService wxMpMaterialService = wxThirdService
                .getWxOpenComponentService()
                .getWxMpServiceByAppid(authAppid).getMaterialService();
        if (wxMpMaterialService == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        try {
            // 先检查封面图片是否存在
            WxUploadMedia wxUploadMedia = checkWxsImage(wxMpMaterialService, wxUploadNewsWxsList.getThumbName());
            if (wxUploadMedia == null) {
                return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA);
            }

            WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate = new WxMpMaterialArticleUpdate();
            wxMpMaterialArticleUpdate.setIndex(wxUploadNewsWxsList.getNewsIndex());
            wxMpMaterialArticleUpdate.setMediaId(wxUploadNewsWxsList.getMediaId());

            WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();
            article.setAuthor(wxUploadNewsWxsList.getAuthor());
            article.setThumbMediaId(wxUploadMedia.getMediaId());
            article.setContent(wxUploadNewsWxsList.getContent());
            article.setDigest(wxUploadNewsWxsList.getDigest());
            article.setTitle(wxUploadNewsWxsList.getTitle());
            article.setShowCoverPic(true);

            wxMpMaterialArticleUpdate.setArticles(article);

            wxMpMaterialService.materialNewsUpdate(wxMpMaterialArticleUpdate);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return new ServerResult(wxUploadNewsWxsList.updateById());
    }

    // 从本地服务器返回已发布到微信公众号服务器的图文素材
    @PostMapping("/wx_get_news_one_wxs")
    public ServerResult getNewsListWxs(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        Long id = reqMap.get("id");

        if (orgId == null && id == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(orgId);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        WxUploadNewsWxsList wxUploadNewsWxsList = new WxUploadNewsWxsList();
        wxUploadNewsWxsList.setId(id);
        return new ServerResult(wxUploadNewsWxsList.selectById());
    }

    // 删除已发布到微信公众号服务器的图文素材
    @PostMapping("/wx_del_news_wxs")
    @Transactional
    public ServerResult delNewsWxs(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        Long id = reqMap.get("id");

        if (orgId == null && id == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(orgId);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        WxUploadNewsToWxs wxUploadNewsToWxs = new WxUploadNewsToWxs();
        wxUploadNewsToWxs.setAuthorizerAppid(authAppid);
        wxUploadNewsToWxs.setId(id);
        wxUploadNewsToWxs = wxUploadNewsToWxs.selectById();
        if (wxUploadNewsToWxs == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA);
        }
        // 获取授权公众号媒体服务对象
        WxMpMaterialService wxMpMaterialService = wxThirdService
                .getWxOpenComponentService()
                .getWxMpServiceByAppid(authAppid).getMaterialService();
        if (wxMpMaterialService == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_AUTH);
        }

        try {
            wxMpMaterialService.materialDelete(wxUploadNewsToWxs.getMediaId());
        } catch (WxErrorException e) {
            e.printStackTrace();
            new ServerResult(ServerStatusEnum.SERVICE_WX_SERVER_ERROR, e.getMessage());
        }

        wxUploadNewsToWxs.deleteById();
        WxUploadNewsWxsList wxUploadNewsWxsList = new WxUploadNewsWxsList();
        wxUploadNewsWxsList.setMediaId(wxUploadNewsToWxs.getMediaId());
        wxUploadNewsWxsList.delete(new EntityWrapper(wxUploadNewsWxsList));

        return new ServerResult(true);
    }

    /**
     * 私有部分
     */
    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }

    private ServerResult uploadNewsToWxs(String authAppid, WxMpMaterialService wxMpMaterialService, List<WxUploadNews> wxUploadNewsList, List<Long> indexs) {
        WxMpMaterialUploadResult wxMpMaterialUploadResult = null;
        WxUploadNewsToWxs wxUploadNewsToWxs = null;
        try {
            WxMpMaterialNews wxMpMaterialNews = new WxMpMaterialNews();
            WxMpMaterialNews.WxMpMaterialNewsArticle[] articles = new WxMpMaterialNews.WxMpMaterialNewsArticle[indexs.size()];
            WxUploadNewsWxsList[] wxUploadNewsWxsLists = new WxUploadNewsWxsList[indexs.size()];

            for (WxUploadNews wxUploadNews : wxUploadNewsList) {
                // 先检查封面图片是否存在
                WxUploadMedia wxUploadMedia = checkWxsImage(wxMpMaterialService, wxUploadNews.getThumbName());
                if (wxUploadMedia == null) {
                    return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA);
                }

                //组装图文消息列表
                WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();
                WxUploadNewsWxsList wxUploadNewsWxsList = new WxUploadNewsWxsList(wxUploadNews);
                wxUploadNewsWxsList.setCreateTime(MyUtil.get10CurrentTime());
                article.setAuthor(wxUploadNews.getAuthor());
                article.setThumbMediaId(wxUploadMedia.getMediaId());
                article.setContent(wxUploadNews.getContent());
                article.setDigest(wxUploadNews.getDigest());
                article.setTitle(wxUploadNews.getTitle());
                article.setShowCoverPic(true);
                Integer index = indexs.indexOf(wxUploadNews.getId());
                articles[index] = article;

                wxUploadNewsWxsList.setNewsIndex(index);
                wxUploadNewsWxsLists[index] = wxUploadNewsWxsList;
            }

            List<WxMpMaterialNews.WxMpMaterialNewsArticle> articleList = Arrays.asList(articles);
            wxMpMaterialNews.setArticles(articleList);
            wxMpMaterialUploadResult = wxMpMaterialService.materialNewsUpload(wxMpMaterialNews);
            wxUploadNewsToWxs = new WxUploadNewsToWxs();
            wxUploadNewsToWxs.setAuthorizerAppid(authAppid);
            wxUploadNewsToWxs.setMediaId(wxMpMaterialUploadResult.getMediaId());
            wxUploadNewsToWxs.setCreateTime(MyUtil.get10CurrentTime());
            wxUploadNewsToWxs.insert();
            for (WxUploadNewsWxsList item : wxUploadNewsWxsLists) {
                item.setMediaId(wxMpMaterialUploadResult.getMediaId());
            }
            wxUploadNewsWxsListService.insertBatch(Arrays.asList(wxUploadNewsWxsLists));

        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ServerResult(ServerStatusEnum.SERVICE_WX_SERVER_ERROR, e.getMessage());
        }

        return new ServerResult(wxUploadNewsToWxs.getId().toString());
    }

    private WxUploadMedia checkWxsImage(WxMpMaterialService wxMpMaterialService, String imgPath) throws WxErrorException {
        WxUploadMedia wxUploadMedia = new WxUploadMedia();
        wxUploadMedia.setServiceName(imgPath);
        wxUploadMedia = wxUploadMedia.selectOne(new EntityWrapper(wxUploadMedia));
        if (wxUploadMedia == null) {
            return null;
        }

        // 如果媒体图片没有上传微信，先上传微信，获取mediaId
        if (wxUploadMedia.getMediaId() == null) {
            WxMpMaterialUploadResult wxMpMaterialUploadResult = null;

            String strUploadThumbPath = uploadPath + wxUploadMedia.getServiceName();
            WxMpMaterial wxMpMaterial = new WxMpMaterial(null, new File(strUploadThumbPath), null, null);
            try {
                wxMpMaterialUploadResult = wxMpMaterialService.materialFileUpload("thumb", wxMpMaterial);
            } catch (WxErrorException e) {
                throw e;
            }
            wxUploadMedia.setMediaId(wxMpMaterialUploadResult.getMediaId());
            wxUploadMedia.setWxUrl(wxMpMaterialUploadResult.getUrl());
            // 更新媒体素材
            wxUploadMedia.updateById();
        }
        return wxUploadMedia;
    }

}

