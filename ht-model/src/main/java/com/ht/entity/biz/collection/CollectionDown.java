package com.ht.entity.biz.collection;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.ht.entity.base.BaseEntity;
import com.ht.entity.biz.library.Library;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * 收藏 和下载 管理类
 * @author jied
 *
 */
@ApiModel(value="collectiondown",description="收藏下载")
@TableName(value="t_collection_down")
public class CollectionDown {

	@TableId(type=IdType.UUID)
	private String id;

	private Date createdate;//创建时间
	@TableField(value="createId")
	private String createid;
	/*
	 * 类型，0 收藏  1 下载  2 微信小程序收藏政策
	 */
	private Integer type=0;
	
	/*
	 * 外键id
	 */
	@TableField(value="targetId")
	private String targetId;
	/*
	 * 类名 反射是收藏的那个表的记录
	 * 默认文库
	 */
	@TableField(value="clazzName")
	private String clazzName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	
}
