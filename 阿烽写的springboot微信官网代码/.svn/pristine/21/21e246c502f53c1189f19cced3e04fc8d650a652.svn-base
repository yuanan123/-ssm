package com.kingtechfin.wxthirdparty.service.impl;

import com.kingtechfin.wxthirdparty.entity.WxHistoryNews;
import com.kingtechfin.wxthirdparty.mapper.WxHistoryNewsMapper;
import com.kingtechfin.wxthirdparty.service.WxHistoryNewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MrChao
 * @since 2018-07-16
 */
@Service
public class WxHistoryNewsServiceImpl extends ServiceImpl<WxHistoryNewsMapper, WxHistoryNews> implements WxHistoryNewsService {
    public List<WxHistoryNews> getHisNewsList(WxHistoryNews wxHistoryNews) {
        return this.baseMapper.getHisNewsList(wxHistoryNews);
    }
}
