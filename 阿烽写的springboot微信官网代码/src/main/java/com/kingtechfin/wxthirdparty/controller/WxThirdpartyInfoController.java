package com.kingtechfin.wxthirdparty.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.entity.WxThirdpartyInfo;
import com.kingtechfin.wxthirdparty.service.WxThirdpartyInfoService;
import com.kingtechfin.wxthirdparty.thirdman.WxThirdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MrChao
 * @since 2018-06-23
 */
@RestController
@RequestMapping("/wx_thirdparty_info")
public class WxThirdpartyInfoController {
    @Autowired
    private WxThirdServiceImpl wxOpenService;

    @PostMapping("/wx_add_config")
    public ServerResult wxAddConfig(@RequestBody WxThirdpartyInfo wxThirdpartyInfo) {
        boolean ret = wxThirdpartyInfo.insert();
        if (ret) {
            wxOpenService.init();
        }
        return new ServerResult(ret);
    }

    @PostMapping("/wx_update_config")
    public ServerResult wxUpdateConfig(@RequestBody WxThirdpartyInfo wxThirdpartyInfo) {
        boolean ret = wxThirdpartyInfo.updateById();
        if (ret) {
            wxOpenService.init();
        }
        return new ServerResult(ret);
    }

    @PostMapping("/wx_get_config")
    public ServerResult wxGetConfig() {
        return new ServerResult(new WxThirdpartyInfo().selectOne(new EntityWrapper()));
    }

    @PostMapping("/wx_del_config")
    public ServerResult wxDelConfig() {
        boolean ret = new WxThirdpartyInfo().delete(new EntityWrapper());
        if (ret) {
            wxOpenService.init();
        }
        return new ServerResult(ret);
    }

}

