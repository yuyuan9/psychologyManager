package com.solr.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

//import com.ht.commons.utils.MyPage;

public interface HttpSolrService {
	//创建引擎连接
	public HttpSolrClient getHttpSolrClient(String solrUrl,String core) throws Exception;
	
	//获取列表
	public SolrDocumentList getlist(HttpSolrClient solrClient,SolrQuery sq) throws Exception;
	
	//获取总数
	public Integer getlistcounts(HttpSolrClient solrClient,SolrQuery sq) throws Exception;
	
	//提交以及关闭服务
	public void commitAndCloseSolr(HttpSolrClient solrClient)throws Exception;
	
	//添加或者修改文档
	public void addSolrInputDocument(SolrInputDocument sid,HttpSolrClient solrClient) throws Exception;
		
	//批量添加或者修改文档
	public void addSolrInputDocumentlist(List<SolrInputDocument> sids,HttpSolrClient solrClient) throws Exception;	
		
	//删除文档(方法一)
	public void deleteDocumentById(String id,HttpSolrClient solrClient) throws Exception;	
		
	//删除文档(方法二)
	public void deleteDocumentByQuery(String name,String value,HttpSolrClient solrClient) throws Exception;
	
	//添加排序
	public void setOrder(SolrQuery sq,String orderValue);
	
	//设置q查询
	public void setQ(SolrQuery sq,String qValue);
	
	//设置fq查询
	public void setFq(SolrQuery sq,String fqValue);
	
	//设置分页
	public void setPage(SolrQuery sq,Integer start,Integer end);
	
	//封装Page分页
	//public MyPage getPage(List<?> list,Integer counts,MyPage page);
}
