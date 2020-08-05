package com.ht.entity.policydig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_customer_info")
public class CustomerInfo extends BaseEntity {
	/*
	 * 客户名称
	 */
	@TableField(value="name")
	private String name;
	/*
	 * 统一信用代码
	 */ 
	@TableField(value="orgcode")
	private String orgcode;
	/* 项目总负责人 */
	@TableField(value="leadingPerson")
	private String leadingPerson;
	
	/* 部门 */
	@TableField(value="deptName")
	private String deptName;
	
	/* 职位 */
	@TableField(value="duty")
	private String duty;
	
	/*
	 * 固定电话
	 */
	@TableField(value="fixedphone")
	private String fixedphone;
	/* 手机号码 */
	@TableField(value="phone")
	private String phone;
	/* QQ号 */
	@TableField(value="qq")
	private String qq;
	/* 微信号 */
	@TableField(value="weixin")
	private String weixin;
	/* 联系邮箱 */
	@TableField(value="linkemail")
	private String linkemail;
	/* 省份 */
	@TableField(value="province")
	private String province;
	/* 市 */
	@TableField(value="city")
	private String city;
	/* 区 */
	@TableField(value="area")
	private String area;
	/* 地址 */
	@TableField(value="addr")
	private String addr;
	/* 技术领域 */
	@TableField(value="field")
	private String field;
	/* 产业领域 */
	@TableField(value="industry")
	private String industry;
	/* 经营范围 */
	@TableField(value="managesscope")
	private String managesscope;
	/*
	 * 0 为未签单  1 为已签订
	 */
	@TableField(value="signOrder")
	private String signOrder="0";
	/*导入id*/
	@TableField(value="customerRecordId")
	private String customerRecordId;
	/*
	 * 状态  0 未过期  1 已过期 （可表示删除）
	 */
	@TableField(value="deleted")
	private String deleted="0";


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLeadingPerson() {
		return leadingPerson;
	}

	public void setLeadingPerson(String leadingPerson) {
		this.leadingPerson = leadingPerson;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getLinkemail() {
		return linkemail;
	}

	public void setLinkemail(String linkemail) {
		this.linkemail = linkemail;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCustomerRecordId() {
		return customerRecordId;
	}

	public void setCustomerRecordId(String customerRecordId) {
		this.customerRecordId = customerRecordId;
	}

	public String getIndustry() {
		return industry;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getDuty() {
		return duty;
	}

	public String getFixedphone() {
		return fixedphone;
	}

	public String getAddr() {
		return addr;
	}

	public String getManagesscope() {
		return managesscope;
	}

	public String getSignOrder() {
		return signOrder;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public void setFixedphone(String fixedphone) {
		this.fixedphone = fixedphone;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setManagesscope(String managesscope) {
		this.managesscope = managesscope;
	}

	public void setSignOrder(String signOrder) {
		this.signOrder = signOrder;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}


	

}
