package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.Package2CI;

public interface Package2CIMapper extends BaseMapper<Package2CI>{
	public List<PageData> findByPackageId(String id);
	void deleteByPackageId(String packageId);
	Package2CI findByIdAndPackageId(Package2CI package2CI);
}
