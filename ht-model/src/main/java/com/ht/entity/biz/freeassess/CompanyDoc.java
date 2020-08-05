package com.ht.entity.biz.freeassess;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ht.entity.base.BaseEntity;
import com.ht.entity.biz.file.TUploadFile;

public class CompanyDoc extends BaseEntity{
	
	@TableField(value="companyOrgcode")
	private String companyOrgcode;
	
	@TableField(value="companyDocPath")
	private String companyDocPath;
	
	@TableField(exist=false)
	private TUploadFile tUploadFile;
	public String getCompanyOrgcode() {
		return companyOrgcode;
	}
	public String getCompanyDocPath() {
		return companyDocPath;
	}
	public void setCompanyOrgcode(String companyOrgcode) {
		this.companyOrgcode = companyOrgcode;
	}
	public void setCompanyDocPath(String companyDocPath) {
		this.companyDocPath = companyDocPath;
	}
	public TUploadFile gettUploadFile() {
		return tUploadFile;
	}
	public void settUploadFile(TUploadFile tUploadFile) {
		this.tUploadFile = tUploadFile;
	}
}
