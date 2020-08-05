package com.solr.addr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 地址配置类，管理
 * @author jied
 *
 */
@Component
@ConfigurationProperties(prefix = "solr")
@PropertySource(value = { "classpath:solr.properties" })
public class SolrAddrConfig {
	//服务器（http://47.92.70.12/solr/）
	@Value("solr.url_first")
	private String url_first;
	//服务器（http://47.92.70.12/solr/）
	@Value("solr.url_second")
	private String url_second;
	//高企云政策大数据
	@Value("solr.first_policylib")
	private String first_policylib;
	//高企云立项库
	@Value("solr.first_projectlib")
	private String first_projectlib;
	//高企云社区发帖
	@Value("solr.second_community")
	private String second_community;
	//高企云社区评论
	@Value("solr.second_comments")
	private String second_comments;
	//高企云社区回复
	@Value("solr.second_reply")
	private String second_reply;
	//高企云企政查企业信息
	@Value("solr.second_compnew")
	private String second_compnew;
	//高企云企政查企业专利
	@Value("solr.second_comppatentrainpat")
	private String second_comppatentrainpat;
	//高企云企政查企业软件著作权
	@Value("solr.second_compsoftware")
	private String second_compsoftware;
	//高企云企政查企业商标信息
	@Value("solr.second_compbrand")
	private String second_compbrand;
	//高企云政策速递
	@Value("solr.second_policydig")
	private String second_policydig;
	//高企云文库
	@Value("solr.second_library")
	private String second_library;

	public String getUrl_first() {
		return url_first;
	}

	public String getUrl_second() {
		return url_second;
	}

	public String getFirst_policylib() {
		return first_policylib;
	}

	public String getFirst_projectlib() {
		return first_projectlib;
	}

	public String getSecond_community() {
		return second_community;
	}

	public String getSecond_comments() {
		return second_comments;
	}

	public String getSecond_reply() {
		return second_reply;
	}

	public String getSecond_compnew() {
		return second_compnew;
	}

	public String getSecond_comppatentrainpat() {
		return second_comppatentrainpat;
	}

	public String getSecond_compsoftware() {
		return second_compsoftware;
	}

	public String getSecond_compbrand() {
		return second_compbrand;
	}

	public String getSecond_policydig() {
		return second_policydig;
	}

	public String getSecond_library() {
		return second_library;
	}

	public void setUrl_first(String url_first) {
		this.url_first = url_first;
	}

	public void setUrl_second(String url_second) {
		this.url_second = url_second;
	}

	public void setFirst_policylib(String first_policylib) {
		this.first_policylib = first_policylib;
	}

	public void setFirst_projectlib(String first_projectlib) {
		this.first_projectlib = first_projectlib;
	}

	public void setSecond_community(String second_community) {
		this.second_community = second_community;
	}

	public void setSecond_comments(String second_comments) {
		this.second_comments = second_comments;
	}

	public void setSecond_reply(String second_reply) {
		this.second_reply = second_reply;
	}

	public void setSecond_compnew(String second_compnew) {
		this.second_compnew = second_compnew;
	}

	public void setSecond_comppatentrainpat(String second_comppatentrainpat) {
		this.second_comppatentrainpat = second_comppatentrainpat;
	}

	public void setSecond_compsoftware(String second_compsoftware) {
		this.second_compsoftware = second_compsoftware;
	}

	public void setSecond_compbrand(String second_compbrand) {
		this.second_compbrand = second_compbrand;
	}

	public void setSecond_policydig(String second_policydig) {
		this.second_policydig = second_policydig;
	}

	public void setSecond_library(String second_library) {
		this.second_library = second_library;
	}
}
