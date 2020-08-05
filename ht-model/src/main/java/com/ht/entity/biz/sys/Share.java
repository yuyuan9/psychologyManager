package com.ht.entity.biz.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 分享注册
 */
@TableName(value = "t_share")
public class Share {
    @TableId(type=IdType.UUID)
    private String id;
    private String createid;

    private Date createdate;

    private String fromuserid; //分享人
    private String touserid; //被分享人
    private String type;// 分享类型
    private String phone;//分享人手机号
    private String honey;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getTouserid() {
        return touserid;
    }

    public void setTouserid(String touserid) {
        this.touserid = touserid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHoney() {
        return honey;
    }

    public void setHoney(String honey) {
        this.honey = honey;
    }
}
