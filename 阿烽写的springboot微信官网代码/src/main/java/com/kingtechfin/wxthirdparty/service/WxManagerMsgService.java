package com.kingtechfin.wxthirdparty.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.kingtechfin.wxthirdparty.Result.ExceptionResult;
import com.kingtechfin.wxthirdparty.entity.WxManagerMsg;
import com.baomidou.mybatisplus.service.IService;
import com.kingtechfin.wxthirdparty.entity.WxManagerMsgRet;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-10
 */
public interface WxManagerMsgService extends IService<WxManagerMsg> {
    public void responseMsg(WxManagerMsg wxManagerMsg)  throws ExceptionResult;
    public WxManagerMsgRet getMsgList(WxManagerMsg wxManagerMsg);
}
