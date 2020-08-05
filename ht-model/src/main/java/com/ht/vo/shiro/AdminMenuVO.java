package com.ht.vo.shiro;

import com.ht.commons.support.tree.entity.TreeEntity;

/**
 * 后台菜单vo
 * 
 * @author jied
 *
 */
public class AdminMenuVO extends TreeEntity {

	private String path;// 前端路由

	private String component; // 组件 配置调整到后台的文件夹

	private String redirect; // 重定向

	private String name; // 名称 后台使用
	
	private Meta meta=new Meta();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	
	public static class  Meta{
		private String title;
		private String icon;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
	}

}


