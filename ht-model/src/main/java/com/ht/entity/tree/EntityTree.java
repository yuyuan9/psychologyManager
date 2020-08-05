package com.ht.entity.tree;


import com.ht.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityTree extends BaseEntity {
	
	private String pid;
	
	private List<EntityTree> children = new ArrayList<EntityTree>();

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<EntityTree> getChildren() {
		return children;
	}

	public void setChildren(List<EntityTree> children) {
		this.children = children;
	}

	


	
	
	

}
