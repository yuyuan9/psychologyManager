package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.Package2PolicyMapper;
import com.ht.biz.service.Package2PolicyService;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.Package2Policy;
@Service
public class Package2PolicyServiceImpl extends ServiceImpl<Package2PolicyMapper, Package2Policy> implements Package2PolicyService {

	@Override
	public List<PageData> findByPackageId(String id) {
		// TODO Auto-generated method stub
		return baseMapper.findByPackageId(id);
	}

	@Override
	public void insert(Package2Policy package2Policy) {
		// TODO Auto-generated method stub
		baseMapper.insert(package2Policy);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

	@Override
	public void deleteByPackageId(String packageId) {
		// TODO Auto-generated method stub
		baseMapper.deleteByPackageId(packageId);
	}

	@Override
	public Package2Policy findByIdAndPackageId(Package2Policy package2Policy) {
		// TODO Auto-generated method stub
		return baseMapper.findByIdAndPackageId(package2Policy);
	}

}
