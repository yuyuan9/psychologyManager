package com.ht.entity.biz.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

//购物车
@TableName("t_shopping_cart")
public class ShoppingCart extends BaseEntity{
	//用户id
	@TableField(value="userId")
	private String userId;
	//产品id
	@TableField(value="productId")
	private String productId;
	//服务商id
	@TableField(value="serviceId")
	private String serviceId;
	//数量
	private Integer counts=1;
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getUserId() {
		return userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
}
