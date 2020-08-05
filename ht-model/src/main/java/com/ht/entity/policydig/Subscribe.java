package com.ht.entity.policydig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_subscribe")
public class Subscribe extends BaseEntity{
	@TableField(value="region")
	private String region;//订阅地区集合
	@TableField(value="nature")
	private String nature;//性质分类集合
	@TableField(value="productType")
	private String productType;//政策分类集合
	@TableField(value="tecnology")
	private String tecnology;//技术领域集合
	@TableField(value="vip")
	private Integer vip=0;//是否是vip
	public String getRegion() {
		return region;
	}
	public String getNature() {
		return nature;
	}
	public String getProductType() {
		return productType;
	}
	public String getTecnology() {
		return tecnology;
	}
	public Integer getVip() {
		return vip;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public void setTecnology(String tecnology) {
		this.tecnology = tecnology;
	}
	public void setVip(Integer vip) {
		this.vip = vip;
	}
}
