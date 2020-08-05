package com.ht.entity.biz.solr.projectlib;

import java.lang.reflect.Field;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.commons.utils.ReflectHelper;
import com.ht.entity.base.BaseEntity;

/**
 * 立项库
 * 
 * @author jied
 *
 */
@TableName("t_project_lib")
public class Projectlib extends BaseEntity implements java.io.Serializable{

	/*
	 * 企业名称
	 */
	
	@TableField(value="companyName")
	private String companyName;

	/*
	 * 项目名称
	 */
	
	@TableField(value="name")
	private String name;
	/*
	 * 类型
	 */
	
	@TableField(value="type")
	private String type;
	
	/*
	 * 专题
	 */
	
	@TableField(value="special")
	private String special;
	
	/*
	 * 合伙人单位
	 */
	
	@TableField(value="unit")
	private String unit;
	
	@TableField(value="project_leader")
	private String project_leader;//项目负责人
	
	@TableField(value="project_number")
	private String project_number;  // 立项编号
	/*
	 * 立项额度
	 */
	
	@TableField(value="quota")
	private String quota;
	
	/*
	 * 主管单位
	 */
	
	@TableField(value="directorUnit")
	private String directorUnit;
	
	/*
	 * 年度立项
	 */
	@TableField(value="yearProject")
	private String yearProject;
	
	//批次
	
	@TableField(value="batch")
	private String batch;
	
	/*
	 * 区域
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
	 * 主营产品
	 */
	
	@TableField(value="mainProduct")
	private String mainProduct;
	
	/*
	 * 所属行业
	 */
	
	@TableField(value="industry")
	private String industry;
	
	/*
	 * 技术领域(一级,二级,三级)
	 */
	
	@TableField(value="firstTechField")
	private String firstTechField;
	
	@TableField(value="secondTechField")
	private String secondTechField;
	
	@TableField(value="thirdTechField")
	private String thirdTechField;
	
	@TableField(value="technicalField")
	private String technicalField;
	
	/*
	 * 是否是博士老客户
	 */
	
	@TableField(value="oldcustomer")
	private String oldcustomer;
	/*
	 * 是否推送
	 */
	@TableField(value="repstatus")
	private String repstatus="0";
	
	@TableField(value="hisImportId")
	private String hisImportId;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

	public String getDirectorUnit() {
		return directorUnit;
	}

	public void setDirectorUnit(String directorUnit) {
		this.directorUnit = directorUnit;
	}

	public String getYearProject() {
		return yearProject;
	}

	public void setYearProject(String yearProject) {
		this.yearProject = yearProject;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
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

	public void setArea(String area) {
		this.area = area;
	}

	public String getFirstTechField() {
		return firstTechField;
	}

	public void setFirstTechField(String firstTechField) {
		this.firstTechField = firstTechField;
	}

	public String getSecondTechField() {
		return secondTechField;
	}

	public void setSecondTechField(String secondTechField) {
		this.secondTechField = secondTechField;
	}

	public String getThirdTechField() {
		return thirdTechField;
	}

	public void setThirdTechField(String thirdTechField) {
		this.thirdTechField = thirdTechField;
	}

	public String getOldcustomer() {
		return oldcustomer;
	}

	public void setOldcustomer(String oldcustomer) {
		this.oldcustomer = oldcustomer;
	}
	
	public String getProject_leader() {
		return project_leader;
	}

	public void setProject_leader(String project_leader) {
		this.project_leader = project_leader;
	}

	public String getProject_number() {
		return project_number;
	}

	public void setProject_number(String project_number) {
		this.project_number = project_number;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getTechnicalField() {
		return technicalField;
	}

	public void setTechnicalField(String technicalField) {
		this.technicalField = technicalField;
	}

	public String getRepstatus() {
		return repstatus;
	}

	public void setRepstatus(String repstatus) {
		this.repstatus = repstatus;
	}

	public String getHisImportId() {
		return hisImportId;
	}

	public void setHisImportId(String hisImportId) {
		this.hisImportId = hisImportId;
	}
	
	public ProjectlibCopy getProjectlibCopy(Projectlib p) throws Exception{
		ProjectlibCopy pc=new ProjectlibCopy();
		Field[] fs=ReflectHelper.getAllField(p.getClass());
		for(Field f:fs){
			ReflectHelper.setValueByFieldName(pc,f.getName(),ReflectHelper.getValueByFieldName(p,f.getName()));
		}
		return pc;
	}
	
	public static void main(String[] args) {
		Field[] fs=ReflectHelper.getAllField(Projectlib.class);
		System.out.println(fs.length);
		for(Field f:fs){
			System.out.println(f.getName());
		}
	}

}
