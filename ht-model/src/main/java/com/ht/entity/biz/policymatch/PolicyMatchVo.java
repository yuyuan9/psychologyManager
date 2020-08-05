package com.ht.entity.biz.policymatch;

import org.apache.commons.lang.StringUtils;

public class PolicyMatchVo {
	private String applyField1;
	private String[] applyField1s;
	private String applyField2;
	private String[] applyField2s;
	private String scaleCode;
	private String[] scaleCodes;
	private String applyScale1;
	private String[] applyScale1s;
	private String applyScale2;
	private String[] applyScale2s;
	private String applyScale3;
	private String[] applyScale3s;
	
	public PolicyMatchVo(){
		
	}
	
	public PolicyMatchVo(String applyField1,String applyField2,String scaleCode,String applyScale1,String applyScale2,String applyScale3){
		this.applyField1=applyField1;
		this.applyField2=applyField2;
		this.scaleCode=scaleCode;
		this.applyScale1=applyScale1;
		this.applyScale2=applyScale2;
		this.applyScale3=applyScale3;
	}
	
	public String getApplyField1() {
		return applyField1;
	}
	public String getApplyField2() {
		return applyField2;
	}
	public String getScaleCode() {
		return scaleCode;
	}
	public String getApplyScale1() {
		return applyScale1;
	}
	public String getApplyScale2() {
		return applyScale2;
	}
	public String getApplyScale3() {
		return applyScale3;
	}
	public void setApplyField1(String applyField1) {
		this.applyField1 = applyField1;
	}
	public void setApplyField2(String applyField2) {
		this.applyField2 = applyField2;
	}
	public void setScaleCode(String scaleCode) {
		this.scaleCode = scaleCode;
	}
	public void setApplyScale1(String applyScale1) {
		this.applyScale1 = applyScale1;
	}
	public void setApplyScale2(String applyScale2) {
		this.applyScale2 = applyScale2;
	}
	public void setApplyScale3(String applyScale3) {
		this.applyScale3 = applyScale3;
	}
	public String[] getApplyField1s() {
		if(StringUtils.isNotBlank(applyField1)){
			applyField1s=StringUtils.split(applyField1, ",");
		}
		return applyField1s;
	}
	public String[] getApplyField2s() {
		if(StringUtils.isNotBlank(applyField2)){
			applyField2s=StringUtils.split(applyField2, ",");
		}
		return applyField2s;
	}
	public String[] getScaleCodes() {
		if(StringUtils.isNotBlank(scaleCode)){
			scaleCodes=StringUtils.split(scaleCode, ",");
		}
		return scaleCodes;
	}
	public String[] getApplyScale1s() {
		if(StringUtils.isNotBlank(applyScale1)){
			applyScale1s=StringUtils.split(applyScale1, ",");
		}
		return applyScale1s;
	}
	public String[] getApplyScale2s() {
		if(StringUtils.isNotBlank(applyScale2)){
			applyScale2s=StringUtils.split(applyScale2, ",");
		}
		return applyScale2s;
	}
	public String[] getApplyScale3s() {
		if(StringUtils.isNotBlank(applyScale3)){
			applyScale3s=StringUtils.split(applyScale3, ",");
		}
		return applyScale3s;
	}
	public void setApplyField1s(String[] applyField1s) {
		this.applyField1s = applyField1s;
	}
	public void setApplyField2s(String[] applyField2s) {
		this.applyField2s = applyField2s;
	}
	public void setScaleCodes(String[] scaleCodes) {
		this.scaleCodes = scaleCodes;
	}
	public void setApplyScale1s(String[] applyScale1s) {
		this.applyScale1s = applyScale1s;
	}
	public void setApplyScale2s(String[] applyScale2s) {
		this.applyScale2s = applyScale2s;
	}
	public void setApplyScale3s(String[] applyScale3s) {
		this.applyScale3s = applyScale3s;
	}
}
