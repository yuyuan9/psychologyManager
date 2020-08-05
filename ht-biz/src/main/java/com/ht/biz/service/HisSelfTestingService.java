package com.ht.biz.service;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.freeassess.HisSelfTesting;

public interface HisSelfTestingService extends IService<HisSelfTesting>{
	List<HisSelfTesting> findList(MyPage<HisSelfTesting> page);
	public void exportpdfs(Collection<Map<String, String>> forms,OutputStream outputStream,String dist,int size);
	public String getHisSelfTesting(Map<String,Object> table1,HisSelfTesting his);
}
