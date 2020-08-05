package com.ht.commons.support.geelink.entity;

import java.util.Date;

import com.ht.commons.support.geelink.annotation.HighLightParam;
import com.ht.commons.support.geelink.annotation.ReturnParam;


public class GeelinkPolicyDig {
	@ReturnParam
	private String id;
	
	@ReturnParam
	@HighLightParam
	private String name;
	// 关键字
	private String seoword;

	@ReturnParam
	@HighLightParam
	private String title;
	
	@ReturnParam
	private String date;
	
	@ReturnParam
	private String  datetime;

	@ReturnParam
	private String province;// 省

	@ReturnParam
	private String city; // 市

	@ReturnParam
	private String area;// 区

	@ReturnParam
	private Integer areaGrade = 0;

	@ReturnParam
	private String companyProvince;

	@ReturnParam
	private String companyCity;

	@ReturnParam
	private String companyArea;

	@ReturnParam
	private String source;

	private String content;

	@ReturnParam
	private String url;

	@ReturnParam
	private String shortUrl;

	@ReturnParam
	private String sign;// 标签

	@ReturnParam
	private Integer productType = 0;// 政策类型 1:高新技术企业|2:研发机构|3:科技立项|4:科技成果|5:知识产权|6:科技财税|7:人才团队|8:其他

	@ReturnParam
	private String issueCompany;// 主管单位1:人民政府|2:科技局|3:知识产权局|4:人社局|5:经信委|6:工信厅|7:发改委|8:工商局|9:中小企业|10:其他|11:商务委|12:经济科技促进局|13:高新技术企业协会|14:中关村|15省科技厅

	@ReturnParam
	private Integer nature = 0;// 性质分类1:申报通知（指南）|2:政府文件（管理办法）|3:公示名录|4:政策解读|5:新闻资讯|6:其他

	@ReturnParam
	private Integer status = 0;// 1:入库，0：原始数据,2回收站,3暂存

	@ReturnParam
	private Integer onShow = 0;// 0不展示；1展示

	@ReturnParam
	private Integer top = 0;// 0不置顶；1置顶

	@ReturnParam
	private String  beginDate;// 申报开始时间

	@ReturnParam
	private String  endDate;// 申报结束时间

	@ReturnParam
	private Integer browsecount = 0;

	@ReturnParam
	private String field;// 政策领域

//	@ReturnParam
//	private Integer applyStatus = 0;// 申报状态：0未开始1申报中2结束申报

	@ReturnParam
	private Date originalDate;

//	private Integer forward = 0;// 转发量

//	private Integer collection = 0;// 收藏量

	@ReturnParam
	private String industry;// 产业领域

	@ReturnParam
	private String industryFirst;// 产业领域一级目录（方便记录和查询）
	
	@ReturnParam
	private String tecnology;// 技术领域
	
	@ReturnParam
	private String tecnologyFirst;// 技术领域一级目录（方便记录和查询）
	
	@ReturnParam
	private String product;// 产品领域
	
	@ReturnParam
	private String productFirst;// 产品领域一级目录（方便记录和查询）
	
	@ReturnParam
	private Integer sort;// 排序
	
	/**
	 * geelink 增加标签字段
	 */
	@ReturnParam
	private String _gl_dp_taxonomy_policyTypeTax;
	
	@ReturnParam
	private String _gl_dp_taxonomy_projectOneTax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeoword() {
		return seoword;
	}

	public void setSeoword(String seoword) {
		this.seoword = seoword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String get_gl_dp_taxonomy_policyTypeTax() {
		return _gl_dp_taxonomy_policyTypeTax;
	}

	public void set_gl_dp_taxonomy_policyTypeTax(String _gl_dp_taxonomy_policyTypeTax) {
		this._gl_dp_taxonomy_policyTypeTax = _gl_dp_taxonomy_policyTypeTax;
	}

	public String get_gl_dp_taxonomy_projectOneTax() {
		return _gl_dp_taxonomy_projectOneTax;
	}

	public void set_gl_dp_taxonomy_projectOneTax(String _gl_dp_taxonomy_projectOneTax) {
		this._gl_dp_taxonomy_projectOneTax = _gl_dp_taxonomy_projectOneTax;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getAreaGrade() {
		return areaGrade;
	}

	public void setAreaGrade(Integer areaGrade) {
		this.areaGrade = areaGrade;
	}

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	public String getCompanyArea() {
		return companyArea;
	}

	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getIssueCompany() {
		return issueCompany;
	}

	public void setIssueCompany(String issueCompany) {
		this.issueCompany = issueCompany;
	}

	public Integer getNature() {
		return nature;
	}

	public void setNature(Integer nature) {
		this.nature = nature;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOnShow() {
		return onShow;
	}

	public void setOnShow(Integer onShow) {
		this.onShow = onShow;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}



	public Integer getBrowsecount() {
		return browsecount;
	}

	public void setBrowsecount(Integer browsecount) {
		this.browsecount = browsecount;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

//	public Integer getApplyStatus() {
//		return applyStatus;
//	}
//
//	public void setApplyStatus(Integer applyStatus) {
//		this.applyStatus = applyStatus;
//	}

	public Date getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}

/*	public Integer getForward() {
		return forward;
	}

	public void setForward(Integer forward) {
		this.forward = forward;
	}*/

//	public Integer getCollection() {
//		return collection;
//	}
//
//	public void setCollection(Integer collection) {
//		this.collection = collection;
//	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIndustryFirst() {
		return industryFirst;
	}

	public void setIndustryFirst(String industryFirst) {
		this.industryFirst = industryFirst;
	}

	public String getTecnology() {
		return tecnology;
	}

	public void setTecnology(String tecnology) {
		this.tecnology = tecnology;
	}

	public String getTecnologyFirst() {
		return tecnologyFirst;
	}

	public void setTecnologyFirst(String tecnologyFirst) {
		this.tecnologyFirst = tecnologyFirst;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductFirst() {
		return productFirst;
	}

	public void setProductFirst(String productFirst) {
		this.productFirst = productFirst;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	

}
