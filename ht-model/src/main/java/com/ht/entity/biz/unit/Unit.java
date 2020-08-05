package com.ht.entity.biz.unit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_unit")
public class Unit extends BaseEntity{
	@TableField(value="beforeUnitName")
	private String beforeUnitName;//前端展示
	
	@TableField(value="afterUnitName")
	private String afterUnitName;//后台展示
	
	@TableField(value="alias")
	private String alias;//别称
	
	@TableField(value="region")
	private String region;//所属省市区
	
	@TableField(value="remark")
	private String remark;//备注
	
	public String getBeforeUnitName() {
		return beforeUnitName;
	}
	public String getAlias() {
		return alias;
	}
	public String getRegion() {
		return region;
	}
	public void setBeforeUnitName(String beforeUnitName) {
		this.beforeUnitName = beforeUnitName;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAfterUnitName() {
		return afterUnitName;
	}
	public void setAfterUnitName(String afterUnitName) {
		this.afterUnitName = afterUnitName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
