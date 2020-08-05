package com.ht.entity.policydig;

import org.apache.commons.lang.StringUtils;

public class PolicydigVo {
	private String tecnologyName;
	private String[] tecnologyNames;
	private String productName;
	private String[] productNames;
	private String industryName;
	private String[] industryNames;
	private String applyScale;
	private String[] applyScales;
	public String getProductName() {
		return productName;
	}
	public String[] getProductNames() {
		return productNames;
	}
	public String[] getIndustryNames() {
		return industryNames;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setProductNames(String[] productNames) {
		this.productNames = productNames;
	}
	public void setIndustryNames(String[] industryNames) {
		this.industryNames = industryNames;
	}
	public String getTecnologyName() {
		return tecnologyName;
	}
	public String[] getTecnologyNames() {
		return tecnologyNames;
	}
	public void setTecnologyName(String tecnologyName) {
		this.tecnologyName = tecnologyName;
	}
	public void setTecnologyNames(String[] tecnologyNames) {
		this.tecnologyNames = tecnologyNames;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public PolicydigVo(){
		
	}
	public PolicydigVo(String tecnologyName,String productName,String industryName,String applyScale){
		if(StringUtils.isNotBlank(tecnologyName)){
			this.tecnologyName = tecnologyName;
			this.tecnologyNames =tecnologyName.split("@");
		}
		if(StringUtils.isNotBlank(productName)){
			this.productName = productName;
			this.productNames =productName.split("@");
		}
		if(StringUtils.isNotBlank(industryName)){
			this.industryName = industryName;
			this.industryNames =industryName.split("@");
		}
		if(StringUtils.isNotBlank(applyScale)){
			this.applyScale = applyScale;
			this.applyScales =applyScale.split("@");
		}
	}
	public String getApplyScale() {
		return applyScale;
	}
	public String[] getApplyScales() {
		return applyScales;
	}
	public void setApplyScale(String applyScale) {
		this.applyScale = applyScale;
	}
	public void setApplyScales(String[] applyScales) {
		this.applyScales = applyScales;
	}
}
