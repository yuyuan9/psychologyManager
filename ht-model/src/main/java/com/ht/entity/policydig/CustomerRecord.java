package com.ht.entity.policydig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_customer_record")
public class CustomerRecord extends BaseEntity {
	//上传文件名
	@TableField(value="fileName")
	private String fileName;
	//上传数量
	@TableField(value="counts")
	private Integer counts=0;
	//上传人地区
	@TableField(value="addr")
	private String addr;
	@TableField(value="province")
	private String province;//省
	@TableField(value="city")
	private String city;//市
	@TableField(value="area")
	private String area;//区
	//上传人姓名
	@TableField(value="uplaodPerson")
	private String uplaodPerson;
	//联系方式
	@TableField(value="phone")
	private String phone;
	//上传具体情况
	@TableField(value="uploadCon")
	private String uploadCon;
	public String getFileName() {
		return fileName;
	}
	public Integer getCounts() {
		return counts;
	}
	public String getAddr() {
		return addr;
	}
	public String getUplaodPerson() {
		return uplaodPerson;
	}
	public String getPhone() {
		return phone;
	}
	public String getUploadCon() {
		return uploadCon;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public void setUplaodPerson(String uplaodPerson) {
		this.uplaodPerson = uplaodPerson;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setUploadCon(String uploadCon) {
		this.uploadCon = uploadCon;
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
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setArea(String area) {
		this.area = area;
	}
}
