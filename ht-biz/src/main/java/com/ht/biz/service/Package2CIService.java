package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.Package2CI;

public interface Package2CIService extends IService<Package2CI>{
	public List<PageData> findByPackageId(String id);
	void insert(Package2CI package2CI);
	void deleted(String id);
	void deleteByPackageId(String packageId);
	Package2CI findByIdAndPackageId(Package2CI package2CI);
	public Object getshortUrl(String baseUrl);
}
