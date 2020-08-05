package com.ht.entity.biz.solr.companypolicy;
/*
 * 商标信息
 */
public class CompBrand {
	private Integer id=0;
	private String img;//商标图片
	private String brandName;//商标名称
	private String statu;//状态
	private String applicationTime;//申请时间
	private String regNumber;//注册号
	private String type;//注册类型
	private Integer comId=0;//对应公司
	private Integer pagesize=0;
	private String compname;
	public Integer getId() {
		return id;
	}
	public String getImg() {
		return img;
	}
	public String getBrandName() {
		return brandName;
	}
	public String getStatu() {
		return statu;
	}
	public String getApplicationTime() {
		return applicationTime;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public String getType() {
		return type;
	}
	public Integer getComId() {
		return comId;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setComId(Integer comId) {
		this.comId = comId;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public String getCompname() {
		return compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
}
