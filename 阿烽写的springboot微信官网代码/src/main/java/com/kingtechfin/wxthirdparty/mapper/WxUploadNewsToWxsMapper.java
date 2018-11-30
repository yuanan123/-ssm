package com.kingtechfin.wxthirdparty.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kingtechfin.wxthirdparty.entity.WxUploadNewsToWxs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MrChao
 * @since 2018-07-14
 */
public interface WxUploadNewsToWxsMapper extends BaseMapper<WxUploadNewsToWxs> {
    public List<WxUploadNewsToWxs> getUploadNewsList(@Param("news") WxUploadNewsToWxs wxUploadNewsToWxs);
}
