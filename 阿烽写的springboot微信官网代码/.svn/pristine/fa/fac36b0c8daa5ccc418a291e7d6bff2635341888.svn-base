package com.kingtechfin.wxthirdparty.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.controller.common.CheckAuth;
import com.kingtechfin.wxthirdparty.entity.WxUploadMedia;
import com.kingtechfin.wxthirdparty.entity.WxUploadNewsToWxs;
import com.kingtechfin.wxthirdparty.entity.WxUploadNewsWxsList;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import com.kingtechfin.wxthirdparty.util.FileUtil;
import com.kingtechfin.wxthirdparty.util.MyUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-07-12
 */
@RestController
@RequestMapping("/wx_media")
public class WxUploadMediaController {

    @Value("${wechat.media.upload-path}")
    private String uploadPath;

    @Value("${wechat.media.front-display-path}")
    private String frontDisplayPath;

    @Autowired
    private WxThirdServiceImpl wxThirdService;



    //处理文件上传
    @PostMapping("/wx_media_upload")
    public ServerResult uploadMedia(@RequestParam("file") MultipartFile file,
                                  @RequestParam("mediaType") String mediaType,
                                  @RequestParam("orgId") Long orgId,
                                  HttpServletRequest request) throws Exception {
        if (file == null || mediaType == null || orgId == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }
        // 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
        String[] fileTypes = {"image","voice","video","thumb"};
        if (!StringUtils.equalsAnyIgnoreCase(mediaType, fileTypes)) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_PARAM_ERROR);
        }

        ServerResult ret = checkAuth(orgId);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        String fileOldName = file.getOriginalFilename();
        String fileNewName = IdWorker.getIdStr() + fileOldName.substring(fileOldName.lastIndexOf("."));
        String savePath = uploadPath + frontDisplayPath;
        String frontDisplayName = request.getContextPath() + frontDisplayPath + fileNewName;

        WxUploadMedia wxUploadMedia = null;
        try {
            FileUtil.uploadFile(file.getBytes(), savePath, fileNewName);
            wxUploadMedia = new WxUploadMedia();
            wxUploadMedia.setAuthorizerAppid(authAppid);
            wxUploadMedia.setCreateTime(MyUtil.get10CurrentTime());
            wxUploadMedia.setLocalName(fileOldName);
            wxUploadMedia.setServiceName(frontDisplayName);
            wxUploadMedia.setMediaType(mediaType);
            wxUploadMedia.insert();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return new ServerResult(wxUploadMedia);
    }

    // 获取图片素材列表
    @PostMapping("/wx_get_media_list")
    public ServerResult getMediaList(@RequestBody Map<String, Object> reqMap) {
        String orgId = (String) reqMap.get("orgId");
        String mediaType = (String) reqMap.get("mediaType");

        if (orgId == null || mediaType == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(Long.valueOf(orgId));

        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String[] mediaTypes = mediaType.split("\\|");
        String authAppid = (String) ret.getResult();
        WxUploadMedia wxUploadMedia = new WxUploadMedia();
        wxUploadMedia.setAuthorizerAppid(authAppid);
        return new ServerResult(wxUploadMedia.selectList(new EntityWrapper(wxUploadMedia).in("mediaType", mediaTypes)));
    }

    // 获取图片素材列表
    @PostMapping("/wx_get_media_by_id")
    public ServerResult getMediaById(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        Long id = reqMap.get("id");
        if (orgId == null || id == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(orgId);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        WxUploadMedia wxUploadMedia = new WxUploadMedia();
        wxUploadMedia.setAuthorizerAppid(authAppid);
        wxUploadMedia.setId(id);
        return new ServerResult(wxUploadMedia.selectOne(new EntityWrapper(wxUploadMedia)));
    }

    // 删除媒体素材
    @PostMapping("/wx_del_media")
    public ServerResult delMedia(@RequestBody Map<String, Long> reqMap) {
        Long orgId = reqMap.get("orgId");
        Long id = reqMap.get("id");
        if (orgId == null || id == null) {
            return new ServerResult(ServerStatusEnum.SERVICE_PARAM_NULL);
        }

        ServerResult ret = checkAuth(orgId);
        if (ret.getStatus() != ServerStatusEnum.SERVICE_OK.getStatus()) {
            return ret;
        }

        String authAppid = (String) ret.getResult();
        WxUploadMedia wxUploadMedia = new WxUploadMedia();
        wxUploadMedia.setAuthorizerAppid(authAppid);
        wxUploadMedia.setId(id);
        wxUploadMedia = wxUploadMedia.selectById();

        //删除之前校验新闻否有该图片
        WxUploadNewsWxsList wxUploadNewsWxsList = new WxUploadNewsWxsList();
        wxUploadNewsWxsList.setThumbName(wxUploadMedia.getServiceName());
        List<WxUploadNewsWxsList> wxUploadNewsWxsLists = wxUploadNewsWxsList.selectList(new EntityWrapper(wxUploadNewsWxsList));
        if (wxUploadNewsWxsLists.size() > 0) {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NEWS_HAVE_MEDIA);
        }

        if (wxUploadMedia != null) {
            // 删除本地文件
            String filePath = uploadPath + wxUploadMedia.getServiceName();
            FileUtil.deleteFile(filePath);
            // 删除微信服务器上文件
            if (wxUploadMedia.getMediaId() != null) {
                WxMpMaterialService wxMpMaterialService = wxThirdService
                        .getWxOpenComponentService()
                        .getWxMpServiceByAppid(wxUploadMedia.getAuthorizerAppid()).getMaterialService();
                try {
                    wxMpMaterialService.materialDelete(wxUploadMedia.getMediaId());
                } catch (WxErrorException e) {
                    e.printStackTrace();
                    // 微信上这个mediaId不存在，直接忽略
                }
            }
        } else {
            return new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA);
        }
        return new ServerResult(wxUploadMedia.deleteById());
    }

    private ServerResult checkAuth(Long orgId) {
        CheckAuth checkAuth = new CheckAuth();
        return checkAuth.getAuthAppid(orgId);
    }

}

