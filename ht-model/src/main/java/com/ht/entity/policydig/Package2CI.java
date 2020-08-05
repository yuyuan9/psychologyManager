package com.ht.entity.policydig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/*
 * 关联客户信息
 */
@TableName("t_package2_c_i")
public class Package2CI extends BaseEntity {
	
	@TableField(value="ciId")
	private String ciId;//客户信息id
	
	@TableField(value="packageId")
	private String packageId;//政策包id
	
	@TableField(value="ciIdUrl")
	private String ciIdUrl;//发送链接

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getCiIdUrl() {
		return ciIdUrl;
	}

	public void setCiIdUrl(String ciIdUrl) {
		this.ciIdUrl = ciIdUrl;
	}
	

}
