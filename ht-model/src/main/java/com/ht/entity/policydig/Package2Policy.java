package com.ht.entity.policydig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/*
 * 关联政策信息
 */
@TableName("t_package2_policy")
public class Package2Policy extends BaseEntity{
	@TableField(value="policyDigId")
	private String policyDigId;//政策速递id
	
	@TableField(value="packageId")
	private String packageId;//政策包id

	public String getPolicyDigId() {
		return policyDigId;
	}

	public void setPolicyDigId(String policyDigId) {
		this.policyDigId = policyDigId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	

}
