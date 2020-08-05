package com.ht.biz.util;

import java.util.Date;

import com.ht.biz.service.SolrSearchRecordService;
import com.ht.commons.constants.Const;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;
import com.ht.utils.LoginUserUtils;

public class SearchRecordUtil {
	
	public static void searchRecord(String keyword,Integer counts){
		searchRecord(false,null,null,null,keyword,counts,null,null);
	}

	public static void searchRecord(String keyword,Integer counts,String solrCoreType){
		searchRecord(false,null,null,null,keyword,counts,solrCoreType,null);
	}


	public static void searchRecord(String q,String fq,String highlight,Integer counts,String solrCoreType){
		searchRecord(true,q,fq,null,highlight,counts,solrCoreType,null);
	}
	//issolr判断是否是solr引擎，其他所有参数为SolrSearchRecord的属性
	public static void searchRecord(boolean issolr,String q,String fq,String order,String highlight,Integer counts,String solrCoreType,String remark){
		new Thread(new Runnable() {
			@Override
			public void run() {
				SolrSearchRecord entity = new SolrSearchRecord();
				if(issolr){
					entity.setFq(fq);
					entity.setQ(q);
				}
				entity.setOrders(order);
				entity.setHighlight(highlight);
				entity.setCounts(counts);
				entity.setCreatedate(new Date());
				entity.setSolrAddr(Const.solrUrl);
				entity.setSolrCore(solrCoreType);
				entity.setStatus(2);
				entity.setRemark(remark);
				try{
					if(null!=LoginUserUtils.getLoginUser()){
						entity.setCreateid(LoginUserUtils.getUserId());
						entity.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
					}
					SolrSearchRecordService solrSearch=(SolrSearchRecordService)SpringContextUtil.getBean(SolrSearchRecordService.class);
					solrSearch.save(entity);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}

}
