package com.ht.entity.shiro.usertype;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import com.ht.entity.shiro.SysUser;

/**
 * 企业用户关联信息类
 * @author jied
 *
 */
@TableName(value="t_company_user")
public class CompanyUser extends BaseEntity{

	
	private String linkman;//联系人
	
	private String companyname;//企业名称
	
	private String compregcode;//企业统一信用代码
	
	private String province;//省
	
	private String city;//市
	
	private String area;//区
	
	private String addr;//详细地址
	
	private String regdate;//注册时间
	
	private String techfield;//技术领域
	
	private String weburl;//企业网站
	
	private String remark;//简介
	
	private String mainproduct;//主营产品
	
	
	private String businesspath;//营业执照文件
	
	private String billpath;//附件

	@TableField(value="bankName")
	private String bankName;//银行卡
	@TableField(value="compregcodePath")
	private String compregcodePath;//统一信用代码
	@TableField(value="linkmanJustPath")
	private String linkmanJustPath;//联系人身份证正面
	@TableField(value="linkmanBackPath")
	private String linkmanBackPath;//联系人身份证反面
	@TableField(value="OpenAccountPath")
	private String OpenAccountPath;//开户许可证
	@TableField(value="invoicePath")
	private String invoicePath;//企业开票资料
	

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTechfield() {
		return techfield;
	}

	public void setTechfield(String techfield) {
		this.techfield = techfield;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompregcode() {
		return compregcode;
	}

	public void setCompregcode(String compregcode) {
		this.compregcode = compregcode;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getMainproduct() {
		return mainproduct;
	}

	public void setMainproduct(String mainproduct) {
		this.mainproduct = mainproduct;
	}

	public String getBusinesspath() {
		return businesspath;
	}

	public void setBusinesspath(String businesspath) {
		this.businesspath = businesspath;
	}

	public String getBankName() {
		return bankName;
	}

	public String getCompregcodePath() {
		return compregcodePath;
	}

	public String getLinkmanJustPath() {
		return linkmanJustPath;
	}

	public String getLinkmanBackPath() {
		return linkmanBackPath;
	}

	public String getOpenAccountPath() {
		return OpenAccountPath;
	}

	public String getInvoicePath() {
		return invoicePath;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setCompregcodePath(String compregcodePath) {
		this.compregcodePath = compregcodePath;
	}

	public void setLinkmanJustPath(String linkmanJustPath) {
		this.linkmanJustPath = linkmanJustPath;
	}

	public void setLinkmanBackPath(String linkmanBackPath) {
		this.linkmanBackPath = linkmanBackPath;
	}

	public void setOpenAccountPath(String openAccountPath) {
		OpenAccountPath = openAccountPath;
	}

	public void setInvoicePath(String invoicePath) {
		this.invoicePath = invoicePath;
	}

	public String getBillpath() {
		return billpath;
	}

	public void setBillpath(String billpath) {
		this.billpath = billpath;
	}
}
