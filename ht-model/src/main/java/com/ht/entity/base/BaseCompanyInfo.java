package com.ht.entity.base;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class BaseCompanyInfo {
	
	@TableId(type=IdType.UUID)
    private String id;
	
	private String companyname;//企业名称
	//社会统一信用代码
	private String number;
	//注册时间 
	private String regDate;
	//注册地址
	private String regAddr;
	//联系人
	private String linkman;
	//电话
	private String tel;
	
	//邮箱/E-mail/E-mail
	private String email;
	
	//主营产品技术领域/主营业务技术领域/主营产品（服务）
	private String techfield;
	
	//职工总数/企业职工总数/企业职工总数
	private Integer worktotal;
	
	//科技人员数/科技活动人员/研发人员数量
	private Integer develtotal;
	//净资产/净资产/净资产总额
	private Double netassetstotal;
	//销售收入/收入总额/销售（营业）收入
	private Double saletotal;
	//发明专利/授权发明专利/国内发明专利拥有量(申请/授权)
	private Integer invent;
	//植物新物种/拥有植物新品种/植物新品种权拥有量(申请/授权)
	private Integer plant;
	//集成电路布局设计专有权/拥有集成电路布图/集成电路布图设计权拥有量(申请/授权)
	private Integer molectron;
	//实用新型专利/拥有国内实用新型专利/国内实用新型专利拥有量(申请/授权)
	private Integer utilitymodel;
	//外观设计专利/拥有外观专利/外观设计专利拥有量(申请/授权)
	private Integer appearance;
	//软件著作权/拥有软件著作权/软件著作权拥有量(申请/授权)
	private Integer software;

    
    private Date createdate;//创建时间
    
    private String createid;
    
    private Date lastmodified;//最后修改时间
    
    private String regionid; //所属区域
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	
	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTechfield() {
		return techfield;
	}

	public void setTechfield(String techfield) {
		this.techfield = techfield;
	}

	public Integer getWorktotal() {
		return worktotal;
	}

	public void setWorktotal(Integer worktotal) {
		this.worktotal = worktotal;
	}

	public Integer getDeveltotal() {
		return develtotal;
	}

	public void setDeveltotal(Integer develtotal) {
		this.develtotal = develtotal;
	}

	public Double getNetassetstotal() {
		return netassetstotal;
	}

	public void setNetassetstotal(Double netassetstotal) {
		this.netassetstotal = netassetstotal;
	}

	public Double getSaletotal() {
		return saletotal;
	}

	public void setSaletotal(Double saletotal) {
		this.saletotal = saletotal;
	}

	public Integer getInvent() {
		return invent;
	}

	public void setInvent(Integer invent) {
		this.invent = invent;
	}

	public Integer getPlant() {
		return plant;
	}

	public void setPlant(Integer plant) {
		this.plant = plant;
	}

	public Integer getMolectron() {
		return molectron;
	}

	public void setMolectron(Integer molectron) {
		this.molectron = molectron;
	}

	public Integer getUtilitymodel() {
		return utilitymodel;
	}

	public void setUtilitymodel(Integer utilitymodel) {
		this.utilitymodel = utilitymodel;
	}

	public Integer getAppearance() {
		return appearance;
	}

	public void setAppearance(Integer appearance) {
		this.appearance = appearance;
	}

	public Integer getSoftware() {
		return software;
	}

	public void setSoftware(Integer software) {
		this.software = software;
	}
	
	


}
