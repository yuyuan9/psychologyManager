package com.ht.entity.shiro.usertype;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/**
 * 企业银行信息
 * @author jied
 *
 */
@TableName(value="t_company_bank")
public class CompanyBank extends BaseEntity {
	
	private String companyuserid;//企业用户id<外键>
	
	private String name;//银行名称
	
	private String no;//银行账号
	
	private String payee;//收款人

	

	public String getCompanyuserid() {
		return companyuserid;
	}

	public void setCompanyuserid(String companyuserid) {
		this.companyuserid = companyuserid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	

}
