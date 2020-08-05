package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CityTreeMapper;
import com.ht.biz.mapper.PushMapper;
import com.ht.biz.service.CityTreeService;
import com.ht.biz.service.WxPushService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.Citytree;
import com.ht.entity.biz.wx.Push;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jied
 */
@Service
public class WxpushServiceImpl extends ServiceImpl<PushMapper, Push> implements WxPushService {


}
