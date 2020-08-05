package com.ht.entity.biz.catalog;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/*
 * 技术领域等层级目录
 */
@TableName("t_catalog")
public class Catalog {
	@TableField(value="id")
	@TableId(type=IdType.AUTO)
	private Integer id;
	@TableField(value="name")
	private String name;
	@TableField(value="value")
	private String value;
	@TableField(value="type")
	private String type;
	@TableField(value="pname")
	private String pname;
	@TableField(value="grade")
	private String grade;
	@TableField(exist=false)
	private List<Catalog> list;
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public String getType() {
		return type;
	}
	public String getGrade() {
		return grade;
	}
	public List<Catalog> getList() {
		return list;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setList(List<Catalog> list) {
		this.list = list;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Catalog [id=" + id + ", name=" + name + ", value=" + value + ", type=" + type + ", pname=" + pname
				+ ", grade=" + grade + ", list=" + list + "]";
	}
}
