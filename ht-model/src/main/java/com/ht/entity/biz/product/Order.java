package com.ht.entity.biz.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
//用户订单
@TableName("t_order")
public class Order extends BaseEntity{
	private String number;//订单编号
	@TableField(exist=false)
	private ProductVo productVo;//产品数据
	@TableField(value="productId")
	private String productId;//产品id
	@TableField(value="productName")
	private String productName;//产品名称(产品信息可能会变化，但用户下过的订单信息不能改变)
	@TableField(value="productPrice")
	private Double productPrice=0d;//产品单价(产品信息可能会变化，但用户下过的订单信息不能改变)
	@TableField(value="productCounts")
	private Integer productCounts=0;//产品数量(产品信息可能会变化，但用户下过的订单信息不能改变)
	@TableField(value="serviceId")
	private String serviceId;//服务商id
	@TableField(value="servicePhone")
	private String servicePhone;//服务商目前手机号
	@TableField(value="serviceLinkman")
	private String serviceLinkman;//服务商联系人
	@TableField(value="serviceCast")
	private Double serviceCast;//服务费用（总价）
	private Integer status=0;//status=0待服务status=1交易完成status=2拒接服务
	@TableField(value="companyName")
	private String companyName;//企业名称
	private String province;//省
	private String city;//市
	private String area;//区
	private String linkman;//联系人
	private String phone;//下单人的手机号
	private String remark;//备注
	private String refuse;//拒接理由
	@TableField(value="userDeleted")
	private Integer userDeleted=0;//(普通用户删除标识0表示未删除1表示已经删除)
	@TableField(value="serviceDeleted")
	private Integer serviceDeleted=0;//(服务商删除标识0表示未删除1表示已经删除)
	public String getNumber() {
		return number;
	}
	public String getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public Integer getProductCounts() {
		return productCounts;
	}
	public String getServiceId() {
		return serviceId;
	}
	public Double getServiceCast() {
		return (productPrice==null?0d:productPrice)*(productCounts==null?0:productCounts);
	}
	public Integer getStatus() {
		return status;
	}
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
	public String getRefuse() {
		return refuse;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public void setProductCounts(Integer productCounts) {
		this.productCounts = productCounts;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public void setServiceCast(Double serviceCast) {
		this.serviceCast = serviceCast;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}
	public ProductVo getProductVo() {
		return productVo;
	}
	public void setProductVo(ProductVo productVo) {
		this.productVo = productVo;
	}
	public String getServicePhone() {
		return servicePhone;
	}
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	public String getServiceLinkman() {
		return serviceLinkman;
	}
	public void setServiceLinkman(String serviceLinkman) {
		this.serviceLinkman = serviceLinkman;
	}
	public Integer getUserDeleted() {
		return userDeleted;
	}
	public Integer getServiceDeleted() {
		return serviceDeleted;
	}
	public void setUserDeleted(Integer userDeleted) {
		this.userDeleted = userDeleted;
	}
	public void setServiceDeleted(Integer serviceDeleted) {
		this.serviceDeleted = serviceDeleted;
	}
}
