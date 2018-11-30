package com.kingtechfin.wxthirdparty.service.impl;

import com.kingtechfin.wxthirdparty.entity.WxUploadNewsToWxs;
import com.kingtechfin.wxthirdparty.mapper.WxUploadNewsToWxsMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kingtechfin.wxthirdparty.service.WxUploadNewsToWxsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-14
 */
@Service
public class WxUploadNewsToWxsServiceImpl extends ServiceImpl<WxUploadNewsToWxsMapper, WxUploadNewsToWxs> implements WxUploadNewsToWxsService {
    public List<WxUploadNewsToWxs> getUploadNewsList(WxUploadNewsToWxs wxUploadNewsToWxs) {
        return this.baseMapper.getUploadNewsList(wxUploadNewsToWxs);
    }
}
