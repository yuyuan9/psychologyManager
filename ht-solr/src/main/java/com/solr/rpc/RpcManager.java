package com.solr.rpc;

import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrDocumentList;

import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.biz.solr.recore.ImportRecord;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;


public interface RpcManager {
	
	public String createPolicyDig(String json);
	//获取列表(solr)
	public MyPage getList(String solrUrl,String solrCore,String q,String fq,String order,Integer start,Integer rows,String classPath,String Highlight,String userId,Boolean hight) throws Exception;
	//获取总数（此方法防止页面多次调用list导致总条数不准确）(solr)
	public Integer getCounts(String solrUrl,String solrCore,String q,String fq) throws Exception;
	//添加（修改）数据(solr)
	public String add(String solrUrl,String solrCore,String json,String classPath,String serviceName) throws Exception;
	public String addList(String solrUrl,String solrCore,String jsonlist,String classPath,String serviceName) throws Exception;
	//删除数据(solr)
	public String deleteByIds(String solrUrl,String solrCore,String ids,String serviceName) throws Exception;
	//更具某字段批量删除(solr)
	public String deleteByQuery(String solrUrl,String solrCore,String fileName,String fileValue,String serviceName) throws Exception;
	//获取搜索记录
	public MyPage<SolrSearchRecord> solrlist(String page) throws Exception;
	//删除搜索记录(包含批量删除和单个删除)
	public String solrdeleted(String ids);
	//政策库过滤导入数据，防止重复导入
	public List<Policylib> getPolicyList(String jsonlist,String fields);
	//立项库过滤导入数据，防止重复导入
	public List<Projectlib> getProjectList(String jsonlist,String fields);
	//获取导入记录
	public MyPage<ImportRecord> importlist(String page) throws Exception;
	//删除导入记录(包含批量删除和单个删除)
	public String importdeleted(String ids);
	//添加导入记录()
	public String importadd(String importRecord);
}
