package com.ht.entity.biz.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/*
 * 错误记录
 */
@TableName(value = "t_smalltech_erroinfo")
public class Erroinfo {
	@TableId(type=IdType.AUTO)
	private int id;

	private int rosertid;//外键

	private String num; //错误编号

	private String name;//文件名称

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRosertid() {
		return rosertid;
	}

	public void setRosertid(int rosertid) {
		this.rosertid = rosertid;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
