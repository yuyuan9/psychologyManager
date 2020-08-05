package com.ht.entity.biz.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 分享注册
 */
@TableName(value = "t_sys_watermark")
public class Watermark implements Serializable {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private String createid;
    private Date createdate;
    private String context; //水印内容
    private Integer isenable; //是否启用 1：启用 2：禁用

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getIsenable() {
        return isenable;
    }

    public void setIsenable(Integer isenable) {
        this.isenable = isenable;
    }
}
