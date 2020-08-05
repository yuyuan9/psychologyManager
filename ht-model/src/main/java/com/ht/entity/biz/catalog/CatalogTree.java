package com.ht.entity.biz.catalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CatalogTree {
	
	private static List<Catalog> list;
	
	
	public CatalogTree(List<Catalog> lists){
		list=lists;
	}
	public List<Catalog> getList(){
		List<Catalog> treeList=new ArrayList<Catalog>();
		for(Catalog c:list){
			if(StringUtils.equals(c.getGrade(), "1")){
				treeList.add(getChildList(c));
			}
		}
		return treeList;
	}
	
	public static Catalog getChildList(Catalog catalog){
		List<Catalog> ChildList=new ArrayList<Catalog>();
		for(Catalog c:list){
			if(StringUtils.equals(c.getPname(), catalog.getName())){
				getChildList(c);
				ChildList.add(c);
			}
		}
		catalog.setList(ChildList);
		return catalog;
	}
}
