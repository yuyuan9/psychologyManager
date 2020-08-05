package com.ht.entity.biz.citytree;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ht.commons.support.tree.entity.TreeEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 区域城市树
 * 
 * @author jied
 *
 */
@ApiModel(value = "城市树形实体列", description = "城市树形实体类Citytree")
public class Citytree extends TreeEntity {
	//@TableId
	//@ApiModelProperty(value = "id", name = "id")
	//private String id;
	//@ApiModelProperty(value = "父类编码", name = "pid", required = true) // 必填
	//private String pid;
	//@ApiModelProperty(value = "子类编码", name = "rpid", required = true) // 必填
	private String rpid;
	@ApiModelProperty(value = "城市名", name = "cityName", required = true) // 必填
	private String cityName;

	private String url;
	@ApiModelProperty(value = "实体集合", name = "menus")
	//private List<Citytree> menus;
	
	private Integer disables=0;

	public Citytree() {
	}

	public Citytree(String id, String pid, String cityName, String url, String rpid) {

		super();

		//this.id = id;

		//this.pid = pid;

		this.cityName = cityName;

		this.url = url;

	}

	public String getRpid() {
		return rpid;
	}

	public void setRpid(String rpid) {
		this.rpid = rpid;
	}

	//public String getId() {

		//return id;

	//}

	//public void setId(String id) {

	//	this.id = id;

	//}

	//public String getPid() {

	//	return pid;

	//}

	//public void setPid(String pid) {

	//	this.pid = pid;

	//}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getUrl() {

		return url;

	}

	public void setUrl(String url) {

		this.url = url;

	}

	//public List<Citytree> getMenus() {

	//	return menus;

	//}

	//public void setMenus(List<Citytree> menus) {

	//	this.menus = menus;

	//}

	public Integer getDisables() {
		return disables;
	}

	public void setDisables(Integer disables) {
		this.disables = disables;
	}
	
	

}
