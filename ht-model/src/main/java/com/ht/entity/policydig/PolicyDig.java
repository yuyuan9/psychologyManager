package com.ht.entity.policydig;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;


/*
 * 政策速递类 （挖掘及推送数据）
 */
@TableName("t_policy_dig")
public class PolicyDig extends BaseEntity implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TableField(value="name")
	private String name;
	
	
	//关键字
	@TableField(value="seoword")
	 private String seoword;
	
	@TableField(value="title")
	 private String title;
	
	@TableField(value="date")
	 private String date;
	
	@TableField(value="datetime")
	 private Date datetime;
	
	@TableField(value="province")
	 private String province;//省
	
	@TableField(value="city")
	 private String city; //市
	
	@TableField(value="area")
	 private String area;//区
	
	@TableField(value="areaGrade")
	 private Integer areaGrade=0;
	
	@TableField(value="companyProvince")
	 private String companyProvince;
	
	@TableField(value="companyCity")
	 private String companyCity;
	
	@TableField(value="companyArea")
	 private String companyArea;
	
	@TableField(value="source")
	 private String source;
	
	@TableField(value="content")
	 private String content; 
	
	@TableField(value="url")
	 private String url;
	
	@TableField(value="shortUrl")
	 private String shortUrl;
	
	@TableField(value="sign")
	 private String sign;//标签
	
	@TableField(value="productType")
	private Integer productType=0;//政策类型 1:高新技术企业|2:研发机构|3:科技立项|4:科技成果|5:知识产权|6:科技财税|7:人才团队|8:其他

	@TableField(value="issueCompany") 
	private String issueCompany;//主管单位1:人民政府|2:科技局|3:知识产权局|4:人社局|5:经信委|6:工信厅|7:发改委|8:工商局|9:中小企业|10:其他|11:商务委|12:经济科技促进局|13:高新技术企业协会|14:中关村|15省科技厅
	
	@TableField(value="nature")
	private Integer nature=0;//性质分类1:申报通知（指南）|2:政府文件（管理办法）|3:公示名录|4:政策解读|5:新闻资讯|6:其他
	
	@TableField(value="status")
	private Integer status=0;//1:入库，0：原始数据,2回收站,3暂存
	
	@TableField(value="onShow")
	private Integer onShow=1;//0不展示；1展示
	
	@TableField(value="top")
	private Integer top=0;//0不置顶；1置顶
	
	@TableField(value="beginDate")
	private Date beginDate;//申报开始时间
	@TableField(exist=false)
	private String beginDates;//申报开始时间(字符串处理)
	
	@TableField(value="endDate")
	private Date endDate;//申报结束时间
	@TableField(exist=false)
	private String endDates;//申报结束时间(字符串处理)
	 
	@TableField(value="browsecount")
	private Integer browsecount=0;
	
	@TableField(value="field")
	private String field;//政策领域
	
	 @TableField(exist=false)
	 private Integer applyStatus=0;//申报状态：0未开始1申报中2结束申报
	 
	@TableField(value="originalDate")
	 private Date originalDate;
	 
	 @TableField(value="forward")
	 private Integer forward=0;//转发量
	 
	 @TableField(value="collection")
	 private Integer collection=0;//收藏量
	 
	 @TableField(value="industry")
	 private String industry;//产业领域
	 
	 @TableField(value="industryFirst")
	 private String industryFirst;//产业领域一级目录（方便记录和查询）
	 
	@TableField(value="tecnology")
	 private String tecnology;//技术领域
	 
	 @TableField(value="tecnologyFirst")
	 private String tecnologyFirst;//技术领域一级目录（方便记录和查询）
	 //字段特殊处理类
	 @TableField(exist=false)
	 private PolicydigVo pv=new PolicydigVo();
	 
	 @TableField(value="product")
	 private String product;//产品领域
	 
	 @TableField(value="productFirst")
	 private String productFirst;//产品领域一级目录（方便记录和查询）
	 
	 @TableField(value="sort")
	 private Integer sort;//排序
	 
	 public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getSeoword() {
		return seoword;
	}
	public void setSeoword(String seoword) {
		this.seoword = seoword;
	}
	 
	public String getTecnology() {
		return tecnology;
	}
	public String getTecnologyFirst() {
		return tecnologyFirst;
	}
	public void setTecnology(String tecnology) {
		this.tecnology = tecnology;
	}
	public void setTecnologyFirst(String tecnologyFirst) {
		this.tecnologyFirst = tecnologyFirst;
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
	public String getCompanyProvince() {
		return companyProvince;
	}
	public String getCompanyCity() {
		return companyCity;
	}
	public String getCompanyArea() {
		return companyArea;
	}
	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSign() {
		return sign;
	}
	public Integer getNature() {
		return nature;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public void setNature(Integer nature) {
		this.nature = nature;
	}
	public Integer getOnShow() {
		return onShow;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setOnShow(Integer onShow) {
		this.onShow = onShow;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
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
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public Integer getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	public Integer getAreaGrade() {
		return areaGrade;
	}
	public String getIssueCompany() {
		return issueCompany;
	}
	public void setIssueCompany(String issueCompany) {
		this.issueCompany = issueCompany;
	}
	public void setAreaGrade(Integer areaGrade) {
		this.areaGrade = areaGrade;
	}
	public Date getOriginalDate() {
		return originalDate;
	}
	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}
	public Integer getForward() {
		return forward;
	}
	public void setForward(Integer forward) {
		this.forward = forward;
	}
	public Integer getCollection() {
		return collection;
	}
	public void setCollection(Integer collection) {
		this.collection = collection;
	}	 
	
	@Override
	public String toString() {
		return "GeelinkPolicyDig [name=" + name + ", title=" + title + ", date=" + date + ", datetime=" + datetime
				+ ", province=" + province + ", city=" + city + ", area=" + area + ", areaGrade=" + areaGrade
				+ ", companyProvince=" + companyProvince + ", companyCity=" + companyCity + ", companyArea="
				+ companyArea + ", source=" + source + ", content=" + content + ", url=" + url + ", shortUrl="
				+ shortUrl + ", sign=" + sign + ", productType=" + productType + ", issueCompany=" + issueCompany
				+ ", nature=" + nature + ", status=" + status + ", onShow=" + onShow + ", top=" + top + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", browsecount=" + browsecount + ", field=" + field
				+ ", applyStatus=" + applyStatus + ", originalDate=" + originalDate + ", forward=" + forward
				+ ", collection=" + collection + ", industry=" + industry + "]";
	}
	public PolicydigVo getPv() {
		return pv;
	}
	public void setPv(PolicydigVo pv) {
		this.pv = pv;
	}
	public String getIndustryFirst() {
		return industryFirst;
	}
	public String getProduct() {
		return product;
	}
	public String getProductFirst() {
		return productFirst;
	}
	public void setIndustryFirst(String industryFirst) {
		this.industryFirst = industryFirst;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setProductFirst(String productFirst) {
		this.productFirst = productFirst;
	}
	public void setFirst(){
		this.tecnologyFirst=StringUtils.isBlank(getTecnology())?"":getTecnology().split(",")[0];
		this.productFirst=StringUtils.isBlank(getProduct())?"":getProduct().split(",")[0];
		this.industryFirst=StringUtils.isBlank(getIndustry())?"":getIndustry().split(",")[0];
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBeginDates() {
		return beginDates;
	}
	public String getEndDates() {
		return endDates;
	}
	public void setBeginDates(String beginDates) {
		this.beginDates = beginDates;
	}
	public void setEndDates(String endDates) {
		this.endDates = endDates;
	}
}
