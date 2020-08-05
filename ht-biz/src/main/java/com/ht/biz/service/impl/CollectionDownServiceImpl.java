package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CollectionDownMapper;
import com.ht.biz.service.CollectionDownService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.collection.CollectionDown;
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
public class CollectionDownServiceImpl extends ServiceImpl<CollectionDownMapper, CollectionDown> implements CollectionDownService {

    @Override
    public CollectionDown getColletionByCreateIdAndTagerId(String userId, String targetId,int type) {
        QueryWrapper<CollectionDown> ew = new QueryWrapper<CollectionDown>();
        ew.eq( "createid",userId ).eq( "targetId",targetId ).eq( "type",type );
        return baseMapper.selectOne(ew);
    }

    @Override
    public List<PageData> findcolletionbyUserId(MyPage page) {
        return baseMapper.findcolletionbyUserId(page);
    }

    @Override
    public List<PageData> findMydownByUserId(MyPage page) {
        return null;
    }
}
