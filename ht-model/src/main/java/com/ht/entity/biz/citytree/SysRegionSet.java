package com.ht.entity.biz.citytree;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.entity.base.BaseEntity;

import java.io.Serializable;

@TableName(value="t_sys_region_set")
public class SysRegionSet extends TreeEntity implements Serializable {

	private String name; // 地区名称

	//private String region_id;// 关联区域id
	
	private Boolean active=true;//启用禁用

	private  int sort ;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getName() {
		return name; 
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	

	//public String getRegion_id() {
	//	return region_id;
	//}

	//public void setRegion_id(String region_id) {
	//	this.region_id = region_id;
	//}

}
