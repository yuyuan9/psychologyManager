package com.ht.biz.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.ProjectlibMapper;
import com.ht.biz.service.ProjectlibService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.projectlib.Projectlib;
@Service("projectlibService")
public class ProjectlibServiceImpl extends ServiceImpl<ProjectlibMapper, Projectlib> implements ProjectlibService{

	private static final String speProvince="北京市天津市上海市重庆市";
	private static final String keyword1="(companyName:*%s* OR name:*%s* OR type:*%s* OR special:*%s*)";//电脑端搜索
	private static final String keyword2="(companyName:*%s* OR name:*%s*)";//小程序搜索
	private static final String keyword3="(companyName:*%s* OR name:*%s*)";//手机端搜索
	
	private static final String and=" AND ";
	private static final String or=" OR ";
	
	//pc端
		public String getPcQueryStr(String keyword,String province,String city,String area,String year1,String year2,String regions,String countrys,String provinces,String citys,String areas){
			StringBuffer query = new StringBuffer();
			query.append(keyword(keyword,1));
			query.append(years(year1,year2));
			if(StringUtils.equals(regions, "1")){
				query.append(pca(province,city,area));
			}else{
				query.append(regions(province,city,area,countrys,provinces,citys,areas));
			}
			return StringUtils.removeEnd(query.toString(), and);
		}
	
	/*
	 * (companyName:keyword OR name:keyword) AND
	 * (year:*year*) AND
	 * (province:*国家* AND city:*city* AND area:*area*) AND 
	 * (province:*province* AND city:*city* AND area:*area*)  OR 
	 * (province:*province* AND city:*city* AND area:*area*)  OR
	 * (province:*province* AND city:*city* AND area:*area*)  
	 */
	//手机端
	public String getAppQueryStr(String keyword,String province,String city,String area,String year){
		StringBuffer query = new StringBuffer();
		query.append(keyword(keyword,3));
		query.append(year(year));
		query.append(pca(province,city,area));
		return StringUtils.removeEnd(query.toString(), and);
	}
	//小程序
		public String getSpQueryStr(String keyword, String province, String city, String area, String year) {
			// TODO Auto-generated method stub
			StringBuffer query = new StringBuffer();
			query.append(keyword(keyword,2));
			query.append(year(year));
			query.append(pca(province,city,area));
			return StringUtils.removeEnd(query.toString(), and);
		}
		//根据id获取
		public String getQueryById(String id){
			String string="id:"+id;
			return string;
		}
	private String keyword(String keyword,Integer i){
		if(!StringUtils.isBlank(keyword)){
			String fmt =null;
			if(i==1){
				fmt =keyword1;
			}else if(i==2){
				fmt =keyword2;
			}else{
				fmt =keyword3;
			}
			return String.format(fmt, keyword,keyword,keyword,keyword,keyword)+and;
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	private String years (String year1,String year2){
		if(!StringUtils.isBlank(year1)&&!StringUtils.isBlank(year2)){
			String fmt =" (yearProject:[%s TO %s]) ";
			return String.format(fmt, year1,year2)+and;
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	private String year (String year){
		if(!StringUtils.isBlank(year)){
		 String fmt =" (yearProject:*%s*) ";
		 return String.format(fmt, year)+and;
		}else{
			return StringUtils.EMPTY;
		}
	}
	private String afterpca(String province,String city,String area){
		StringBuffer retstr= new StringBuffer();
		if(StringUtils.isNotBlank(province)){
			if(speProvince.contains(province)){
				retstr.append(String.format("city:*%s* ", province)+and);
			}else{
				retstr.append(String.format("province:*%s* ", province)+and);
			}
		}
		if(StringUtils.isNotBlank(city)){
			if(speProvince.contains(province)){
				retstr.append(String.format("area:*%s* ", city)+and);
			}else{
				retstr.append(String.format("city:*%s* ", city)+and);
			}
		}
		if(StringUtils.isNotBlank(area)){
			retstr.append(String.format("area:*%s*", area)+and);
		}
		if(StringUtils.isNotBlank(retstr.toString())){
			return "("+StringUtils.remove(retstr.toString(), and)+")";
		}else{
			return StringUtils.EMPTY;
		}
	}
	private String userData(String userType,String companyid){
		if(!StringUtils.equals(userType, "ADMIN")){
			return "("+String.format("regionid:*%s*", companyid)+")";
		}else{
			return StringUtils.EMPTY;
		}
	}
	private String pca (String province,String city,String area){
	    String g  =" (province:*国家* AND city:\"\" AND area:\"\") ";
	    String p  =" (province:*%s* AND city:\"\" AND area:\"\") ";
		String pc =" (province:*%s* AND city:*%s* AND area:\"\") ";
		String pca=" (province:*%s* AND city:*%s* AND area:*%s*) ";
		String c=" (city:*%s* AND area:\"\") ";
		String ca=" (city:*%s* AND area:*%s*) ";
		StringBuffer retstr= new StringBuffer();
		if(!StringUtils.isBlank(province)){
			retstr.append(g).append(or);
		}
		if(!StringUtils.isBlank(province)&&!StringUtils.equals(province, "国家")){
			if(speProvince.contains(province)){
				retstr.append(String.format(c, province)).append(or);
			}else{
				retstr.append(String.format(p, province)).append(or);
			}
		}
		if(!StringUtils.isBlank(province) && !StringUtils.isBlank(city)){
			if(speProvince.contains(province)){
				retstr.append(String.format(ca, province,city)).append(or);
			}else{
				retstr.append(String.format(pc, province,city)).append(or);
			}
		}
		if(!StringUtils.isBlank(province) && !StringUtils.isBlank(city) && !StringUtils.isBlank(area)){
			retstr.append(String.format(pca, province,city,area)).append(or);
		}
		if(StringUtils.isNotBlank(retstr.toString())){
			return "("+StringUtils.removeEnd(retstr.toString(), or)+")"+and;
		}else{
			return "";
		}
	}
	
	private String regions (String province,String city,String area,String countrys,String provinces,String citys,String areas){
		String g  =" province:*国家* AND city:\"\" AND area:\"\" ";
	    String p  =" province:*%s* AND city:\"\" AND area:\"\" ";
		String pc =" province:*%s* AND city:*%s* AND area:\"\" ";
		String pca=" province:*%s* AND city:*%s* AND area:*%s* ";
		String c=" city:*%s* AND area:\"\" ";
		String ca=" city:*%s* AND area:*%s* ";
		StringBuffer retstr= new StringBuffer();
		if(StringUtils.isNotBlank(countrys)){
			retstr.append(g);
		}
		if(StringUtils.isNotBlank(provinces)){
			if(speProvince.contains(province)){
				retstr.append(String.format(c,province));
			}else{
				retstr.append(String.format(p,province));
			}
		}
		if(StringUtils.isNotBlank(citys)){
			if(speProvince.contains(province)){
				retstr.append(String.format(ca, province,city));
			}else{
				retstr.append(String.format(pc, province,city));
			}
		}
		if(StringUtils.isNotBlank(areas)){
			retstr.append(String.format(pca, province,city,area));
		}
		return "(("+retstr.toString()+"))";
	}
	
	@Override
	public Integer reset(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.reset(pd);
	}
	
	

	@Override
	public Integer dealReset(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.dealReset(pd);
	}
	

	@Override
	public String getPcAfterStr(String keyword, String province, String city, String area, String year,String userType,String companyid) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append(keyword(keyword,1));
		query.append(year(year));
		query.append(afterpca(province,city,area));
		query.append(userData(userType,companyid));
		return StringUtils.removeEnd(query.toString(), and);
	}

}
