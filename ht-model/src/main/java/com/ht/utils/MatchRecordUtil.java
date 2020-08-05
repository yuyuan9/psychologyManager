package com.ht.utils;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.policymatch.MatchRecord;

public class MatchRecordUtil {
	//MatchRecord相关属性
	public static void save(String companyName,String province,String city,String area,String phone,String companyField,String scaleScale,String createId,String regionid){
		new Thread(new Runnable() {
			@Override
			public void run() {
				IService mrService=(IService) SpringContextUtil.getBean("matchRecordServiceImpl");
				MatchRecord mr=new MatchRecord();
				mr.setCompanyName(companyName);
				mr.setPhone(phone);
				mr.setProvince(province);
				mr.setCity(city);
				mr.setArea(area);
				mr.setCompanyField(companyField);
				mr.setScaleScale(scaleScale);
				mr.setCreateid(createId);
				mr.setRegionid(regionid);
				mr.setCreatedate(new Date());
				mrService.save(mr);
			}
		}).start();
	}
	
}
