package com.kingtechfin.wxthirdparty.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.entity.WxAutoResponse;
import com.kingtechfin.wxthirdparty.mapper.WxAutoResponseMapper;
import com.kingtechfin.wxthirdparty.service.WxAutoResponseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-04
 */
@Service
public class WxAutoResponseServiceImpl extends ServiceImpl<WxAutoResponseMapper, WxAutoResponse> implements WxAutoResponseService {
    @Override
    public WxAutoResponse getByMsgType(String authAppid, String msgType) {
        return selectOne(
                new EntityWrapper<WxAutoResponse>()
                        .eq("authorizerAppid", authAppid)
                        .eq("msgType", msgType));
    }

    @Override
    public WxAutoResponse getByKey(String authAppid, String msgKey) {
        return selectOne(
                new EntityWrapper<WxAutoResponse>()
                        .eq("authorizerAppid", authAppid)
                        .eq("msgKey", msgKey)
                        .notIn("msgType", "auto", "subscribe"));
    }

    @Override
    public List<WxAutoResponse> getByKeyList(String authAppid) {
        return selectList(
                new EntityWrapper<WxAutoResponse>()
                        .eq("authorizerAppid", authAppid)
                        .notIn("msgType", "auto", "subscribe"));
    }

    @Override
    public List<WxAutoResponse> getByKeyLikeList(String authAppid, String msgKey) {
        return selectList(
                new EntityWrapper<WxAutoResponse>()
                        .eq("authorizerAppid", authAppid)
                        .like("msgKey", msgKey)
                        .notIn("msgType", "auto", "subscribe"));
    }

    @Override
    public List<WxAutoResponse> getByTypeOrKeyList(String authAppid, String filterStr) {
        return selectList(
                new EntityWrapper<WxAutoResponse>()
                        .eq("authorizerAppid", authAppid)
                        .like("msgType", filterStr)
                        .or()
                        .like("msgKey", filterStr)
                        .notIn("msgType", "auto", "subscribe"));
    }
}
