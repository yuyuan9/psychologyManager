package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.Package2Policy;

public interface Package2PolicyService extends IService<Package2Policy>{
	public List<PageData> findByPackageId(String id);
	void insert(Package2Policy package2Policy);
	void deleted(String id);
	void deleteByPackageId(String packageId);
	Package2Policy findByIdAndPackageId(Package2Policy package2Policy);
}
