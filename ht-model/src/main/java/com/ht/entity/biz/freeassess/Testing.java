package com.ht.entity.biz.freeassess;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.entity.base.BaseEntity;
import com.ht.validator.annotation.CompareNum;
import com.ht.validator.annotation.Num;
import com.ht.validator.annotation.Regular;
import com.ht.validator.annotation.Regular.RegType;
import com.ht.validator.annotation.Required;


public class Testing extends BaseEntity {
	public Testing(){
		init();
	}
	
	// 测试当前年份
	@TableField(value="curYear")
	private Integer curYear=0;
	// 你的公司名称
	@Required
	@TableField(value="companyName")
	private String companyName;
	// 联系人
	@Required
	@TableField(value="contact")
	private String contact;
	// 手机号码
	@Required
	@Regular(type=RegType.phone,errmsg="手机号码输入错误")
	@TableField(value="phone")
	private String phone;
	// 电子邮箱
	@Regular(type=RegType.email,errmsg="电子邮箱格式错误")
	@TableField(value="email")
	private String email;
	// 注册时间
//	@com.ht.validator.annotation.Date
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Required(errmsg="注册时间不能为空")
	@TableField(value="regDate")
	private Date regDate;
	
	@Required(errmsg="省份不能为空")
	@TableField(value="province")
	private String province;
	
	@Required(errmsg="城市不能为空")
	@TableField(value="city")
	private String city;
	
	//区域
	@TableField(value="area")
	private String area;
	// 注册地址 (街道)
	@TableField(value="regAddr")
	private String regAddr;
	// 主要产品服务(技术领域)
	@TableField(value="productServ")
	private String productServ;
	
	//产品认定情况
	@TableField(value="productCogn")
	private String productCogn;
	// 职工人总数
	@Num
	@TableField(value="workerTotal")
	private Integer workerTotal=0;
	
	//金华科技人员数
	@TableField(value="sciencePsersons")
	private Integer sciencePsersons=0;
	
	// 大专以上技术人员
	//@Num
	//@Deprecated   //开始作废
	@TableField(value="juniorTechTotal")
	private Integer juniorTechTotal=0;
	
	// 研发人员总数（科技人员数）
	@Num
	@CompareNum(maxField="workerTotal",error="科技人员数不能大于职工人总数")
	@TableField(value="developmentTotal")
	private Integer developmentTotal=0;
	
	
	// 近三年销售（金华是近一年销售收入）
	@TableField(value="saleFirstYear")
	private Integer saleFirstYear=0;
	@Num
	@TableField(value="saleFirstValue")
	private double saleFirstValue = 0f;
	@TableField(value="saleSecondYear")
	private Integer saleSecondYear=0;
	@Num
	@TableField(value="saleSecondValue")
	private double saleSecondValue = 0f;
	@TableField(value="saleThirdYear")
	private Integer saleThirdYear=0;
	@Num
	@TableField(value="saleThirdValue")
	private double saleThirdValue = 0f;

	// 近三年总资产（变更成近三年净资产）（金华是近一年净资产）
	@TableField(value="assetFirstYear")
	private Integer assetFirstYear=0;
	@Num
	@TableField(value="assetFirstValue")
	private double assetFirstValue = 0f;
	@TableField(value="assetSecondYear")
	private Integer assetSecondYear=0;
	@Num
	@TableField(value="assetSecondValue")
	private double assetSecondValue = 0f;
	@TableField(value="assetThirdYear")
	private Integer assetThirdYear=0;
	@Num
	@TableField(value="assetThirdValue")
	private double assetThirdValue = 0f;
	
	//近三年利润总额
	@TableField(value="profitFirstYear")
	private Integer profitFirstYear=0;
	@Num
	@TableField(value="profitFirstValue")
	private double profitFirstValue = 0f;
	@TableField(value="profitSecondYear")
	private Integer profitSecondYear=0;
	@Num
	@TableField(value="profitSecondValue")
	private double profitSecondValue = 0f;
	@TableField(value="profitThirdYear")
	private Integer profitThirdYear=0;
	@Num
	@TableField(value="profitThirdValue")
	private double profitThirdValue = 0f;
	
	//知识产权
	//--begin--
	@TableField(value="x1")
	private Integer x1=0;
	@Num
	@TableField(value="x1self")
	private Integer x1self=0;
	@Num
	@TableField(value="x1eg")
	private Integer x1eg=0;
	@TableField(value="x2")
	private Integer x2=0;
	@Num
	@TableField(value="x2self")
	private Integer x2self=0;
	@Num
	@TableField(value="x2eg")
	private Integer x2eg=0;
	@TableField(value="x3")
    private Integer x3=0;
    @Num
    @TableField(value="x3self")
	private Integer x3self=0;
	@Num
	@TableField(value="x3eg")
	private Integer x3eg=0;
	@TableField(value="x4")
    private Integer x4=0;
    @Num
    @TableField(value="x4self")
	private Integer x4self=0;
    @Num
    @TableField(value="x4eg")
	private Integer x4eg=0;
    @TableField(value="x5")
	private Integer x5=0;
	@Num
	@TableField(value="x5self")
	private Integer x5self=0;
	@Num
	@TableField(value="x5eg")
	private Integer x5eg=0;
	@TableField(value="x6")
	private Integer x6=0;
	@Num
	@TableField(value="x6self")
	private Integer x6self=0;
	@Num
	@TableField(value="x6eg")
	private Integer x6eg=0;
	
	
	
	//=========苏州新增begin=============
	@TableField(value="orgcode")
		private String orgcode;//组织机构代码
	@TableField(value="cog2008")
		private Boolean cog2008;//2008年以前是否认定高企
	@TableField(value="thirdfruit")
		private Integer thirdfruit=0;//近三年成果转换
	@TableField(value="onefruit")
		private Integer onefruit=0;//近一年科技成果转化数量 
	@TableField(value="compop")
		private Boolean compop;//合规运营
	@TableField(value="saleyfrate")
		private String saleyfrate;//销售研发比率  A B C D
	@TableField(value="jnyfrate")
		private String jnyfrate;//境内研发比率 	A B（金华是申报前一年高新技术产品（服务）收入占当年总收入的比例）
	@TableField(value="prodrate")
		private String prodrate;//产品比率 A B C
		
		/*
		 * 地区评审数据
		 * 1：为苏州
		 */
	@TableField(value="regionNum")
		private Integer regionNum=0;
		
		
		
		//=========苏州新增end=============
	
	public Integer getXtotal(){
		return this.x1+this.x2+this.x3+this.x4+this.x5+this.x6;
	}
	
	public Integer getXselftotal(){
		this.x1self=(this.x1self==null)?0:this.x1self;
		this.x2self=(this.x2self==null)?0:this.x2self;
		this.x3self=(this.x3self==null)?0:this.x3self;
		this.x4self=(this.x4self==null)?0:this.x4self;
		this.x5self=(this.x5self==null)?0:this.x5self;
		this.x6self=(this.x6self==null)?0:this.x6self;
		return this.x1self+this.x2self+this.x3self+this.x4self+this.x5self+this.x6self;
	}
	
	public Integer getXegTotal(){
		this.x1eg=(this.x1eg==null)?0:this.x1eg;
		this.x2eg=(this.x2eg==null)?0:this.x2eg;
		this.x3eg=(this.x3eg==null)?0:this.x3eg;
		this.x4eg=(this.x4eg==null)?0:this.x4eg;
		this.x5eg=(this.x5eg==null)?0:this.x5eg;
		this.x6eg=(this.x6eg==null)?0:this.x6eg;
		return this.x1eg+this.x2eg+this.x3eg+this.x4eg+this.x5eg+this.x6eg;
		
	}
	
	@TableField(value="y1")
	private Integer y1=0;
	@Num
	@TableField(value="y1self")
	private Integer y1self=0;
	@Num
	@TableField(value="y1eg")
	private Integer y1eg=0;
	@TableField(value="y2")
	private Integer y2=0;
	@Num
	@TableField(value="y2self")
	private Integer y2self=0;
	@Num
	@TableField(value="y2eg")
	private Integer y2eg=0;
	@TableField(value="y3")
	private Integer y3=0;
	@Num
	@TableField(value="y3self")
	private Integer y3self=0;
	@Num
	@TableField(value="y3eg")
	private Integer y3eg=0;
	
	public Integer getYtotal(){
		return this.y1+this.y2+this.y3;
	}
	
	public Integer getYselftotal(){
		this.y1self=(this.y1self==null)?0:this.y1self;
		this.y2self=(this.y2self==null)?0:this.y2self;
		this.y3self=(this.y3self==null)?0:this.y3self;
		return this.y1self+this.y2self+this.y3self;
	}
	public Integer getYegtotal(){
		this.y1eg=(this.y1eg==null)?0:this.y1eg;
		this.y2eg=(this.y2eg==null)?0:this.y2eg;
		this.y3eg=(this.y3eg==null)?0:this.y3eg;
		return this.y1eg+this.y2eg+this.y3eg;
	}
	@Required(errmsg="请选择是或否")
	@TableField(value="other")
	private Boolean other=false;
	
	//--end--
	
	// 近三年专利
	@Deprecated
	@TableField(value="invention")
	private Integer invention = 0;
	// 实用专利
	@Deprecated
	@TableField(value="practical")
	private Integer practical = 0;
	// 外观设计专利
	@Deprecated
	@TableField(value="design")
	private Integer design = 0;
	// 软件
	@Deprecated
	@TableField(value="software")
	private Integer software = 0;
	// 集成电路设计专利
	@Deprecated
	@TableField(value="integrate")
	private Integer integrate = 0;
	// 植物新品专利
	@Deprecated
	@TableField(value="botany")
	private Integer botany = 0;
	
	//是否制定了企业研究开发的组织管理制度、建立了研发投入核算体系、编制了研发费用辅助账
	@Required(errmsg="请选择A、B、C、D中的一项")
	@TableField(value="compsys")
	private String compsys;
	
	//是否设立研发机构、是否开展产学研合作
	@Required(errmsg="请选择A、B、C中的一项")
	@TableField(value="cooper")
	private String cooper;
	//•10、是否建立了科技成果转化的组织实施与激励奖励制度，建立开放式的创新创业平台
	@Required(errmsg="请选择A、B、C中的一项")
	@TableField(value="platform")
	private String platform;
	//•11、是否建立了科技人员的培养进修、职工技能培训、优秀人才引进，以及人才绩效评价奖励制度：
	@Required(errmsg="请选择是或否")
	@TableField(value="personnel")
	private String personnel;
	//•12、是否开展面向社会公众的科学技术普及活动
	//@Required(errmsg="请选择是或否")
	@TableField(value="activity")
	private String activity;

	// 您的研究开发项目是否制定了研究开发项目立项报告
	@Deprecated
	@TableField(value="projReport")
	private Boolean projReport = false;

	// 您是否建立了研发投入核算体系
	@Deprecated
	@TableField(value="accoSystem")
	private Boolean accoSystem = false;

	// 您是否开展了产学研合作的研发活动
	@Deprecated
	@TableField(value="rdact")
	private Boolean rdact = false;

	// 您是否设有研发机构并具备相应的设施和设备
	@Deprecated
	@TableField(value="faciAndEqui")
	private Boolean faciAndEqui = false;

	// 您是否建立了研发人员的绩效考核奖励制度
	@Deprecated
	@TableField(value="perAppr")
	private Boolean perAppr = false;

	// 企业有无研发费用辅助账或有无研发费用归集
	@Deprecated
	@TableField(value="collec")
	private Boolean collec = false;

	public Integer getCurYear() {
		return curYear;
	}

	public void setCurYear(Integer curYear) {
		this.curYear = curYear;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getProductServ() {
		return productServ;
	}

	public void setProductServ(String productServ) {
		this.productServ = productServ;
	}

	public Integer getWorkerTotal() {
		return workerTotal;
	}

	public void setWorkerTotal(Integer workerTotal) {
		this.workerTotal = workerTotal;
	}

	public Integer getJuniorTechTotal() {
		return juniorTechTotal;
	}

	public void setJuniorTechTotal(Integer juniorTechTotal) {
		this.juniorTechTotal = juniorTechTotal;
	}

	public Integer getDevelopmentTotal() {
		return developmentTotal;
	}

	public void setDevelopmentTotal(Integer developmentTotal) {
		this.developmentTotal = developmentTotal;
	}
	
	

	public Integer getSaleFirstYear() {
		return saleFirstYear;
	}

	public void setSaleFirstYear(Integer saleFirstYear) {
		this.saleFirstYear = saleFirstYear;
	}

	public double getSaleFirstValue() {
		return saleFirstValue;
	}

	public void setSaleFirstValue(double saleFirstValue) {
		this.saleFirstValue = saleFirstValue;
	}

	public Integer getSaleSecondYear() {
		return saleSecondYear;
	}

	public void setSaleSecondYear(Integer saleSecondYear) {
		this.saleSecondYear = saleSecondYear;
	}

	public double getSaleSecondValue() {
		return saleSecondValue;
	}

	public void setSaleSecondValue(double saleSecondValue) {
		this.saleSecondValue = saleSecondValue;
	}

	public Integer getSaleThirdYear() {
		return saleThirdYear;
	}

	public void setSaleThirdYear(Integer saleThirdYear) {
		this.saleThirdYear = saleThirdYear;
	}

	public double getSaleThirdValue() {
		return saleThirdValue;
	}

	public void setSaleThirdValue(double saleThirdValue) {
		this.saleThirdValue = saleThirdValue;
	}

	public Integer getAssetFirstYear() {
		return assetFirstYear;
	}

	public void setAssetFirstYear(Integer assetFirstYear) {
		this.assetFirstYear = assetFirstYear;
	}

	public double getAssetFirstValue() {
		return assetFirstValue;
	}

	public void setAssetFirstValue(double assetFirstValue) {
		this.assetFirstValue = assetFirstValue;
	}

	public Integer getAssetSecondYear() {
		return assetSecondYear;
	}

	public void setAssetSecondYear(Integer assetSecondYear) {
		this.assetSecondYear = assetSecondYear;
	}

	public double getAssetSecondValue() {
		return assetSecondValue;
	}

	public void setAssetSecondValue(double assetSecondValue) {
		this.assetSecondValue = assetSecondValue;
	}

	public Integer getAssetThirdYear() {
		return assetThirdYear;
	}

	public void setAssetThirdYear(Integer assetThirdYear) {
		this.assetThirdYear = assetThirdYear;
	}

	public double getAssetThirdValue() {
		return assetThirdValue;
	}

	public void setAssetThirdValue(double assetThirdValue) {
		this.assetThirdValue = assetThirdValue;
	}

	public Integer getInvention() {
		return invention;
	}

	public void setInvention(Integer invention) {
		this.invention = invention;
	}

	public Integer getPractical() {
		return practical;
	}

	public void setPractical(Integer practical) {
		this.practical = practical;
	}

	public Integer getDesign() {
		return design;
	}

	public void setDesign(Integer design) {
		this.design = design;
	}

	public Integer getSoftware() {
		return software;
	}

	public void setSoftware(Integer software) {
		this.software = software;
	}

	public Integer getIntegrate() {
		return integrate;
	}

	public void setIntegrate(Integer integrate) {
		this.integrate = integrate;
	}

	public Integer getBotany() {
		return botany;
	}

	public void setBotany(Integer botany) {
		this.botany = botany;
	}

	public Boolean getProjReport() {
		return projReport;
	}

	public void setProjReport(Boolean projReport) {
		this.projReport = projReport;
	}

	public Boolean getAccoSystem() {
		return accoSystem;
	}

	public void setAccoSystem(Boolean accoSystem) {
		this.accoSystem = accoSystem;
	}

	public Boolean getRdact() {
		return rdact;
	}

	public void setRdact(Boolean rDact) {
		this.rdact = rDact;
	}

	public Boolean getFaciAndEqui() {
		return faciAndEqui;
	}

	public void setFaciAndEqui(Boolean faciAndEqui) {
		this.faciAndEqui = faciAndEqui;
	}

	public Boolean getPerAppr() {
		return perAppr;
	}

	public void setPerAppr(Boolean perAppr) {
		this.perAppr = perAppr;
	}

	public Boolean getCollec() {
		return collec;
	}

	public void setCollec(Boolean collec) {
		this.collec = collec;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getProductCogn() {
		return productCogn;
	}

	public void setProductCogn(String productCogn) {
		this.productCogn = productCogn;
	}

	public Integer getProfitFirstYear() {
		return profitFirstYear;
	}

	public void setProfitFirstYear(Integer profitFirstYear) {
		this.profitFirstYear = profitFirstYear;
	}

	public double getProfitFirstValue() {
		return profitFirstValue;
	}

	public void setProfitFirstValue(double profitFirstValue) {
		this.profitFirstValue = profitFirstValue;
	}

	public Integer getProfitSecondYear() {
		return profitSecondYear;
	}

	public void setProfitSecondYear(Integer profitSecondYear) {
		this.profitSecondYear = profitSecondYear;
	}

	public double getProfitSecondValue() {
		return profitSecondValue;
	}

	public void setProfitSecondValue(double profitSecondValue) {
		this.profitSecondValue = profitSecondValue;
	}

	public Integer getProfitThirdYear() {
		return profitThirdYear;
	}

	public void setProfitThirdYear(Integer profitThirdYear) {
		this.profitThirdYear = profitThirdYear;
	}

	public double getProfitThirdValue() {
		return profitThirdValue;
	}

	public void setProfitThirdValue(double profitThirdValue) {
		this.profitThirdValue = profitThirdValue;
	}

	public Integer getX1() {
		return x1;
	}

	public void setX1(Integer x1) {
		this.x1 = x1;
	}

	public Integer getX1self() {
		return x1self;
	}

	public void setX1self(Integer x1self) {
		this.x1self = x1self;
	}

	public Integer getX1eg() {
		return x1eg;
	}

	public void setX1eg(Integer x1eg) {
		this.x1eg = x1eg;
	}

	public Integer getX2() {
		return x2;
	}

	public void setX2(Integer x2) {
		this.x2 = x2;
	}

	public Integer getX2self() {
		return x2self;
	}

	public void setX2self(Integer x2self) {
		this.x2self = x2self;
	}

	public Integer getX2eg() {
		return x2eg;
	}

	public void setX2eg(Integer x2eg) {
		this.x2eg = x2eg;
	}

	public Integer getX3() {
		return x3;
	}

	public void setX3(Integer x3) {
		this.x3 = x3;
	}

	public Integer getX3self() {
		return x3self;
	}

	public void setX3self(Integer x3self) {
		this.x3self = x3self;
	}

	public Integer getX3eg() {
		return x3eg;
	}

	public void setX3eg(Integer x3eg) {
		this.x3eg = x3eg;
	}

	public Integer getX4() {
		return x4;
	}

	public void setX4(Integer x4) {
		this.x4 = x4;
	}

	public Integer getX4self() {
		return x4self;
	}

	public void setX4self(Integer x4self) {
		this.x4self = x4self;
	}

	public Integer getX4eg() {
		return x4eg;
	}

	public void setX4eg(Integer x4eg) {
		this.x4eg = x4eg;
	}

	public Integer getX5() {
		return x5;
	}

	public void setX5(Integer x5) {
		this.x5 = x5;
	}

	public Integer getX5self() {
		return x5self;
	}

	public void setX5self(Integer x5self) {
		this.x5self = x5self;
	}

	public Integer getX5eg() {
		return x5eg;
	}

	public void setX5eg(Integer x5eg) {
		this.x5eg = x5eg;
	}

	public Integer getX6() {
		return x6;
	}

	public void setX6(Integer x6) {
		this.x6 = x6;
	}

	public Integer getX6self() {
		return x6self;
	}

	public void setX6self(Integer x6self) {
		this.x6self = x6self;
	}

	public Integer getX6eg() {
		return x6eg;
	}

	public void setX6eg(Integer x6eg) {
		this.x6eg = x6eg;
	}

	

	public Integer getY1() {
		return y1;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}

	public Integer getY1self() {
		return y1self;
	}

	public void setY1self(Integer y1self) {
		this.y1self = y1self;
	}

	public Integer getY1eg() {
		return y1eg;
	}

	public void setY1eg(Integer y1eg) {
		this.y1eg = y1eg;
	}

	public Integer getY2() {
		return y2;
	}

	public void setY2(Integer y2) {
		this.y2 = y2;
	}

	public Integer getY2self() {
		return y2self;
	}

	public void setY2self(Integer y2self) {
		this.y2self = y2self;
	}

	public Integer getY2eg() {
		return y2eg;
	}

	public void setY2eg(Integer y2eg) {
		this.y2eg = y2eg;
	}

	public Integer getY3() {
		return y3;
	}

	public void setY3(Integer y3) {
		this.y3 = y3;
	}

	public Integer getY3self() {
		return y3self;
	}

	public void setY3self(Integer y3self) {
		this.y3self = y3self;
	}

	public Integer getY3eg() {
		return y3eg;
	}

	public void setY3eg(Integer y3eg) {
		this.y3eg = y3eg;
	}


	public Boolean getOther() {
		return other;
	}

	public void setOther(Boolean other) {
		this.other = other;
	}

	public String getCompsys() {
		return compsys;
	}

	public void setCompsys(String compsys) {
		this.compsys = compsys;
	}

	public String getCooper() {
		return cooper;
	}

	public void setCooper(String cooper) {
		this.cooper = cooper;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public Boolean getCog2008() {
		return cog2008;
	}

	public void setCog2008(Boolean cog2008) {
		this.cog2008 = cog2008;
	}

	public Integer getThirdfruit() {
		return thirdfruit;
	}

	public void setThirdfruit(Integer thirdfruit) {
		this.thirdfruit = thirdfruit;
	}

	public Boolean getCompop() {
		return compop;
	}

	public void setCompop(Boolean compop) {
		this.compop = compop;
	}

	public String getSaleyfrate() {
		return saleyfrate;
	}

	public void setSaleyfrate(String saleyfrate) {
		this.saleyfrate = saleyfrate;
	}

	public String getJnyfrate() {
		return jnyfrate;
	}

	public void setJnyfrate(String jnyfrate) {
		this.jnyfrate = jnyfrate;
	}

	public String getProdrate() {
		return prodrate;
	}

	public void setProdrate(String prodrate) {
		this.prodrate = prodrate;
	}

	public Integer getRegionNum() {
		return regionNum;
	}

	public void setRegionNum(Integer regionNum) {
		this.regionNum = regionNum;
	}

	//初始化数据
	private void init(){
		
		if(this.curYear==null){
			this.curYear=Integer.valueOf(DateUtil.getYear());
			this.setSaleFirstYear(this.curYear-2);
			this.setSaleSecondYear(this.curYear-1);
			this.setSaleThirdYear(this.curYear);
			
			this.setAssetFirstYear(this.curYear-2);
			this.setAssetSecondYear(this.curYear-1);
			this.setAssetThirdYear(this.curYear);
		}
	}

	public Integer getOnefruit() {
		return onefruit;
	}

	public void setOnefruit(Integer onefruit) {
		this.onefruit = onefruit;
	}

	public Integer getSciencePsersons() {
		return sciencePsersons;
	}

	public void setSciencePsersons(Integer sciencePsersons) {
		this.sciencePsersons = sciencePsersons;
	}

//	@Override
//	public String toString() {
//		return "Testing [curYear=" + curYear + ", companyName=" + companyName + ", contact=" + contact + ", phone="
//				+ phone + ", email=" + email + ", regDate=" + regDate + ", regAddr=" + regAddr + ", province="
//				+ province + ", city=" + city + ", area=" + area + ", productServ=" + productServ + ", productCogn="
//				+ productCogn + ", workerTotal=" + workerTotal + ", juniorTechTotal=" + juniorTechTotal
//				+ ", developmentTotal=" + developmentTotal + ", saleFirstYear=" + saleFirstYear + ", saleFirstValue="
//				+ saleFirstValue + ", saleSecondYear=" + saleSecondYear + ", saleSecondValue=" + saleSecondValue
//				+ ", saleThirdYear=" + saleThirdYear + ", saleThirdValue=" + saleThirdValue + ", assetFirstYear="
//				+ assetFirstYear + ", assetFirstValue=" + assetFirstValue + ", assetSecondYear=" + assetSecondYear
//				+ ", assetSecondValue=" + assetSecondValue + ", assetThirdYear=" + assetThirdYear + ", assetThirdValue="
//				+ assetThirdValue + ", profitFirstYear=" + profitFirstYear + ", profitFirstValue=" + profitFirstValue
//				+ ", profitSecondYear=" + profitSecondYear + ", profitSecondValue=" + profitSecondValue
//				+ ", profitThirdYear=" + profitThirdYear + ", profitThirdValue=" + profitThirdValue + ", x1=" + x1
//				+ ", x1self=" + x1self + ", x1eg=" + x1eg + ", x2=" + x2 + ", x2self=" + x2self + ", x2eg=" + x2eg
//				+ ", x3=" + x3 + ", x3self=" + x3self + ", x3eg=" + x3eg + ", x4=" + x4 + ", x4self=" + x4self
//				+ ", x4eg=" + x4eg + ", x5=" + x5 + ", x5self=" + x5self + ", x5eg=" + x5eg + ", x6=" + x6 + ", x6self="
//				+ x6self + ", x6eg=" + x6eg + ", y1=" + y1 + ", y1self=" + y1self + ", y1eg=" + y1eg + ", y2=" + y2
//				+ ", y2self=" + y2self + ", y2eg=" + y2eg + ", y3=" + y3 + ", y3self=" + y3self + ", y3eg=" + y3eg
//				+ ", other=" + other + ", invention=" + invention + ", practical=" + practical + ", design=" + design
//				+ ", software=" + software + ", integrate=" + integrate + ", botany=" + botany + ", compsys=" + compsys
//				+ ", cooper=" + cooper + ", platform=" + platform + ", personnel=" + personnel + ", activity="
//				+ activity + ", projReport=" + projReport + ", accoSystem=" + accoSystem + ", rdact=" + rdact
//				+ ", faciAndEqui=" + faciAndEqui + ", perAppr=" + perAppr + ", collec=" + collec + "]";
//	}
	
	

}


