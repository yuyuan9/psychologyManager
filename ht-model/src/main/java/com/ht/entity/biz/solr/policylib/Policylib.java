package com.ht.entity.biz.solr.policylib;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/**
 * 政策库
 * 
 * @author jied
 *
 */
@TableName("t_policy_lib")
public class Policylib extends BaseEntity {
	
	/*
	 * 年份
	 */
	@TableField(value="year")
	private String year;
	/*
	 * 文件名
	 */
	@TableField(value="fileno")
	private String fileno;
	/*
	 * 政策名称
	 */
	@TableField(value="projecname")
	private String projecname;

	/*
	 * 专项
	 */
	@TableField(value="specialmum")
	private String specialmum;
	/*
	 * 管理办法
	 */
	@TableField(value="managerway")
	private String managerway;
	/*
	 * 支持对象
	 */
	@TableField(value="supportobj")
	private String supportobj;
	/*
	 * 申报条件
	 */
	@TableField(value="applyterm")
	private String applyterm;
	/*
	 * 经费及配套
	 */
	@TableField(value="fundfacilitie")
	private String fundfacilitie;
	/*
	 * 申报时间
	 */
	@TableField(value="endtime")
	private String endtime;
	/*
	 * 地区
	 */
	@TableField(value="region")
	private String region;
	
	@TableField(value="province")
	private String province;

	@TableField(value="city")
	private String city;

	@TableField(value="area")
	private String area;
	/*
	 * 主管部门（科技、经贸、发改）
	 */
	@TableField(value="chargedept")
	private String chargedept;
	/*
	 * 联系人
	 */
	@TableField(value="linkman")
	private String linkman;
	/*
	 * 备注(网址链接)
	 */
	@TableField(value="remark")
	private String remark;

	//技术领域
	@TableField(value="technology")
	private String technology;
	//@TableField(exist=false)
	//private String[] technologys;
	//产业分类
	@TableField(value="industry")
	private String industry;
	
	@TableField(value="hisImportId")
	private String hisImportId;
	
	@TableField(value="source")
	private String source;
	
	@TableField(exist=false)
	private String str;
	
	@TableField(exist=false)
	private String policyDigId;
	
	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getProjecname() {
		return projecname;
	}

	public void setProjecname(String projecname) {
		this.projecname = projecname;
	}

	public String getSpecialmum() {
		return specialmum;
	}

	public void setSpecialmum(String specialmum) {
		this.specialmum = specialmum;
	}

	public String getManagerway() {
		return managerway;
	}

	public void setManagerway(String managerway) {
		this.managerway = managerway;
	}

	public String getSupportobj() {
		return supportobj;
	}

	public void setSupportobj(String supportobj) {
		this.supportobj = supportobj;
	}

	public String getApplyterm() {
		return applyterm;
	}

	public void setApplyterm(String applyterm) {
		this.applyterm = applyterm;
	}

	public String getFundfacilitie() {
		return fundfacilitie;
	}

	public void setFundfacilitie(String fundfacilitie) {
		this.fundfacilitie = fundfacilitie;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getChargedept() {
		return chargedept;
	}

	public void setChargedept(String chargedept) {
		this.chargedept = chargedept;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPolicyDigId() {
		return policyDigId;
	}

	public void setPolicyDigId(String policyDigId) {
		this.policyDigId = policyDigId;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHisImportId() {
		return hisImportId;
	}

	public void setHisImportId(String hisImportId) {
		this.hisImportId = hisImportId;
	}

	public String getTechnology() {
		return technology;
	}

	public String getIndustry() {
		return industry;
	}


	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getStr() {
		return (StringUtils.isBlank(this.getProjecname())?"":this.getProjecname())
				+(StringUtils.isBlank(this.getSpecialmum())?"":this.getSpecialmum())
				+(StringUtils.isBlank(this.getManagerway())?"":this.getManagerway())
				+(StringUtils.isBlank(this.getSupportobj())?"":this.getSupportobj())
				+(StringUtils.isBlank(this.getApplyterm())?"":this.getApplyterm());
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
