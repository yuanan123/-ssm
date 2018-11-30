package com.kingtechfin.wxthirdparty.service;

import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxAutoResponse;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-04
 */
public interface WxAutoResponseService extends IService<WxAutoResponse> {
    public WxAutoResponse getByMsgType(String authAppid, String msgType);
    public WxAutoResponse getByKey(String authAppid, String msgKey);
    public List<WxAutoResponse> getByKeyList(String authAppid);
    public List<WxAutoResponse> getByKeyLikeList(String authAppid, String msgKey);
    public List<WxAutoResponse> getByTypeOrKeyList(String authAppid, String filterStr);
}
