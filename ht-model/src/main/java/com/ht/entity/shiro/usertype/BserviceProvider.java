package com.ht.entity.shiro.usertype;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import org.codehaus.jackson.map.Serializers;

import javax.activation.MailcapCommandMap;
import java.awt.*;
import java.util.Date;

/**
 * 服务商关联信息类
 * @author jied
 *
 */
@ApiModel(value="服务商信息中间表",description="服务商信息中间表")
@TableName("t_sys_bservice")
public class BserviceProvider {
	@TableId(type=IdType.UUID)
	private String bid;
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
	@TableField(value="bstats")
	private  Integer stats;// 审核状态
	private String reason ;//备注(拒绝原因)

	private Date createdate;//创建时间

	private String createid;

	private Date lastmodified;//最后修改时间

	private String regionid; //所属区域

	private String businesslicense; //营业执照



	@TableField(value="isagency")
	private Boolean isagency ;//是否相关服务机构 0:否 1：是

	@TableField(value="agencyimg")
	private String agencyimg ;//服务机构证明

//	@TableField(value="ispromote")
//	private String ispromote ;//是否推荐


	@TableField(value="isbond")
	private Boolean isbond ;//是否交保证金0:否 1：是





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

	public Boolean getIsbond() {
		return isbond;
	}

	public void setIsbond(Boolean isbond) {
		this.isbond = isbond;
	}

	public String getBusinesslicense() {
		return businesslicense;
	}

	public void setBusinesslicense(String businesslicense) {
		this.businesslicense = businesslicense;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public enum  btype {
		To_be_audited(1,"待审核"),
		Already(2,"已是服务商"),
		refuse(4,"审核拒绝");

		private final String name;
		private final Integer stat;
		btype(int stat ,String name) {
			this.name = name;
			this.stat = stat;
		}

		public String getName() {
			return this.name;
		}

		public Integer getStat() {
			return stat;
		}
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getNetrytype() {
		return netrytype;
	}

	public void setNetrytype(Integer netrytype) {
		this.netrytype = netrytype;
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

	public String getBanme() {
		return banme;
	}

	public void setBanme(String banme) {
		this.banme = banme;
	}

	public String getNetryname() {
		return netryname;
	}

	public void setNetryname(String netryname) {
		this.netryname = netryname;
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



}
