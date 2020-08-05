package com.ht.entity.biz.policymatch;

import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
//项目匹配
@TableName("t_policy_match")
public class PolicyMatch extends BaseEntity{
	@TableField(value="projectName")
	private String projectName;//项目名称
	private String type;//项目分类(研发机构，科研立项，科技财税，研发团队，科技成果，知识产权)
	@TableField(value="applyCon")
	private String applyCon;//申报条件
	@TableField(value="applyMean")
	private String applyMean;//申报意义
	@TableField(value="applyField1")
	private String applyField1;//申报领域
	@TableField(value="applyField2")
	private String applyField2;//申报领域
	@TableField(value="applyTime")
	private String applyTime;//申报时间
	private String province;//省
	private String city;//市
	private String area;//区
	@TableField(value="scaleCode")
	private String scaleCode;
	@TableField(value="applyScale1")
	private String applyScale1;//申报企业规模(记录数字)
	@TableField(value="applyScale2")
	private String applyScale2;//申报企业规模
	@TableField(value="applyScale3")
	private String applyScale3;//申报企业规模
	@TableField(value="matchType")
	private Integer matchType=0;//匹配分类(1初步匹配2精细匹配)
	@TableField(value="orderid")
	private Integer orderid=0;//排序（暂不使用）
	//字段特殊处理类
	@TableField(exist=false)
	private PolicyMatchVo pmv;
	public String getProjectName() {
		return projectName;
	}
	public String getType() {
		return type;
	}
	public String getApplyCon() {
		return applyCon;
	}
	public String getApplyMean() {
		return applyMean;
	}
	public String getApplyTime() {
		return applyTime;
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
	public String getApplyScale1() {
		return applyScale1;
	}
	public String getApplyScale2() {
		return applyScale2;
	}
	public Integer getMatchType() {
		return matchType;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setApplyCon(String applyCon) {
		this.applyCon = applyCon;
	}
	public void setApplyMean(String applyMean) {
		this.applyMean = applyMean;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
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
	public void setApplyScale1(String applyScale1) {
		this.applyScale1 = applyScale1;
	}
	public void setApplyScale2(String applyScale2) {
		this.applyScale2 = applyScale2;
	}
	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}
	
	public String getScaleCode() {
		return scaleCode;
	}
	public void setScaleCode(String scaleCode) {
		this.scaleCode = scaleCode;
	}

	public enum Match{
//		scaleCode(applyScale1,applyScale2,applyScale3)
		//sv0("0","","请选择"),
		sv1("1","初创","成立2年以内"),
		sv2("2","小微型","收入＜2000万"),
		sv3("3","中小型","收入≥2000万"),
		sv4("4","中大型","收入≥2亿"),
		sv5("5","大型","收入≥5亿"),
		sv6("6","超大型","收入≥10亿"),
		;
		Match(String as1,String as2,String as3){
			this.as1=as1;
			this.as2=as2;
			this.as3=as3;
		}
		private String as1;
		private String as2;
		private String as3;
		public String getAs1() {
			return as1;
		}
		public String getAs2() {
			return as2;
		}
		public String getAs3() {
			return as3;
		}
		public void setAs1(String as1) {
			this.as1 = as1;
		}
		public void setAs2(String as2) {
			this.as2 = as2;
		}
		public void setAs3(String as3) {
			this.as3 = as3;
		}
		public static void setMatch(PolicyMatch p){
			StringBuffer scaleCode=new StringBuffer();
			StringBuffer subapplyScale1=new StringBuffer();
			StringBuffer subapplyScale3=new StringBuffer();
			if(StringUtils.isNotBlank(p.getApplyScale2())){
				for(String s:p.getApplyScale2().split(",")){
					for(Match m:Match.values()){
						if(m.getAs2().equals(s)){
							scaleCode.append(m.name()+",");
							subapplyScale1.append(m.getAs1()+",");
							subapplyScale3.append(m.getAs3()+",");
						}
					}
				}
				p.setScaleCode(StringUtils.removeEnd(scaleCode.toString(), ","));
				p.setApplyScale1(StringUtils.removeEnd(subapplyScale1.toString(), ","));
				p.setApplyScale3(StringUtils.removeEnd(subapplyScale3.toString(), ","));
			}
		}
	}
	public String getApplyField1() {
		return applyField1;
	}
	public String getApplyField2() {
		return applyField2;
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
	public void setApplyScale3(String applyScale3) {
		this.applyScale3 = applyScale3;
	}
	public PolicyMatchVo getPmv() {
		return pmv;
	}
	public void setPmv(PolicyMatchVo pmv) {
		this.pmv = pmv;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public static void main(String[] args) {
		PolicyMatch p=new PolicyMatch();
		p.setApplyScale2("初创,小微型,中小型,超大型");
		Match.setMatch(p);
		System.out.println(p.getScaleCode());
		System.out.println(p.getApplyScale1());
		System.out.println(p.getApplyScale2());
		System.out.println(p.getApplyScale3());
	}
}
