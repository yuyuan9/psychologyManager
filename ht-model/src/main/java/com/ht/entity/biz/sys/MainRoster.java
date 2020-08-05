package com.ht.entity.biz.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/*
 * 科技型中小企业名单主表
 */
@TableName(value = "t_smalltech_mainroster")
public class MainRoster {
	@TableId(type=IdType.AUTO)
	private int id;

	private  String filename;

	private String province;//省

	private String year; //年份

	private String batch;//批次

	private String  type;//类型

	private int  num; //统计保存到数据库的条数
	private int  blag;
	public String getFilename() {
		return filename;
	}

	public int getBlag() {
		return blag;
	}

	public void setBlag(int blag) {
		this.blag = blag;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
