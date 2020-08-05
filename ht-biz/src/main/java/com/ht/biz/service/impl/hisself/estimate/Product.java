package com.ht.biz.service.impl.hisself.estimate;

/**
 * 评估产品类
 * @author jied
 *
 */
public class Product {
	
	//private String histestingid;//评测id
	
	private String type;
	
	private String name;//产品名称
	
	private String foundTime;//成立时间
	
	private String techField;//技术领域
	
	private String persForm;//人员构成
	
	private String rdCost;//研发费用
	
	private String hincome;//高品收入
	
	private String cop;//合规运营
	
	private String intelProp;//知识产权
	
	private String stach;//科技成果
	
	private String rdManag;//研发管理
	
	private String compGrowth;//企业成长性
	
	

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

	public String getFoundTime() {
		return foundTime;
	}

	public void setFoundTime(String foundTime) {
		this.foundTime = foundTime;
	}

	public String getTechField() {
		return techField;
	}

	public void setTechField(String techField) {
		this.techField = techField;
	}

	public String getPersForm() {
		return persForm;
	}

	public void setPersForm(String persForm) {
		this.persForm = persForm;
	}

	public String getRdCost() {
		return rdCost;
	}

	public void setRdCost(String rdCost) {
		this.rdCost = rdCost;
	}

	public String getHincome() {
		return hincome;
	}

	public void setHincome(String hincome) {
		this.hincome = hincome;
	}

	public String getCop() {
		return cop;
	}

	public void setCop(String cop) {
		this.cop = cop;
	}

	public String getIntelProp() {
		return intelProp;
	}

	public void setIntelProp(String intelProp) {
		this.intelProp = intelProp;
	}

	public String getStach() {
		return stach;
	}

	public void setStach(String stach) {
		this.stach = stach;
	}

	public String getRdManag() {
		return rdManag;
	}

	public void setRdManag(String rdManag) {
		this.rdManag = rdManag;
	}

	public String getCompGrowth() {
		return compGrowth;
	}

	public void setCompGrowth(String compGrowth) {
		this.compGrowth = compGrowth;
	}
	
	
	
	@Override
	public String toString() {
		return "Product [type=" + type + ", name=" + name + ", foundTime=" + foundTime + ", techField=" + techField
				+ ", persForm=" + persForm + ", rdCost=" + rdCost + ", hincome=" + hincome + ", cop=" + cop
				+ ", intelProp=" + intelProp + ", stach=" + stach + ", rdManag=" + rdManag + ", compGrowth="
				+ compGrowth + "]";
	}



	//产品类型
	public enum PType{
		number,//序号
		quota,//指标
		gaoqi_introduce,//高企介绍
		peiyu_introduce,//高企培育介绍
		xiaojuren_introduce,//小巨人介绍
		qiye_detail,//企业情况
		
		gaopei_yes_no,//高培对错
		gaoqi_yes_no,//高企对错
		xiaojuren_yes_no,//小巨人对错
		
		huaian_gaopei_yes_no,//淮安 高企培育
		huaian_gaoqi_yes_no,//淮安 高企
		jinhua_shigaorending,//金华市高认定
		jinhua_qiye_detail,//金华市高
		jinhua_shigao_yes_no,//金华市高对错
		jinhua_guogao_yes_no,//金华国高对错
		guangzhou_gaoqirending_yes_no,//广州市高对错
		guangzhou_xiaojuren_ruku,//科技小巨人入库
		guangzhou_qiye_detail,//广州小巨人企业实际
		suzhou_number,//苏州序号
		suzhou_gaoqirending,//苏州高企认定
		suzhou_gaopeiruku,//苏州高培入库
		suzhou_qiye_detail,//苏州企业实际
		suzhou_gaoqi,//苏州高企
		suzhou_gaopei,//苏州高培
		quanguo_gaopei,//全国高培
		;
	}
	
	

}
