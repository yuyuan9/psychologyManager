package com.ht.biz.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.ht.biz.service.HttpSolrService;

@Service("httpSolrService")
public class HttpSolrServiceImpl implements HttpSolrService{

	@Override
	public HttpSolrClient getHttpSolrClient(String solrUrl, String core) throws Exception {
		// TODO Auto-generated method stub
		HttpSolrClient solrClient = new HttpSolrClient(solrUrl+core);
		return solrClient;
	}

	@Override
	public SolrDocumentList getlist(HttpSolrClient solrClient, SolrQuery sq) throws Exception {
		// TODO Auto-generated method stub
		SolrDocumentList results=null;
		QueryResponse result = solrClient.query(sq);
		results =result.getResults();
		return results;
	}

	@Override
	public Integer getlistcounts(HttpSolrClient solrClient, SolrQuery sq) throws Exception {
		// TODO Auto-generated method stub
		SolrDocumentList results =null;
		QueryResponse result = solrClient.query(sq);
		results =result.getResults();
		return (int) results.getNumFound();
	}

	@Override
	public void commitAndCloseSolr(HttpSolrClient solrClient) throws Exception {
		// TODO Auto-generated method stub
		solrClient.commit();
		solrClient.close();
		solrClient=null;
	}

	@Override
	public void addSolrInputDocument(SolrInputDocument sid, HttpSolrClient solrClient) throws Exception {
		// TODO Auto-generated method stub
		solrClient.add(sid);
	}

	@Override
	public void addSolrInputDocumentlist(List<SolrInputDocument> sids, HttpSolrClient solrClient) throws Exception {
		// TODO Auto-generated method stub
		for(SolrInputDocument sid:sids){
			addSolrInputDocument(sid,solrClient);
		}
	}

	@Override
	public void deleteDocumentById(String id, HttpSolrClient solrClient) throws Exception {
		// TODO Auto-generated method stub
		//solrClient.deleteById("00002");
		solrClient.deleteById(id);
	}

	@Override
	public void deleteDocumentByQuery(String name, String value, HttpSolrClient solrClient) throws Exception {
		// TODO Auto-generated method stub
		//solrClient.deleteByQuery("id:00003");
		solrClient.deleteByQuery(""+name+":"+value);
	}

	@Override
	public void setOrder(SolrQuery sq, String orderValue) {
		// TODO Auto-generated method stub
		sq.set("sort", orderValue);
	}

	@Override
	public void setQ(SolrQuery sq, String qValue) {
		// TODO Auto-generated method stub
		sq.set("q", qValue);
	}

	@Override
	public void setFq(SolrQuery sq, String fqValue) {
		// TODO Auto-generated method stub
		sq.set("fq", fqValue);
	}

	@Override
	public void setPage(SolrQuery sq, Integer start, Integer end) {
		// TODO Auto-generated method stub
		sq.setStart(start);
		sq.setRows(end);
	}

//	@Override
//	public MyPage getPage(List<?> list, Integer counts, MyPage page) {
//		// TODO Auto-generated method stub
//		page.setTotal(counts);
//		page.setPages(counts/page.getSize()==0?counts/page.getSize():counts/page.getSize()+1);
//		page.setRecords(list);
//		return page;
//	}
	
}
