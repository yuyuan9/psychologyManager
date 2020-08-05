package com.ht.biz.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.ht.biz.service.HttpSolrService;
import com.ht.biz.service.SolrSearchRecordService;
import com.ht.commons.constants.Const;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.solr.config.SolrTransformation;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;

public class SolrUtils {
	//获取引擎list集合数据
	/*page 分页、封装数据
	 * obj 封装类型
	 * hight 是否标红处理
	 * solrCore 处理模块
	 * 查询条件 q
	 * 查询条件 fq
	 * order 排序
	 * highlight 标红关键字处理
	 * solrRecord 是否加入搜索记录
	 */
	public static MyPage getList(MyPage page,Object obj,Boolean hight,String solrCore,String q,String fq,String order,String highlight,Boolean solrRecord) throws Exception{
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(Const.solrUrl, solrCore);
		SolrQuery sq=new SolrQuery();
		sq.set("q", StringUtils.isBlank(q)?"*:*":q);
		sq.set("fq", StringUtils.isBlank(fq)?"":fq);
		sq.set("sort", StringUtils.isBlank(order)?"":order);
		sq.setStart((int)((page.getCurrent()-1)*page.getSize()));
		sq.setRows((int)page.getSize());
		SolrDocumentList list=httpSolrService.getlist(httpSolrClient, sq);
		Integer counts=httpSolrService.getlistcounts(httpSolrClient, sq);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		SolrTransformation stf=new SolrTransformation();
		List lists=stf.getSolrDocumentList(list, obj, StringUtils.isBlank(highlight)?null:highlight.split("OR"),hight);
		page.setCurrent(page.getCurrent()==0?1:page.getCurrent());
		page.setSize(page.getSize());
		page.setTotal(counts);
		page.setPages(counts/page.getSize()==0?counts/page.getSize():counts/page.getSize()+1);
		page.setRecords(lists);
		if(solrRecord){
			SearchRecordUtil.searchRecord(true, q, fq, order, highlight, counts, solrCore, null);
		}
		//SolrSearchRecordService SolrSearchRecordService=(SolrSearchRecordService) SpringContextUtil.getBean("solrSearchRecordService");
		//SolrSearchRecordService.save(s);
		list=null;
		lists=null;
		sq=null;
		httpSolrService=null;
		return page;
	}
	//添加和修改数据
	public static void add(String solrUrl,String core,Object obj) throws Exception{
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, core);
		SolrTransformation stf=new SolrTransformation();
		SolrInputDocument sid=stf.getSolrInputDocument(obj);
		httpSolrService.addSolrInputDocument(sid, httpSolrClient);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		httpSolrService=null;
		sid=null;
		obj=null;
	}
	//添加和修改数据
	public static void addList(String solrUrl,String core,List list) throws Exception{
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, core);
		SolrTransformation stf=new SolrTransformation();
		for(Object obj:list){
			SolrInputDocument sid=stf.getSolrInputDocument(obj);
			httpSolrService.addSolrInputDocument(sid, httpSolrClient);
		}
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		httpSolrService=null;
		//sid=null;
		//obj=null;
	}
	//根据id删除数据
	public static void deleted(String solrUrl,String core,String ids) throws Exception{
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, core);
		for(String id:ids.split(",")){
			httpSolrService.deleteDocumentById(id, httpSolrClient);
		}
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		httpSolrService=null;
	}
	//根据指定字段删除数据（数据较多时耗时过长）
	public static void deleteByQuery(String solrUrl, String solrCore, String fileName, String fileValue) throws Exception {
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
		httpSolrService.deleteDocumentByQuery(fileName, fileValue, httpSolrClient);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		httpSolrService=null;
	}
	//根据指定字段查询查询数据是否重复
	public static Boolean getone(String solrUrl, String solrCore,String sqs) throws Exception{
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
		SolrQuery sq=new SolrQuery();
		sq.set("q", sqs);
		SolrDocumentList list=httpSolrService.getlist(httpSolrClient, sq);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		httpSolrService=null;
		sq=null;
		if(list!=null&&list.size()>1){
			return true;
		}else{
			return false;
		}
	}
	//根据id来查询数据
	public static Object getById(String solrUrl, String solrCore,String id,Object obj) throws Exception{
		Object object=null;
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
		SolrQuery sq=new SolrQuery();
		sq.set("q", "id:"+id);
		SolrDocumentList list=httpSolrService.getlist(httpSolrClient, sq);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		if(list.size()>0){
			SolrTransformation stf=new SolrTransformation();
			object=stf.getSolrDocument(list.get(0), obj);
		}
		httpSolrService=null;
		return  object;
	}
	
	public static List<Object> getQuery(String solrUrl, String solrCore,String filename,Object filevalue,Object obj) throws Exception{
		List<Object> list=new ArrayList<Object>();
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
		SolrQuery sq=new SolrQuery();
		sq.set("q", filename+":"+filevalue);
		SolrDocumentList lists=httpSolrService.getlist(httpSolrClient, sq);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		if(lists.size()>0){
			SolrTransformation stf=new SolrTransformation();
			list=stf.getSolrDocumentList(lists, obj,null,null);
		}
		httpSolrService=null;
		return  list;
	}
}
