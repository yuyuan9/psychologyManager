package com.ht.utils;

import java.util.Date;
import java.util.List;

import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.entity.policydig.PolicyDig;

public class CommonsUtil {

	
	public static void filterDate(List<PolicyDig> list){
		if(list!=null && !list.isEmpty()){
		  for(PolicyDig dig:list){
			  try{
//				  int flag=Long.valueOf(DateUtil.getDaySub(DateUtil.dateToStr(dig.getEndDate(),11),DateUtil.dateToStr(new Date(),11))).intValue();
//				  dig.setStatus(flag==0?1:flag);
				  if(dig.getEndDate()!=null){
						int flag=Long.valueOf(DateUtil.getDaySub(DateUtil.dateToStr(dig.getEndDate(),11),DateUtil.dateToStr(new Date(),11))).intValue();
						dig.setStatus(flag==0?1:flag);
					}else{
						dig.setStatus(0);
					}
			  }catch(Exception e){
				  dig.setStatus(-1);
			  }
		  }	
		}
	}
}
