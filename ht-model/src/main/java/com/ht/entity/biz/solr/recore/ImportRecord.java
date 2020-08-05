package com.ht.entity.biz.solr.recore;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_import_record")
public class ImportRecord extends BaseEntity{
	//导入文件名
	@TableField(value="fileName")
	private String fileName;
	//导入表名
	@TableField(value="tableName")
	private String tableName;
	//导入总条数
	@TableField(value="allCounts")
	private Integer allCounts=0;
	//重复条数
	@TableField(value="resetCounts")
	private Integer resetCounts=0;
	//成功条数
	@TableField(value="sucdessCounts")
	private Integer sucdessCounts=0;
	//具体详情
	@TableField(value="remark")
	private String remark;
	//联系方式
	@TableField(value="phone")
	private String phone;
	//区域
	@TableField(value="province")
	private String province;
	@TableField(value="city")
	private String city;
	@TableField(value="area")
	private String area;
	//导入库
	@TableField(value="importLocal")
	private String importLocal;
	
	public String getFileName() {
		return fileName;
	}
	public String getTableName() {
		return tableName;
	}
	public Integer getAllCounts() {
		return allCounts;
	}
	public Integer getResetCounts() {
		return resetCounts;
	}
	public Integer getSucdessCounts() {
		return sucdessCounts;
	}
	public String getRemark() {
		return remark;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setAllCounts(Integer allCounts) {
		this.allCounts = allCounts;
	}
	public void setResetCounts(Integer resetCounts) {
		this.resetCounts = resetCounts;
	}
	public void setSucdessCounts(Integer sucdessCounts) {
		this.sucdessCounts = sucdessCounts;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhone() {
		return phone;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getArea() {
		return area;
	}
	public String getImportLocal() {
		return importLocal;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setImportLocal(String importLocal) {
		this.importLocal = importLocal;
	}
}
