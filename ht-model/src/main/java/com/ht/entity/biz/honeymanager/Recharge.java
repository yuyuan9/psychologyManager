package com.ht.entity.biz.honeymanager;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/*
 * 充值板块
 */
@TableName("t_recharge")
public class Recharge extends BaseEntity {
	//标题
	@TableField(value="title")
	private String title;
	//honey值
	@TableField(value="honey")
	private Integer honey=0;
	//折扣
	@TableField(value="discount")
	private Double discount=1d;
	//原价
	@TableField(value="oldmoney")
	private Double oldmoney=0d;
	//现价
	@TableField(value="newmoney")
	private Double newmoney=0d;
    //状态
	@TableField(value="onlinestatus")
	private Boolean onlinestatus=true;
	//排序
	@TableField(value="sort")
	private Integer sort=0;
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getHoney() {
		return honey;
	}

	public void setHoney(Integer honey) {
		this.honey = honey;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getOldmoney() {
		return oldmoney;
	}

	public void setOldmoney(Double oldmoney) {
		this.oldmoney = oldmoney;
	}

	public Double getNewmoney() {
		return newmoney;
	}

	public void setNewmoney(Double newmoney) {
		this.newmoney = newmoney;
	}

	public Boolean getOnlinestatus() {
		return onlinestatus;
	}

	public void setOnlinestatus(Boolean onlinestatus) {
		this.onlinestatus = onlinestatus;
	}
	
}
