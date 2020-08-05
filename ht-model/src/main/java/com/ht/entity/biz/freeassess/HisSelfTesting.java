package com.ht.entity.biz.freeassess;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 高企自评测试表（历史表）
 * 
 * @author jied
 *
 */
@TableName("t_hisselftesting")
public class HisSelfTesting extends Testing {
	@TableField(exist=false)
	private String type = TestType.SELF_TEST.name();

	// 知识产品评分
	@TableField(value="intellectualProperty")
	private double intellectualProperty = 0d;
	
	@TableField(value="intellectualPropertyGrade")
	private String intellectualPropertyGrade;

	// 成果转化评分
	@TableField(value="resultTransformation")
	private double resultTransformation = 0d;
	
	@TableField(value="resultTransformationGrade")
	private String resultTransformationGrade;

	// 研究开发评分
	@TableField(value="researchDevelopment")
	private double researchDevelopment = 0d;
	
	@TableField(value="researchDevelopmentGrade")
	private String researchDevelopmentGrade;

	// 总资产评分
	@TableField(value="totalAssets")
	private double totalAssets = 0d;
	
	@TableField(value="totalAssetsGrade")
	private String totalAssetsGrade;
	
	@TableField(value="total")
	private double total = 0d;

	// 结论
	@TableField(value="result")
	private String result;
	// 建议
	@TableField(value="suggest")
	private String suggest;
	// 档次
	@TableField(value="grade")
	private String grade;
	/*
	 * 上传文档路径
	 */
	@TableField(value="path")
	private String path;
	/*
	 * 文档关联集合
	 */
	@TableField(exist=false)
	private List<CompanyDoc> listcdoc;
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	// 申报建议
	@TableField(value="applySuggest")
	private String applySuggest;
	//市高评估结果
	@TableField(exist=false)
	private String highCity;
	 
	// 建议价格
	@TableField(value="priceSuggest")
	private Double priceSuggest=0d;
	
	@TableField(exist=false)
	private String scienceResult;

	// 外键，是自测，还是企业评测id
	@TableField(value="testId")
	private String testId;
	
	/*
	 * 是否发送消息
	 */
	@TableField(exist=false)
	private Boolean sendMsg=false;
	
	
	//=======苏州辅助字段不写入数据库=========
	@TableField(exist=false)
	private String szmsg;//一段话提取
	
	@TableField(exist=false)
	private String sztable;//表格返回
	
	
	/*
	 * 评估结果新增字段
	 */
	//时间成立
	@TableField(exist=false)
	private String yearToString;
	//知识产权
	@TableField(exist=false)
	private String nologyToString;
	//技术领域
	@TableField(exist=false)
	private String techToString;
	//人员构成
	@TableField(exist=false)
	private String personToString;
	//研发费用
	@TableField(exist=false)
	private String costToString;
	//高品收入
	@TableField(exist=false)
	private String incomeToString;
	//合规运营
	@TableField(exist=false)
	private String compopToString;
	//是否暂存 1暂存 0不是暂存
	@TableField(value="temporary")
	private Integer temporary=0;
	public String getSzmsg() {
		return szmsg;
	}

	public String getSztable() {
		return sztable;
	}

	public void setSztable(String sztable) {
		this.sztable = sztable;
	}

	public void setSzmsg(String szmsg) {
		this.szmsg = szmsg;
	}

	public Boolean getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(Boolean sendMsg) {
		this.sendMsg = sendMsg;
	}

	/**
	 * 总分数
	 * 
	 * @return
	 */
	public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getIntellectualProperty() {
		return intellectualProperty;
	}

	public void setIntellectualProperty(double intellectualProperty) {
		this.intellectualProperty = intellectualProperty;
	}

	public double getResultTransformation() {
		return resultTransformation;
	}

	public void setResultTransformation(double resultTransformation) {
		this.resultTransformation = resultTransformation;
	}

	public double getResearchDevelopment() {
		return researchDevelopment;
	}

	public void setResearchDevelopment(double researchDevelopment) {
		this.researchDevelopment = researchDevelopment;
	}

	public double getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getApplySuggest() {
		return applySuggest;
	}

	public void setApplySuggest(String applySuggest) {
		this.applySuggest = applySuggest;
	}

	public Double getPriceSuggest() {
		return priceSuggest;
	}

	public void setPriceSuggest(Double priceSuggest) {
		this.priceSuggest = priceSuggest;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIntellectualPropertyGrade() {
		return intellectualPropertyGrade;
	}

	public String getYearToString() {
		return yearToString;
	}

	public String getNologyToString() {
		return nologyToString;
	}

	public String getTechToString() {
		return techToString;
	}

	public String getPersonToString() {
		return personToString;
	}

	public String getCostToString() {
		return costToString;
	}

	public String getIncomeToString() {
		return incomeToString;
	}

	public void setYearToString(String yearToString) {
		this.yearToString = yearToString;
	}

	public void setNologyToString(String nologyToString) {
		this.nologyToString = nologyToString;
	}

	public void setTechToString(String techToString) {
		this.techToString = techToString;
	}

	public void setPersonToString(String personToString) {
		this.personToString = personToString;
	}

	public void setCostToString(String costToString) {
		this.costToString = costToString;
	}

	public void setIncomeToString(String incomeToString) {
		this.incomeToString = incomeToString;
	}

	public void setIntellectualPropertyGrade(String intellectualPropertyGrade) {
		this.intellectualPropertyGrade = intellectualPropertyGrade;
	}

	public String getResultTransformationGrade() {
		return resultTransformationGrade;
	}

	public void setResultTransformationGrade(String resultTransformationGrade) {
		this.resultTransformationGrade = resultTransformationGrade;
	}

	public String getResearchDevelopmentGrade() {
		return researchDevelopmentGrade;
	}

	public void setResearchDevelopmentGrade(String researchDevelopmentGrade) {
		this.researchDevelopmentGrade = researchDevelopmentGrade;
	}

	public String getTotalAssetsGrade() {
		return totalAssetsGrade;
	}

	public void setTotalAssetsGrade(String totalAssetsGrade) {
		this.totalAssetsGrade = totalAssetsGrade;
	}

	public void setTotal(double total) {
		this.total = total;
	}



	public enum TestType {
		SELF_TEST, COMPANY_TEST
	}


	public String getScienceResult() {
		return scienceResult;
	}

	public void setScienceResult(String scienceResult) {
		this.scienceResult = scienceResult;
	}

	@Override
	public String toString() {
		return "HisSelfTesting [type=" + type + ", intellectualProperty=" + intellectualProperty
				+ ", intellectualPropertyGrade=" + intellectualPropertyGrade + ", resultTransformation="
				+ resultTransformation + ", resultTransformationGrade=" + resultTransformationGrade
				+ ", researchDevelopment=" + researchDevelopment + ", researchDevelopmentGrade="
				+ researchDevelopmentGrade + ", totalAssets=" + totalAssets + ", totalAssetsGrade=" + totalAssetsGrade
				+ ", total=" + total + ", result=" + result + ", suggest=" + suggest + ", grade=" + grade
				+ ", applySuggest=" + applySuggest + ", priceSuggest=" + priceSuggest + ", testId=" + testId + "]";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<CompanyDoc> getListcdoc() {
		return listcdoc;
	}

	public void setListcdoc(List<CompanyDoc> listcdoc) {
		this.listcdoc = listcdoc;
	}

	public String getHighCity() {
		return highCity;
	}

	public void setHighCity(String highCity) {
		this.highCity = highCity;
	}

	public String getCompopToString() {
		return compopToString;
	}

	public void setCompopToString(String compopToString) {
		this.compopToString = compopToString;
	}

	public Integer getTemporary() {
		return temporary;
	}

	public void setTemporary(Integer temporary) {
		this.temporary = temporary;
	}
	
	

}
