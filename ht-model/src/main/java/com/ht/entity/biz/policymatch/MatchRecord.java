package com.ht.entity.biz.policymatch;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import com.ht.entity.policydig.PolicydigVo;

//项目匹配记录
@TableName("t_match_record")
public class MatchRecord extends BaseEntity{
	@TableField(value="companyName")
	private String companyName;//企业名称
	private String province;//省
	private String city;//市
	private String area;//区
	private String phone;//手机号
	@TableField(value="companyField")
	private String companyField;//企业领域
	@TableField(value="scaleScale")
	private String scaleScale;//企业规模
	public String getCompanyName() {
		return companyName;
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
	public String getPhone() {
		return phone;
	}
	public String getCompanyField() {
		return companyField;
	}
	public String getScaleScale() {
		return scaleScale;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setCompanyField(String companyField) {
		this.companyField = companyField;
	}
	public void setScaleScale(String scaleScale) {
		this.scaleScale = scaleScale;
	}
}
