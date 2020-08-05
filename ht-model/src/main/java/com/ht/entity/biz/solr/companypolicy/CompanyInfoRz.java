package com.ht.entity.biz.solr.companypolicy;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
@TableName("t_company_info_rz")
public class CompanyInfoRz {
	private String url;
	private String companyname;//企业名称
	private String companynameold;
	private String number;//证书编号
	private String type;//认定类型
	private String year;//认定时间
	private String province;//省
	private String city;//市
	private String area;
	private String batch;//批次
	private String time;
	private String newhightid;
	private Integer hasnew=0;
	private String id;
	private String regioncode;
	private String companyid;
	private String createid;
	private String remark;
	private Integer flag=0;
	private Integer cleanflag=0;
	private Date createdate;
	private Date lastmodified;
	public String getUrl() {
		return url;
	}
	public String getCompanyname() {
		return companyname;
	}
	public String getCompanynameold() {
		return companynameold;
	}
	public String getNumber() {
		return number;
	}
	public String getType() {
		return type;
	}
	public String getYear() {
		return year;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getArea() {
		return area;
	}
	public String getBatch() {
		return batch;
	}
	public String getTime() {
		return time;
	}
	public String getNewhightid() {
		return newhightid;
	}
	public Integer getHasnew() {
		return hasnew;
	}
	public String getId() {
		return id;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public String getCompanyid() {
		return companyid;
	}
	public String getCreateid() {
		return createid;
	}
	public String getRemark() {
		return remark;
	}
	public Integer getFlag() {
		return flag;
	}
	public Integer getCleanflag() {
		return cleanflag;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public Date getLastmodified() {
		return lastmodified;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public void setCompanynameold(String companynameold) {
		this.companynameold = companynameold;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setNewhightid(String newhightid) {
		this.newhightid = newhightid;
	}
	public void setHasnew(Integer hasnew) {
		this.hasnew = hasnew;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public void setCleanflag(Integer cleanflag) {
		this.cleanflag = cleanflag;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	
}
