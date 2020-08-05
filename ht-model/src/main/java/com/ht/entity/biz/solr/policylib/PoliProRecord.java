package com.ht.entity.biz.solr.policylib;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
/*
 * 用户访问记录
 */
@TableName("t_poli_pro_record")
public class PoliProRecord extends BaseEntity{
	
	@TableField(value="ip")
	private String ip;//访问者ip
	
	@TableField(value="interfaceName")
	private String interfaceName;//访问接口
	
	@TableField(value="counts")
	private Double counts=0d;//获取数据总数
	
	@TableField(value="local")
	private String local;//访问库
	
	public String getIp() {
		return ip;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public Double getCounts() {
		return counts;
	}
	public void setCounts(Double counts) {
		this.counts = counts;
	}
}
