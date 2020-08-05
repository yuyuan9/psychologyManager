package com.ht.entity.biz.library;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;


/**
 * 资源包实体类
 */

@TableName(value="t_resource_page")
public class ResourcePage  extends BaseEntity  implements java.io.Serializable{

    private String name;//资源包名称
    private  Integer retype;//资源包类型
    private Integer classify;//分类
    private String  original;//原价
    private String  benefit;//优惠价
    private String  profile;//简介
    private String   libraryid;     //资源文档
    private String  intrpage;//资源包介绍页仅允许上传PNG、JPEG、PDF格式文件
    private String displaymap;//封面展示图
    private Integer browsecount=0;//浏览量
    private Integer jbrowsecount=0;//后台管理浏览量
    private Integer downloadcount =0;//下载量
    private Integer jdownloadcount =0;//后台管理下载量
    private String ableregion;//适用区域 （多区域逗号隔开）
    private String province;//省
    private String city;//市
    private String area;//区
    private String department;//主管部门
    private String score;//评分
    private int istop;//是否置顶
    private int sort;//
    private  int collenum;//收藏量
    private String description;
    private String keywords;
    private String zippath;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getCollenum() {
        return collenum;
    }

    public void setCollenum(int collenum) {
        this.collenum = collenum;
    }

    public String getZippath() {
        return zippath;
    }

    public void setZippath(String zippath) {
        this.zippath = zippath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRetype() {
        return retype;
    }

    public void setRetype(Integer retype) {
        this.retype = retype;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLibraryid() {
        return libraryid;
    }

    public void setLibraryid(String libraryid) {
        this.libraryid = libraryid;
    }

    public String getIntrpage() {
        return intrpage;
    }

   

    public void setIntrpage(String intrpage) {
		this.intrpage = intrpage;
	}

	public String getDisplaymap() {
        return displaymap;
    }

    public void setDisplaymap(String displaymap) {
        this.displaymap = displaymap;
    }

    public Integer getBrowsecount() {
        return browsecount;
    }

    public void setBrowsecount(Integer browsecount) {
        this.browsecount = browsecount;
    }

    public Integer getJbrowsecount() {
        return jbrowsecount;
    }

    public void setJbrowsecount(Integer jbrowsecount) {
        this.jbrowsecount = jbrowsecount;
    }

    public Integer getDownloadcount() {
        return downloadcount;
    }

    public void setDownloadcount(Integer downloadcount) {
        this.downloadcount = downloadcount;
    }

    public Integer getJdownloadcount() {
        return jdownloadcount;
    }

    public void setJdownloadcount(Integer jdownloadcount) {
        this.jdownloadcount = jdownloadcount;
    }

    public String getAbleregion() {
        return ableregion;
    }

    public void setAbleregion(String ableregion) {
        this.ableregion = ableregion;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getIstop() {
        return istop;
    }

    public void setIstop(int istop) {
        this.istop = istop;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
