package com.ht.entity.shiro;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.commons.support.tree.entity.TreeEntity;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 */
@TableName(value="t_sys_permission")
public class SysPermission extends TreeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String path;//前端路由
    
    private String component; //组件  配置调整到后台的文件夹
    
    private String redirect; // 重定向
    
    private String name; //名称  后台使用
     
    private String title; //标题
    
    private String icon;  //后台图标
    
    private String url;
    
    private Integer menu;//0为功能权限，1为菜单
    
    private String remark;//备注
    private Integer sort;//排序
    
  
    private String open;//树形是否打开或者关闭
    
    
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getMenu() {
		return menu;
	}
	public void setMenu(Integer menu) {
		this.menu = menu;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

  


   
}
