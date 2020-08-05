package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.Package2Policy;

public interface Package2PolicyMapper extends BaseMapper<Package2Policy>{
	public List<PageData> findByPackageId(String id);
	void deleteByPackageId(String packageId);
	Package2Policy findByIdAndPackageId(Package2Policy package2Policy);
}
