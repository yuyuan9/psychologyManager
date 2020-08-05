package com.ht.entity.biz.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 微信智能推送
 * @author lhy
 *
 */
@TableName(value = "weixin_push")
public class Push {
	/**
	 * id(自动生成)
	 */
	@TableId(type=IdType.UUID)
	private String id;
	/**
	 * 	企业名称
	 */
	private String enterprisename;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 详细地址
	 */
	private String detailaddress;
	/**
	 * 联系人手机号
	 */
	private String phone;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 登录人手机号
	 */
	@TableField(value = "createId")

	private String createId;
	/**
	 * 创建日期（自动生成）
	 */

	@TableField(value = "createData")

	private Date createData;
	
	/**
	 * 注册类型
	 */
	@TableField(value = "regType")
	private String regType;
	
	/**
	 * 
	 *感兴趣内容
	 */
	@TableField(value = "interesteContext")
	private String interesteContext;
	
	
	
	public String getRegType() {
		return regType;
	}
	public void setRegType(String regType) {
		this.regType = regType;
	}
	public String getInteresteContext() {
		return interesteContext;
	}
	public void setInteresteContext(String interesteContext) {
		this.interesteContext = interesteContext;
	}
	public Date getCreateData() {
		return createData;
	}
	public void setCreateData(Date createData) {
		this.createData = createData;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnterprisename() {
		return enterprisename;
	}
	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
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
	public String getDetailaddress() {
		return detailaddress;
	}
	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
}
