package com.ht.entity.biz.solr.companypolicy;

import com.baomidou.mybatisplus.annotation.TableField;

//企业信息
public class CompNew {
	private Integer id=0;
	private String companyname;//企业名称
	private String number;//社会信用代码
	private String orgcode;//机构代码
	private String regnumber;//注册编码
	private String managestatus;//运营状态
	private String companytype;//公司类型
	private String companycreatedate;//成立日期
	private String legalperson;//企业法人
	private String businessdate;//经营日期
	private String regcapital;//注册金额
	private String permitdate;//批准日期
	private String regauth;//登记处
	private String addr;//详细地址
	private String managesscope;//商业范围（经营范围）
	private String weburl;//访问地址
	private String province;//省份
	private String city;//城市
	private String area;//地区
	private Integer isPatent=0;
	private Integer isqcc=0;
	private Integer iscopyright=0;
	private String tel;//联系电话
	private String industry;//行业
	private Integer isbrand;
	private Integer repstatus=0;
	private Integer total=0;
	private Integer pageno=0;
	private Integer pindex=0;
	private Integer ztotal=0;
	private Integer issoftware=0;
	private String forwardlooklab;
	private String exhibitionlab;
	private Integer flagClean=0;
	private String email;
	private String website;
	private Integer eyear=0;
	private String high;
	public Integer getId() {
		return id;
	}
	public String getForwardlooklab() {
		return forwardlooklab;
	}
	public String getExhibitionlab() {
		return exhibitionlab;
	}
	public void setForwardlooklab(String forwardlooklab) {
		this.forwardlooklab = forwardlooklab;
	}
	public void setExhibitionlab(String exhibitionlab) {
		this.exhibitionlab = exhibitionlab;
	}
	public String getCompanyname() {
		return companyname;
	}
	public String getNumber() {
		return number;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public String getRegnumber() {
		return regnumber;
	}
	public String getManagestatus() {
		return managestatus;
	}
	public String getCompanytype() {
		return companytype;
	}
	public String getCompanycreatedate() {
		return companycreatedate;
	}
	public String getLegalperson() {
		return legalperson;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	public String getRegcapital() {
		return regcapital;
	}
	public String getPermitdate() {
		return permitdate;
	}
	public String getRegauth() {
		return regauth;
	}
	public String getAddr() {
		return addr;
	}
	public String getManagesscope() {
		return managesscope;
	}
	public String getWeburl() {
		return weburl;
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
	public Integer getIsPatent() {
		return isPatent;
	}
	public Integer getIsqcc() {
		return isqcc;
	}
	public Integer getIscopyright() {
		return iscopyright;
	}
	public String getTel() {
		return tel;
	}
	public String getIndustry() {
		return industry;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFlagClean() {
		return flagClean;
	}
	public String getEmail() {
		return email;
	}
	public String getWebsite() {
		return website;
	}
	public Integer getEyear() {
		return eyear;
	}
	public String getHigh() {
		return high;
	}
	public void setFlagClean(Integer flagClean) {
		this.flagClean = flagClean;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public void setEyear(Integer eyear) {
		this.eyear = eyear;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public void setRegnumber(String regnumber) {
		this.regnumber = regnumber;
	}
	public void setManagestatus(String managestatus) {
		this.managestatus = managestatus;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public void setCompanycreatedate(String companycreatedate) {
		this.companycreatedate = companycreatedate;
	}
	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}
	public void setRegcapital(String regcapital) {
		this.regcapital = regcapital;
	}
	public void setPermitdate(String permitdate) {
		this.permitdate = permitdate;
	}
	public void setRegauth(String regauth) {
		this.regauth = regauth;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public void setManagesscope(String managesscope) {
		this.managesscope = managesscope;
	}
	public void setWeburl(String weburl) {
		this.weburl = weburl;
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
	public void setIsPatent(Integer isPatent) {
		this.isPatent = isPatent;
	}
	public void setIsqcc(Integer isqcc) {
		this.isqcc = isqcc;
	}
	public void setIscopyright(Integer iscopyright) {
		this.iscopyright = iscopyright;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Integer getIsbrand() {
		return isbrand;
	}
	public void setIsbrand(Integer isbrand) {
		this.isbrand = isbrand;
	}
	@Override
	public String toString() {
		return "CompNew [id=" + id + ", companyname=" + companyname + ", number=" + number + ", orgcode=" + orgcode
				+ ", regnumber=" + regnumber + ", managestatus=" + managestatus + ", companytype=" + companytype
				+ ", companycreatedate=" + companycreatedate + ", legalperson=" + legalperson + ", businessdate="
				+ businessdate + ", regcapital=" + regcapital + ", permitdate=" + permitdate + ", regauth=" + regauth
				+ ", addr=" + addr + ", managesscope=" + managesscope + ", weburl=" + weburl + ", province=" + province
				+ ", city=" + city + ", area=" + area + ", isPatent=" + isPatent + ", isqcc=" + isqcc + ", iscopyright="
				+ iscopyright + ", tel=" + tel + ", industry=" + industry + ", isbrand=" + isbrand + "]";
	}
	public Integer getRepstatus() {
		return repstatus;
	}
	public Integer getTotal() {
		return total;
	}
	public Integer getPageno() {
		return pageno;
	}
	public Integer getPindex() {
		return pindex;
	}
	public Integer getZtotal() {
		return ztotal;
	}
	public void setRepstatus(Integer repstatus) {
		this.repstatus = repstatus;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}
	public void setPindex(Integer pindex) {
		this.pindex = pindex;
	}
	public void setZtotal(Integer ztotal) {
		this.ztotal = ztotal;
	}
	public Integer getIssoftware() {
		return issoftware;
	}
	public void setIssoftware(Integer issoftware) {
		this.issoftware = issoftware;
	}
	
}
