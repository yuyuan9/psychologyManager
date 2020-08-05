package com.ht.commons.support.tree.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 树的父类
 * 
 * @author jied 2019年5月13日
 */
public class TreeEntity {

	@TableId(type=IdType.AUTO)
	private Integer id;

	private Integer pid;

	@TableField(exist = false)
	private List<TreeEntity> children;
	@TableField(exist = false)
    private List<String> flag;
	private String createid;// 创建人

	private Date createdate;// 创建时间
	private Date lastmodified;// 最后修改时间

	public List<String> getFlag() {
		return flag;
	}

	public void setFlag(List<String> flag) {
		this.flag = flag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public List<TreeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<TreeEntity> children) {
		this.children = children;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	

}
