package com.ht.entity.shiro.usertype;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;

/**
 * 服务商关联信息类
 * @author jied
 *
 */
@ApiModel(value="服务商信息表",description="服务商信息表")
@TableName("t_sys_service")
public class ServiceProvider extends BaseEntity {

	@TableField(value="netrytype")
	private Integer netrytype;//入驻类型 1：服务机构 2：个人
	@TableField(value="socialcode")
	private String socialcode;//社会统一信用代码
	@TableField(value="servicetype")
	private String servicetype;//服务商类型 1：全部 2：科技服务咨询 3：知识产权代理 4：会计事务所 5：其他
	@TableField(value="banme")
	private String banme;//企业名称(机构入驻)真实名称（个人入驻）
	@TableField(value="netryname")
	private String netryname;//真实名称（个人入驻）
	@TableField(value="pronice")
	private String pronice;//省
	@TableField(value="city")
	private String city ;//市
	@TableField(value="area")
	private String area;//区
	@TableField(value="detailadrr")
	private String detailadrr;//详细地址
	@TableField(value="shopname")
	private String shopname;//服务商铺名称
	@TableField(value="contacts")
	private String contacts;//联系人
	@TableField(value="phone")
	private String phone;//联系人电话
	@TableField(value="customercont")
	private String customercont;//客服联系
	@TableField(value="customerqq")
	private String customerqq;//客服qq
	@TableField(value="businesscope")
	private String businesscope;//业务范围多个用逗号隔开
	@TableField(value="sociepath")
	private String sociepath; //社会统一信用代码
	@TableField(value="zconstacpath")
	private String zconstacpath; //联系人身份证正面
	@TableField(value="fconstacpath")
	private String fconstacpath; //联系人身份证负面
	@TableField(value="stats")
	private Integer stats;//1:待审核2:正常3:保存4:审核拒绝
	@TableField(value="bmoney")
	private Integer bmoney;//保证金
	@TableField(value="serverstars")
	private Integer serverstars;//服务商星级
	@TableField(value="email")
	private String email;//邮箱*/
	@TableField(value="companyurl")
	private String companyurl;//企业网址
	@TableField(value="introduction")
	private String  introduction;//简介 （企业时为企业简介，个人为个人简介）
	@TableField(value="servicecase")
	private String servicecase;//服务案例
	@TableField(value="servicetame")
	private String servicetame;//服务团队
	@TableField(value="serviceflow")
	private String serviceflow;//服务流程
	@TableField(value="serviceensure")
	private String serviceensure;//服务保障
	@TableField(value="other")
	private String other;//其他
	@TableField(value="honor")
	private String honor;//荣誉资质
	@TableField(value="reason")
	private String reason ;//备注(拒绝原因)

	@TableField(value="grade")
	private int grade;//评分
	@TableField(value="browse")
	private int browse;//浏览
	

	@TableField(value="sort")
	private String sort ;//排序号

	@TableField(value="serviceproduct")
	private String serviceproduct ;//服务产品
	@TableField(value="serviceimg")
	private String serviceimg ;//封面图
	@TableField(value="detailedphone")
	private String detailedphone ;//店铺详细图
	@TableField(value="businesslicense")
	private String businesslicense ;//营业执照

	@TableField(value="isagency")
	private Boolean isagency ;//是否相关服务机构 0:否 1：是

	@TableField(value="agencyimg")
	private String agencyimg ;//服务机构证明

//	@TableField(value="ispromote")
//	private String ispromote ;//是否推荐


	@TableField(value="isbond")
	private Boolean isbond ;//是否交保证金0:否 1：是


	private String shopimg ;//店铺 图片

	public String getShopimg() {
		return shopimg;
	}

	public void setShopimg(String shopimg) {
		this.shopimg = shopimg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSort() {
		return sort;
	}

	public String getServiceensure() {
		return serviceensure;
	}

	public void setServiceensure(String serviceensure) {
		this.serviceensure = serviceensure;
	}

	public String getDetailedphone() {
		return detailedphone;
	}

	public void setDetailedphone(String detailedphone) {
		this.detailedphone = detailedphone;
	}

	public String getNetryname() {
		return netryname;
	}

	public void setNetryname(String netryname) {
		this.netryname = netryname;
	}

	public String getBusinesslicense() {
		return businesslicense;
	}

	public void setBusinesslicense(String businesslicense) {
		this.businesslicense = businesslicense;
	}

	public String getServiceproduct() {
		return serviceproduct;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setServiceproduct(String serviceproduct) {
		this.serviceproduct = serviceproduct;
	}



	public String getServiceimg() {
		return serviceimg;
	}



	public void setServiceimg(String serviceimg) {
		this.serviceimg = serviceimg;
	}



	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getBrowse() {
		return browse;
	}

	public void setBrowse(int browse) {
		this.browse = browse;
	}

	public String getCompanyurl() {
		return companyurl;
	}

	public void setCompanyurl(String companyurl) {
		this.companyurl = companyurl;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getServicecase() {
		return servicecase;
	}

	public void setServicecase(String servicecase) {
		this.servicecase = servicecase;
	}

	public String getServicetame() {
		return servicetame;
	}

	public void setServicetame(String servicetame) {
		this.servicetame = servicetame;
	}

	public String getServiceflow() {
		return serviceflow;
	}

	public void setServiceflow(String serviceflow) {
		this.serviceflow = serviceflow;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}

	public Integer getBmoney() {
		return bmoney;
	}

	public void setBmoney(Integer bmoney) {
		this.bmoney = bmoney;
	}

	public Integer getServerstars() {
		return serverstars;
	}

	public void setServerstars(Integer serverstars) {
		this.serverstars = serverstars;
	}

/*	public String getEamil() {
		return eamil;
	}

	public void setEamil(String eamil) {
		this.eamil = eamil;
	}*/

	public Integer getNetrytype() {
		return netrytype;
	}

	public void setNetrytype(Integer netrytype) {
		this.netrytype = netrytype;
	}

	public String getBanme() {
		return banme;
	}

	public void setBanme(String banme) {
		this.banme = banme;
	}



	public String getSocialcode() {
		return socialcode;
	}

	public void setSocialcode(String socialcode) {
		this.socialcode = socialcode;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getPronice() {
		return pronice;
	}

	public void setPronice(String pronice) {
		this.pronice = pronice;
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

	public String getDetailadrr() {
		return detailadrr;
	}

	public void setDetailadrr(String detailadrr) {
		this.detailadrr = detailadrr;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	/*public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
*/
	public String getCustomercont() {
		return customercont;
	}

	public void setCustomercont(String customercont) {
		this.customercont = customercont;
	}

	public String getCustomerqq() {
		return customerqq;
	}

	public void setCustomerqq(String customerqq) {
		this.customerqq = customerqq;
	}

	public String getBusinesscope() {
		return businesscope;
	}

	public void setBusinesscope(String businesscope) {
		this.businesscope = businesscope;
	}

	public String getSociepath() {
		return sociepath;
	}

	public void setSociepath(String sociepath) {
		this.sociepath = sociepath;
	}

	public String getZconstacpath() {
		return zconstacpath;
	}

	public void setZconstacpath(String zconstacpath) {
		this.zconstacpath = zconstacpath;
	}

	public String getFconstacpath() {
		return fconstacpath;
	}

	public void setFconstacpath(String fconstacpath) {
		this.fconstacpath = fconstacpath;
	}

	public Boolean getIsagency() {
		return isagency;
	}

	public void setIsagency(Boolean isagency) {
		this.isagency = isagency;
	}

	public String getAgencyimg() {
		return agencyimg;
	}

	public void setAgencyimg(String agencyimg) {
		this.agencyimg = agencyimg;
	}

/*	public String getIspromote() {
		return ispromote;
	}

	public void setIspromote(String ispromote) {
		this.ispromote = ispromote;
	}*/

	public Boolean getIsbond() {
		return isbond;
	}

	public void setIsbond(Boolean isbond) {
		this.isbond = isbond;
	}
}
