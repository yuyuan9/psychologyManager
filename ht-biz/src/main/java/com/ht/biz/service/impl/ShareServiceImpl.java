package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CityTreeMapper;
import com.ht.biz.mapper.ShareMapper;
import com.ht.biz.service.CityTreeService;
import com.ht.biz.service.ShareService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.Citytree;
import com.ht.entity.biz.sys.Share;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jied
 */
@Service("shareService")
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {


}
