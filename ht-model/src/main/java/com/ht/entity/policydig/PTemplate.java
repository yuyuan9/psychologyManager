package com.ht.entity.policydig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/*
 * 模板，短信，邮件
 */
@TableName("t_p_template")
public class PTemplate extends BaseEntity{
	/*
	 * 模板名称
	 */
	@TableField(value="name")
	private String name;
	/*
	 * 0 : 邮箱    1： 短信
	 */
	@TableField(value="type")
	private Integer type=0;
	/*
	 * 模板内容
	 */
	@TableField(value="content")
	private String content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	

}
