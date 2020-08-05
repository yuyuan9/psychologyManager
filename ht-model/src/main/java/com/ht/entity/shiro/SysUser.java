package com.ht.entity.shiro;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ht.entity.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2018/1/10.
 */

/**
 * swagger
 * @ApiModel()用于类 ；表示对类进行说明，用于参数用实体类接收
 *  value–表示对象名
 *  description–描述
 * 都可省略
 * @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
 *  value–字段说明
 *  name–重写属性名字
 *  dataType–重写属性类型
 *  required–是否必填
 *  example–举例说明
 *  hidden–隐藏
 */


@ApiModel(value="系统用户",description="用户对象SysUser")
@TableName(value="t_sys_user")
public class SysUser implements Serializable {

    @ApiModelProperty(value="用户id",name="userId")
    @TableId(value = "USER_ID", type = IdType.UUID)
    private String userId;
    
    private String username;//登录名  唯一（手机/统一信用代码）
    
    private String password;//密码
	@TableField(value="headImg")
	private String headImg;//用户图片
    
    @TableField(value="trueName")
    private String trueName;//登录后显示名称
    
    @TableField(value = "email")
    private String email;//电子邮件
    
    @TableField(value = "regip")
    private String regip;//注册时ip地址
    
    @TableField(value = "ip")
    private String lastip;//记录最后登录ip地址
    
    @TableField(value = "active")
    private Boolean active=true;//是否账号可用
    
    @TableField(value = "login_count")
    private Integer logincount;
    
    @TableField(value = "regcity")
    private String regcity;//注册城市（辅助，通过ip地址拿取）
    @TableField(value = "regProvince")
    private String regprovince;//注册的省份（辅助，通过ip地址拿取）
	@TableField(value = "regarec")
	private String regarec;

	@TableField(value = "phone")
    private String phone;
    
    @TableField(value = "linkman")
    private String linkman;//联系人
    
    @TableField(value = "companyid")
    private String companyid;//分公司id
    
    @TableField(value = "remark")
    private String remark;
    
    
    
    /**
     * @see com.ht.entity.shiro.constants.UserType
     * 用户类型
     */
    @TableField(value = "userType")
    private String userType;
    
    @TableField(value = "createdate")
    private Date createdate;//创建时间
    
    @TableField(value = "create_id")
    private String createId;
    
    @TableField(value = "lastmodified")
    private Date lastmodified;//最后修改时间
    
    @TableField(value = "region_id")
    private String regionId; //所属区域
	@TableField(value = "ROLE_ID")

	private String  roleid; //用户角色

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRegarec() {
		return regarec;
	}

	public void setRegarec(String regarec) {
		this.regarec = regarec;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getLogincount() {
		return logincount;
	}

	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	public String getRegcity() {
		return regcity;
	}

	public void setRegcity(String regcity) {
		this.regcity = regcity;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getRegprovince() {
		return regprovince;
	}

	public void setRegprovince(String regprovince) {
		this.regprovince = regprovince;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	


}
