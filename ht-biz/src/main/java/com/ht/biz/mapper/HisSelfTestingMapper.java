package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.freeassess.HisSelfTesting;

public interface HisSelfTestingMapper extends BaseMapper<HisSelfTesting>{
	List<HisSelfTesting> findList(MyPage<HisSelfTesting> page);
}
