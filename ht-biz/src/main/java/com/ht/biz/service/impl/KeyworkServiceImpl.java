package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.KeyworkMapper;

import com.ht.biz.service.KeyworkService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.keywork.Keywork;
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
public class KeyworkServiceImpl extends ServiceImpl<KeyworkMapper, Keywork> implements KeyworkService {


	@Override
	public MyPage<PageData> syslistall(MyPage page) {
		return baseMapper.syslistall(page);
	}

	@Override
	public List<Keywork> findList() {
		return baseMapper.findList();
	}


	@Override
	public PageData getfindbyId(String id) {
		return baseMapper.getfindbyId(id);
	}
}
