package com.ht.entity.biz.product;

import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
//下单人信息
@TableName("t_order_user_comp")
public class OrderUserComp extends BaseEntity{
	@TableField(value="companyName")
	private String companyName;//企业名称
	private String province;//省
	private String city;//市
	private String area;//区
	private String linkman;//联系人
	private String phone;//下单人的手机号
	private String remark;//备注
	private Boolean defaults=false;//是否默认
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
	public String getLinkman() {
		return linkman;
	}
	public String getPhone() {
		return phone;
	}
	public String getRemark() {
		return remark;
	}
	public Boolean getDefaults() {
		return defaults;
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
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setDefaults(Boolean defaults) {
		this.defaults = defaults;
	}
}
