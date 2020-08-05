package com.ht.biz.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.PolicyMatchMapper;
import com.ht.biz.service.PolicyMatchService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.policymatch.PolicyMatch;
@Service
public class PolicyMatchServiceImpl extends ServiceImpl<PolicyMatchMapper, PolicyMatch> implements PolicyMatchService{

	private static final String speProvince="北京市天津市上海市重庆市";
	private static final String or=" or ";
	@Override
	public List<PolicyMatch> findlist(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findlist(page);
	}

	@Override
	public PolicyMatch findById(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.findById(pd);
	}
	
	public String getSql(String province,String city,String area){
		String p="(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.city,'')='' and ifnull(t.area,'')='')";
		String pc="(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.city like CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.area,'')='')";
		String pca="(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.city like CONCAT(CONCAT('%s', '%s'),'%s') and t.area like CONCAT(CONCAT('%s', '%s'),'%s'))";
		String c="(t.city LIKE CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.area,'')='')";
		String ca="(t.city LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.area like CONCAT(CONCAT('%s', '%s'),'%s'))";
		StringBuffer subsql=new StringBuffer();
		//subsql.append("(");
		//字符串中包含有%,String.format会报异常
		if(!StringUtils.isBlank(province)){
			subsql.append("select * from t_policy_match t where 1=1 and t.province LIKE CONCAT(CONCAT('%', '国家'),'%') or ");
		}
		if(!StringUtils.isBlank(province)&&!StringUtils.equals(province, "国家")){
			if(speProvince.contains(province)){
				subsql.append(String.format(c,"%",province,"%")).append(or);
			}else{
				subsql.append(String.format(p,"%",province,"%")).append(or);
			}
		}
		if(!StringUtils.isBlank(province) && !StringUtils.isBlank(city)){
			if(speProvince.contains(province)){
				subsql.append(String.format(ca,"%",province,"%","%",city,"%")).append(or);
			}else{
				subsql.append(String.format(pc,"%",province,"%","%",city,"%")).append(or);
			}
		}
		if(!StringUtils.isBlank(province) && !StringUtils.isBlank(city) && !StringUtils.isBlank(area)){
			subsql.append(String.format(pca,"%",province,"%","%",city,"%","%",area,"%")).append(or);
		}
		//subsql.append(")tt");
		if(StringUtils.isNotBlank(subsql.toString())){
			return "("+StringUtils.removeEnd(subsql.toString(), or)+")tt";
		}else{
			return "t_policy_match";
		}
		
	}
	public static void main(String[] args) {
		String p="(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.city,'')='' and ifnull(t.area,'')='')";
		System.out.println(String.format(p,"%" ,"公司的","%"));
	}
}
