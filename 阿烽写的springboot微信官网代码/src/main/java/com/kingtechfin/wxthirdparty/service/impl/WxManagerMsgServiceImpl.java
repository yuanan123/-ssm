package com.kingtechfin.wxthirdparty.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kingtechfin.wxthirdparty.Result.ExceptionResult;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxManagerMsg;
import com.kingtechfin.wxthirdparty.entity.WxManagerMsgRet;
import com.kingtechfin.wxthirdparty.entity.WxVipcnAuthInfo;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.mapper.WxManagerMsgMapper;
import com.kingtechfin.wxthirdparty.service.WxManagerMsgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-10
 */
@Service
public class WxManagerMsgServiceImpl extends ServiceImpl<WxManagerMsgMapper, WxManagerMsg> implements WxManagerMsgService{

    @Autowired
    private WxThirdServiceImpl wxThirdService;

    @Autowired
    private WxMpService wxService;

    @Transactional
    @Override
    public void responseMsg(WxManagerMsg wxManagerMsg) throws ExceptionResult {
        WxManagerMsg wxSelectRet = wxManagerMsg.selectById();
        if (wxSelectRet == null) {
            throw new ExceptionResult(new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA));
        }
        wxSelectRet.setReplied(1);
        boolean bRet = wxSelectRet.updateById();
        if (!bRet) {
            throw new ExceptionResult(new ServerResult(ServerStatusEnum.SERVICE_WX_NO_DATA));
        }

        WxManagerMsg wxResponseMsg = new WxManagerMsg();

        wxResponseMsg.setMsgTransmitType("s");
        wxResponseMsg.setContent(wxManagerMsg.getContent());
        wxResponseMsg.setsMsgId(wxSelectRet.getMsgId());
        wxResponseMsg.setAuthorizerAppid(wxSelectRet.getAuthorizerAppid());
        wxResponseMsg.setFromUser(wxSelectRet.getToUser());
        wxResponseMsg.setToUser(wxSelectRet.getFromUser());
        wxResponseMsg.setCreateTime(System.currentTimeMillis()/1000);
        wxResponseMsg.setMsgType("text");
        wxResponseMsg.insert();

        WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(wxResponseMsg.getContent()).toUser(wxResponseMsg.getToUser()).build();
        try {
            wxThirdService.getWxOpenComponentService().getWxMpServiceByAppid(wxResponseMsg.getAuthorizerAppid()).getKefuService().sendKefuMessage(kefuMessage);
            // 微信公众号调试接口
            //wxService.getKefuService().sendKefuMessage(kefuMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ExceptionResult(new ServerResult(ServerStatusEnum.SERVICE_FAILED, e.getMessage()));
        }
    }

    @Transactional
    @Override
    public WxManagerMsgRet getMsgList(WxManagerMsg wxManagerMsg) {
        WxManagerMsgRet wxManagerMsgRet = new WxManagerMsgRet();
        wxManagerMsgRet.setWxMsgInfo(this.baseMapper.selectMsgListPage(wxManagerMsg));
        WxVipcnAuthInfo wxVipcnAuthInfo = new WxVipcnAuthInfo();
        wxVipcnAuthInfo.setAuthorizerAppid(wxManagerMsg.getAuthorizerAppid());
        wxVipcnAuthInfo = wxVipcnAuthInfo.selectOne(new EntityWrapper(wxVipcnAuthInfo));
        wxManagerMsgRet.setWxVipcnAuthInfo(wxVipcnAuthInfo);
        return wxManagerMsgRet;
    }
}
