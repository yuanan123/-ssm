package com.kingtechfin.wxthirdparty.service;

import com.baomidou.mybatisplus.service.IService;
import com.kingtechfin.wxthirdparty.entity.WxUploadNewsToWxs;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-14
 */
public interface WxUploadNewsToWxsService extends IService<WxUploadNewsToWxs> {
    public List<WxUploadNewsToWxs> getUploadNewsList(WxUploadNewsToWxs wxUploadNewsToWxs);
}
