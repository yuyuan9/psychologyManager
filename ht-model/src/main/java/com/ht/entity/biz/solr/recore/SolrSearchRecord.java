package com.ht.entity.biz.solr.recore;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_solr_search_record")
public class SolrSearchRecord extends BaseEntity{
	//搜索地址
	@TableField(value="solrAddr")
	private String solrAddr;
	//搜索模块
	@TableField(value="solrCore")
	private String solrCore;
	//搜索条件q
	@TableField(value="q")
	private String q;
	//搜索条件fq
	@TableField(value="fq")
	private String fq;
	//搜索总数
	@TableField(value="counts")
	private Integer counts=0;
	//搜索排序
	@TableField(value="orders")
	private String orders;
	//搜索标红内容
	@TableField(value="highlight")
	private String highlight;
	//状态(1已处理2未处理)
	@TableField(value="status")
	private Integer status=2;
	//备注
	@TableField(value="remark")
	private String remark;
	
	
	public String getSolrAddr() {
		return solrAddr;
	}
	public String getSolrCore() {
		return solrCore;
	}
	public String getQ() {
		return q;
	}
	public String getFq() {
		return fq;
	}
	public Integer getCounts() {
		return counts;
	}
	public String getHighlight() {
		return highlight;
	}
	public void setSolrAddr(String solrAddr) {
		this.solrAddr = solrAddr;
	}
	public void setSolrCore(String solrCore) {
		this.solrCore = solrCore;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public void setFq(String fq) {
		this.fq = fq;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public Integer getStatus() {
		return status;
	}
	public String getRemark() {
		return remark;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
