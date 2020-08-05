package com.ht.entity.biz.solr.companypolicy;

public class CompSoftware {
	private Integer id=0;
	private String softwareName;//软件名称
	private String regNum;//注册号
	private String versionNum;//版本号
	private String category;//类别
	private String approvalDate;//批准日期
	private String abbreviation;
	private Integer compId=0;//对应公司
	private Integer pagesize=0;
	private Integer total=0;
	private String compname;
	public Integer getId() {
		return id;
	}
	public String getSoftwareName() {
		return softwareName;
	}
	public String getRegNum() {
		return regNum;
	}
	public String getVersionNum() {
		return versionNum;
	}
	public String getCategory() {
		return category;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public Integer getCompId() {
		return compId;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public void setCompId(Integer compId) {
		this.compId = compId;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getCompname() {
		return compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
}
