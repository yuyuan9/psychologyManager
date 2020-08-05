package com.ht.entity.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;


@ApiModel(value="轮播图实体类",description="Indexbanner")
@TableName(value="t_sys_banner")
public class Indexbanner extends BaseEntity {
    /**
     * 标题
     */
    private String title ;
    /*
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String description;

    /*
     * 图片地址
     */
    private String imgpath;

    /*
     * 轮播图点击url地址
     */
    private String url;
    /*
     * 类型
     */
    private Integer type; // 1 :首页 2:交易大厅顶部 3：交易大厅服务商推荐

    private  Integer isdisable; //是否禁用

    private String proid ;//省份id

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsdisable() {
        return isdisable;
    }

    public void setIsdisable(Integer isdisable) {
        this.isdisable = isdisable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
