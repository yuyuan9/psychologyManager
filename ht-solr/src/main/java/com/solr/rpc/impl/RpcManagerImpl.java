package com.solr.rpc.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.codehaus.jackson.map.ObjectMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.constants.Const;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.ReflectHelper;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.biz.solr.recore.ImportRecord;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;
import com.solr.addr.SolrTransformation;
import com.solr.rpc.RpcManager;
import com.solr.service.HttpSolrService;
import com.solr.service.ImportRecordService;
import com.solr.service.PolicylibService;
import com.solr.service.ProjectlibService;
import com.solr.service.SolrSearchRecordService;

import net.sf.json.JSONArray;

public class RpcManagerImpl implements RpcManager {

	@Override
	public String createPolicyDig(String json) {
		// TODO Auto-generated method stub
		return json;
	}
	/*
	 * solrUrl：solr引擎地址
	 * solrCore：solr模块core
	 * q：查询条件q
	 * fq：查询条件fq
	 * order：查询结果排序方法
	 * start：当前页
	 * rows：每页数量
	 * classPath：相关实体类全路径
	 * Highlight：关键字搜索（标红），多个关键字用OR链接
	 * userId：查询人ID
	 */
	@Override
	public MyPage getList(String solrUrl, String solrCore, String q, String fq,String order,Integer start, Integer rows,String classPath,String Highlight,String userId,Boolean hight)
			throws Exception {
		// TODO Auto-generated method stub
		MyPage page=new MyPage();
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
		SolrSearchRecordService SolrSearchRecordService=(SolrSearchRecordService) SpringContextUtil.getBean("solrSearchRecordService");
		SolrSearchRecord s=new SolrSearchRecord();
		SolrQuery sq=new SolrQuery();
		sq.set("q", StringUtils.isBlank(q)?"*:*":q);
		sq.set("fq", StringUtils.isBlank(fq)?"":fq);
		sq.set("sort", StringUtils.isBlank(order)?"":order);
		sq.setStart((start-1)*rows);
		sq.setRows(rows);
		SolrDocumentList list=httpSolrService.getlist(httpSolrClient, sq);
		Integer counts=httpSolrService.getlistcounts(httpSolrClient, sq);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		SolrTransformation stf=new SolrTransformation();
		Object obj=Class.forName(classPath).newInstance();
		List lists=stf.getSolrDocumentList(list, obj, StringUtils.isBlank(Highlight)?null:Highlight.split("OR"),hight);
		page.setCurrent(start==0?1:start);
		page.setSize(rows);
		page.setTotal(counts);
		page.setPages(counts/page.getSize()==0?counts/page.getSize():counts/page.getSize()+1);
		page.setRecords(lists);
		s.setSolrAddr(solrUrl);s.setSolrCore(solrCore);s.setQ(q);s.setFq(fq);
		s.setCounts(counts);s.setOrders(order);s.setHighlight(Highlight);
		s.setCreatedate(new Date());s.setCreateid(userId);
		SolrSearchRecordService.saveOrUpdate(s);
		return page;
	}
	/*
	 * solrUrl：solr引擎地址
	 * solrCore：solr模块core
	 * q：查询条件q
	 * fq：查询条件fq
	 */
	@Override
	public Integer getCounts(String solrUrl, String solrCore, String q, String fq) throws Exception {
		// TODO Auto-generated method stub
		HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
		HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
		SolrQuery sq=new SolrQuery();
		sq.set("q", StringUtils.isBlank(q)?"*:*":q);
		sq.set("fq", StringUtils.isBlank(fq)?"":fq);
		Integer counts=httpSolrService.getlistcounts(httpSolrClient, sq);
		httpSolrService.commitAndCloseSolr(httpSolrClient);
		return counts;
	}
	/*
	 * solrUrl：solr引擎地址
	 * solrCore：solr模块core
	 * json：数据json化
	 * classPath：相关实体类全路径
	 * serviceName：相关@service名称
	 */
	@Override
	public String add(String solrUrl, String solrCore, String json,String classPath,String serviceName) throws Exception {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper=new ObjectMapper();
			HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
			IService iService=(IService) SpringContextUtil.getBean(serviceName);
			HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
			Object obj=Class.forName(classPath).newInstance();
			obj =mapper.readValue(json, obj.getClass());
			iService.saveOrUpdate(obj);
			SolrTransformation stf=new SolrTransformation();
			SolrInputDocument sid=stf.getSolrInputDocument(obj);
			httpSolrService.addSolrInputDocument(sid, httpSolrClient);
			httpSolrService.commitAndCloseSolr(httpSolrClient);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "error";
		}
		
	}
	/*
	 * solrUrl：solr引擎地址
	 * solrCore：solr模块core
	 * jsonlist：数据list集合json化
	 * classPath：相关实体类全路径
	 * serviceName：相关@service名称
	 */
	@Override
	public String addList(String solrUrl, String solrCore, String jsonlist,String classPath,String serviceName) throws Exception {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper=new ObjectMapper();
			HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
			IService iService=(IService) SpringContextUtil.getBean(serviceName);
			HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
			Object obj=Class.forName(classPath).newInstance();
			SolrTransformation stf=new SolrTransformation();
			JSONArray jsons=JSONArray.fromObject(jsonlist);
			if(jsons.size()>0){
				for(Object json:jsons){
					obj =mapper.readValue(String.valueOf(json), obj.getClass());
					iService.saveOrUpdate(obj);
					//iService.save(obj);
					SolrInputDocument sid=stf.getSolrInputDocument(obj);
					httpSolrService.addSolrInputDocument(sid, httpSolrClient);
				}
				httpSolrService.commitAndCloseSolr(httpSolrClient);
			}
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	/*
	 * solrUrl：solr引擎地址
	 * solrCore：solr模块core
	 * ids：数据ID集合
	 * serviceName：相关@service名称 
	 */
	@Override
	public String deleteByIds(String solrUrl, String solrCore, String ids,String serviceName) throws Exception {
		// TODO Auto-generated method stub
		try {
			HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
			IService iService=(IService) SpringContextUtil.getBean(serviceName);
			HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
			for(String id:ids.split(",")){
				iService.removeById(id);
				httpSolrService.deleteDocumentById(id, httpSolrClient);
			}
			httpSolrService.commitAndCloseSolr(httpSolrClient);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	/*
	 * solrUrl：solr引擎地址
	 * solrCore：solr模块core
	 * fileName：字段属性名称
	 * fileValue：字段值
	 * serviceName：相关@service名称 
	 */
	@Override
	public String deleteByQuery(String solrUrl, String solrCore, String fileName, String fileValue,String serviceName) throws Exception {
		try {
			HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
			IService iService=(IService) SpringContextUtil.getBean(serviceName);
			HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(solrUrl, solrCore);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put(fileName, fileValue);
			iService.removeByMap(map);
			httpSolrService.deleteDocumentByQuery(fileName, fileValue, httpSolrClient);
			httpSolrService.commitAndCloseSolr(httpSolrClient);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
	/*
	 * 引擎搜索记录
	 */
	@Override
	public MyPage<SolrSearchRecord> solrlist(String page) throws Exception{
		// TODO Auto-generated method stub
		ObjectMapper mapper=new ObjectMapper();
		MyPage mypage=mapper.readValue(String.valueOf(page), MyPage.class);
		SolrSearchRecordService SolrSearchRecordService=(SolrSearchRecordService) SpringContextUtil.getBean("solrSearchRecordService");
		mypage=(MyPage) SolrSearchRecordService.page(mypage);
		return mypage;
	}
	/*
	 * 引擎搜索记录删除
	 * ids：数据id集合
	 */
	@Override
	public String solrdeleted(String ids){
		// TODO Auto-generated method stub
		try {
			List<String> list=new ArrayList<String>();
			for(String id:ids.split(",")){
				list.add(id);
			}
			SolrSearchRecordService SolrSearchRecordService=(SolrSearchRecordService) SpringContextUtil.getBean("solrSearchRecordService");
			SolrSearchRecordService.removeByIds(list);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	/*
	 * jsonlist：政策大数据list集合json化
	 * fields：判断重复的字段（多个，用,隔开）
	 */
	@Override
	public List<Policylib> getPolicyList(String jsonlist,String fields) {
		// TODO Auto-generated method stub
		List<Policylib> list = new ArrayList<Policylib>();
		try {
			ObjectMapper mapper=new ObjectMapper();
			PolicylibService policylibService=(PolicylibService) SpringContextUtil.getBean("policylibService");
			JSONArray jsons=JSONArray.fromObject(jsonlist);
			if(jsons.size()>0){
				for(Object json:jsons){
					Policylib p=mapper.readValue(String.valueOf(json), Policylib.class);
					QueryWrapper<Policylib> qw=new QueryWrapper<Policylib>();
					for(String s:fields.split(",")){
						Object o=ReflectHelper.getValueByFieldName(p, s);
						if(o==null){
							qw.isNull(s);
						}else{
							qw.eq(s, o);
						}
					}
					Object obj=policylibService.getOne(qw, false);
					if(obj==null){
						list.add(p);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/*
	 * jsonlist：政策立项list集合json化
	 * fields：判断重复的字段（多个，用,隔开）
	 */
	@Override
	public List<Projectlib> getProjectList(String jsonlist,String fields) {
		// TODO Auto-generated method stub
		List<Projectlib> list = new ArrayList<Projectlib>();
		try {
			ObjectMapper mapper=new ObjectMapper();
			ProjectlibService projectlibService=(ProjectlibService) SpringContextUtil.getBean("projectlibService");
			JSONArray jsons=JSONArray.fromObject(jsonlist);
			if(jsons.size()>0){
				for(Object json:jsons){
					Projectlib p=mapper.readValue(String.valueOf(json), Projectlib.class);
					QueryWrapper<Projectlib> qw=new QueryWrapper<Projectlib>();
					for(String s:fields.split(",")){
						Object o=ReflectHelper.getValueByFieldName(p, s);
						if(o==null){
							qw.isNull(s);
						}else{
							qw.eq(s, o);
						}
					}
					Object obj=projectlibService.getOne(qw, false);
					if(obj==null){
						list.add(p);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public MyPage<ImportRecord> importlist(String page) throws Exception {
		// TODO Auto-generated method stub
		MyPage<ImportRecord> mypage=null;
		try {
			ObjectMapper mapper=new ObjectMapper();
			ImportRecordService importRecordService=(ImportRecordService) SpringContextUtil.getBean("importRecordService");
			mypage = mapper.readValue(String.valueOf(page), MyPage.class);
			mypage=importRecordService.findlist(mypage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mypage;
	}
	@Override
	public String importdeleted(String ids) {
		// TODO Auto-generated method stub
		try {
			ImportRecordService importRecordService=(ImportRecordService) SpringContextUtil.getBean("importRecordService");
			HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
			HttpSolrClient httpSolrClient=null;
			for(String id:ids.split(",")){
				ImportRecord im=importRecordService.getById(id);
				if(StringUtils.equals("政策库", im.getImportLocal())){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("hisImportId", id);
					httpSolrClient=httpSolrService.getHttpSolrClient(Const.solrUrl, Const.solrCore_policylib);
					PolicylibService policylibService=(PolicylibService) SpringContextUtil.getBean("policylibService");
					policylibService.removeByMap(map);
				}else if(StringUtils.equals("立项库", im.getImportLocal())){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("hisImportId", id);
					httpSolrClient=httpSolrService.getHttpSolrClient(Const.solrUrl, Const.solrCore_projectlib);
					ProjectlibService projectlibService=(ProjectlibService) SpringContextUtil.getBean("projectlibService");
					projectlibService.removeByMap(map);
				}
				importRecordService.removeById(id);
				if(httpSolrClient!=null){
					httpSolrService.deleteDocumentByQuery("hisImportId", id, httpSolrClient);
				}
			}
			if(httpSolrClient!=null){
				httpSolrService.commitAndCloseSolr(httpSolrClient);
			}
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
	@Override
	public String importadd(String importRecord) {
		// TODO Auto-generated method stub
		try {
			ImportRecordService importRecordService=(ImportRecordService) SpringContextUtil.getBean("importRecordService");
			ObjectMapper mapper=new ObjectMapper();
			ImportRecord im=mapper.readValue(importRecord,ImportRecord.class);
			importRecordService.save(im);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
}
