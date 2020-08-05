package com.ht.biz.quarz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.GuaranteeMoneyService;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.GuaranteeMoney;

@Component
@EnableScheduling
public class GuaranteeMoneyQuarz {

	@Autowired
	private GuaranteeMoneyService guaranteeMoneyService;
	
	public void task(){
		Date date=new Date();
		PageData pd=new PageData();
		pd.add("newdate", DateUtil.dateToStr(date, 11));
		QueryWrapper<GuaranteeMoney> qw=new QueryWrapper<GuaranteeMoney>();
		qw.eq("status", 1);
		pd.add("a", 0);pd.add("b", 10);
		List<GuaranteeMoney> list=guaranteeMoneyService.quarzlist(pd);
		while(list!=null&&list.size()>0){
			for(GuaranteeMoney g:list){
				if(g.getEndDate().compareTo(date)<0){
					g.setStatus(2);
				}
				g.setLastmodified(date);
				g.setRegionid(pd.getString("newdate"));
				guaranteeMoneyService.updateById(g);
			}
			list=guaranteeMoneyService.quarzlist(pd);
		}
		list=null;
	}
}
