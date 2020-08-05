package com.ht.biz.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.projectlib.Projectlib;

public interface ProjectlibService extends IService<Projectlib>{
	public Integer reset(PageData pd);
	public Integer dealReset(PageData pd);
	public String getAppQueryStr(String keyword,String province,String city,String area,String year);
	public String getPcQueryStr(String keyword,String province,String city,String area,String year1,String year2,String regions,String countrys,String provinces,String citys,String areas);
	public String getSpQueryStr(String keyword,String province,String city,String area,String year);
	public String getQueryById(String id);
	public String getPcAfterStr(String keyword,String province,String city,String area,String year,String userType,String companyid);
}
