package com.ht.entity.biz.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

import java.util.Date;


@TableName("t_sys_passproduct")
public class PassProduct extends BaseEntity {

   private  String productname;//产品名称
   private  Integer plevel; //产品级别  1：国家 2：省级 3：市级  4：区级
   private  String provice;//省
   private  String city; //市
   private  String area; //区
   private  Integer  pricetype;//价格类型 1:一口价 2：区级价  3：面议
   private  String price; //价格
   private  Integer applytimetype ; //申报时间类型 1：开始时间-结束时间 2：全年申报
   private  Date  begintime ; //开始申报时间
   private  Date  endtime ;//结束申报时间
   private  String applyobject; //申报对象
   private  String supportmode; //支持方式
   private  String departmet; //主要部门
   private  String subsidy; //项目补助金
   private  String applybenefit; //申报好处
   private  String productinfo;// 产品介绍
   private  String servicecontent;//服务内容
   private  String other;//其他
   private  String finishtime;//完成时间/ 天（粒度产品）
   private  String producttypeone;
   private  String producttypetwo;
   private  String producttypethree;
   private  Integer staut; //状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
   private  String resond; //原因

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getPlevel() {
        return plevel;
    }

    public void setPlevel(Integer plevel) {
        this.plevel = plevel;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
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

    public Integer getPricetype() {
        return pricetype;
    }

    public void setPricetype(Integer pricetype) {
        this.pricetype = pricetype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getApplytimetype() {
        return applytimetype;
    }

    public void setApplytimetype(Integer applytimetype) {
        this.applytimetype = applytimetype;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getApplyobject() {
        return applyobject;
    }

    public void setApplyobject(String applyobject) {
        this.applyobject = applyobject;
    }

    public String getSupportmode() {
        return supportmode;
    }

    public void setSupportmode(String supportmode) {
        this.supportmode = supportmode;
    }

    public String getDepartmet() {
        return departmet;
    }

    public void setDepartmet(String departmet) {
        this.departmet = departmet;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getApplybenefit() {
        return applybenefit;
    }

    public void setApplybenefit(String applybenefit) {
        this.applybenefit = applybenefit;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getServicecontent() {
        return servicecontent;
    }

    public void setServicecontent(String servicecontent) {
        this.servicecontent = servicecontent;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getProducttypeone() {
        return producttypeone;
    }

    public void setProducttypeone(String producttypeone) {
        this.producttypeone = producttypeone;
    }

    public String getProducttypetwo() {
        return producttypetwo;
    }

    public void setProducttypetwo(String producttypetwo) {
        this.producttypetwo = producttypetwo;
    }

    public String getProducttypethree() {
        return producttypethree;
    }

    public void setProducttypethree(String producttypethree) {
        this.producttypethree = producttypethree;
    }

    public Integer getStaut() {
        return staut;
    }

    public void setStaut(Integer staut) {
        this.staut = staut;
    }

    public String getResond() {
        return resond;
    }

    public void setResond(String resond) {
        this.resond = resond;
    }
}