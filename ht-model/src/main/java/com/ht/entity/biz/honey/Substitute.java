package com.ht.entity.biz.honey;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;

/**
 * 金币置换honey 金币提现
 */
@ApiModel(value="金币置换honey 金币提现",description="Substitute")
@TableName(value="t_sys_honeyapply")
public class Substitute  extends BaseEntity {

    private String costtyoe; //费用类型
    private String payresion; //支付原因
    private String paymethod;//支付方式
    private String paycompanyname;//支付公司名称
    private String costattribution;//费用归属
    private String number;//数据id
    private String  banktype; //银行类型
    private String cardnumber;//银行卡号
    private String applicants; //收款人姓名
    private String phone ; //提款人手机号
    private double beforetax; //税前(金额)
    private double aftertax;  //税后（实际转账）
    private String taxrate;//税率
    private double taxes; //纳税额




    private double applygold; //申请金币
    private double gethoney;//置换的honey
    private String flownumber;//流水单号
    private Integer type ;//1:待处理 2：审核失败 3：审核中 4：转账失败 5：转账成功
    private String refuseresion; //备注（拒绝原因或其他）
    private Integer applytype;//1:金币转honey 2:金币转现金

    private int numberadd;


    public Substitute(String payresion) {
        this.payresion = payresion;
    }

    public Substitute() {

    }

    public int getNumberadd() {
        return numberadd;
    }

    public void setNumberadd(int numberadd) {
        this.numberadd = numberadd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getApplygold() {
        return applygold;
    }

    public void setApplygold(double applygold) {
        this.applygold = applygold;
    }

    public double getGethoney() {
        return gethoney;
    }

    public void setGethoney(double gethoney) {
        this.gethoney = gethoney;
    }

    public double getBeforetax() {
        return beforetax;
    }

    public void setBeforetax(double beforetax) {
        this.beforetax = beforetax;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getAftertax() {
        return aftertax;
    }


    public void setAftertax(double aftertax) {
        this.aftertax = aftertax;
    }

    public String getFlownumber() {
        return flownumber;
    }

    public void setFlownumber(String flownumber) {
        this.flownumber = flownumber;
    }



    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getApplicants() {
        return applicants;
    }

    public void setApplicants(String applicants) {
        this.applicants = applicants;
    }

    public String getRefuseresion() {
        return refuseresion;
    }

    public void setRefuseresion(String refuseresion) {
        this.refuseresion = refuseresion;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getApplytype() {
		return applytype;
	}

	public void setApplytype(Integer applytype) {
		this.applytype = applytype;
	}

    public String getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(String taxrate) {
        this.taxrate = taxrate;
    }

    public String getCosttyoe() {
        return costtyoe;
    }

    public void setCosttyoe(String costtyoe) {
        this.costtyoe = costtyoe;
    }

    public String getPayresion() {
        return payresion;
    }

    public void setPayresion(String payresion) {
        this.payresion = payresion;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getPaycompanyname() {
        return paycompanyname;
    }

    public void setPaycompanyname(String paycompanyname) {
        this.paycompanyname = paycompanyname;
    }

    public String getCostattribution() {
        return costattribution;
    }

    public void setCostattribution(String costattribution) {
        this.costattribution = costattribution;
    }
}
