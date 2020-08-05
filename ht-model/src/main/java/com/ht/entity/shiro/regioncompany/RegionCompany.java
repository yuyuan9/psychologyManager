package com.ht.entity.shiro.regioncompany;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/**
 * 區域公司
 * @author jied
 *
 */
@TableName("t_region_company")
public class RegionCompany extends BaseEntity{
	
	private String name;//分公司名称
	
	private String leader;//负责人
	
	private String province;//省
	
	private String city;//市
	
	private String area;//区
	
	private String tel;//联系电话

	private String number; //区域编号

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


}
