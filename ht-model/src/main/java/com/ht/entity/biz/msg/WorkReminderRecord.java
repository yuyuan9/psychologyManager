package com.ht.entity.biz.msg;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_work_reminder_record")
public class WorkReminderRecord extends BaseEntity {
	@TableField(value="code")
	private String code;
	@TableField(value="content")
	private String content;
	@TableField(value="userId")
	private String userId;
	/*
	 * 0 没读
	 * 1 已读
	 */
	@TableField(value="read0")
	private Integer read0=0;

	@TableField(value="targetId")
	private String targetId;
	@TableField(value="deleted")
	private Integer deleted=0;
	

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getRead0() {
		return read0;
	}

	public void setRead0(Integer read0) {
		this.read0 = read0;
	}
}
