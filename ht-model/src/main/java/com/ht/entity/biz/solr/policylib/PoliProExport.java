package com.ht.entity.biz.solr.policylib;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_poli_pro_export")
public class PoliProExport extends BaseEntity{
	
	@TableField(value="counts")
	private Integer counts=0;//导出数据总数
	
	@TableField(value="local")
	private String local;//导出库

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
}
