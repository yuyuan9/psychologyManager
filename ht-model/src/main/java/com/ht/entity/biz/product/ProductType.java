package com.ht.entity.biz.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.commons.support.tree.entity.TreeEntity;
@TableName("t_sys_producttype")
public class ProductType extends TreeEntity {

    private String name; // 产品名称
    private Boolean active;//启用禁用
    private  int sort= 0 ; //排序号
    private  String  imgpath;
    private Boolean isscience ;//判断产品种类 0:否 1：是
    private  String remark;//备注
    private  String twoflag;//产品标签（二级显示，多个逗号隔开）
    private Boolean ishot;//是否最热 false:否 true：是

    public Boolean getIsscience() {
        return isscience;
    }

    public void setIsscience(Boolean isscience) {
        this.isscience = isscience;
    }

    public Boolean getIshot() {
        return ishot;
    }

    public void setIshot(Boolean ishot) {
        this.ishot = ishot;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTwoflag() {
        return twoflag;
    }

    public void setTwoflag(String twoflag) {
        this.twoflag = twoflag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
