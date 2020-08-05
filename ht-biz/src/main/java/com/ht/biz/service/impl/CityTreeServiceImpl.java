package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CityTreeMapper;
import com.ht.biz.service.CityTreeService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.Citytree;
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
public class CityTreeServiceImpl extends ServiceImpl<CityTreeMapper, Citytree> implements CityTreeService {

 
	@Override
	public List<Citytree> findlistPage() throws Exception {
		return baseMapper.findlistPage( );
	}

	@Override
	public void deleteCityById(String  id) throws Exception {
		baseMapper.deleteById( id );

	}

	@Override
	public boolean ischildnode(String id) throws Exception {
		int num = baseMapper.childnodecount(id);
		if(num>0){
			return false;
		}
		return true;
	}

	@Override
	public Citytree selectbyId(String id)  {
		return baseMapper.selectbyId( id );
	}

	@Override
	public void updateOne(PageData pd) {
		 baseMapper.updateOne( pd);
	}


}
