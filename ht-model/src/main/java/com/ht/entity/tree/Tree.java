package com.ht.entity.tree;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 管理权限树
 */
public class Tree<T extends EntityTree>  {

//	private  Map<String, T> map = new LinkedHashMap<String, T>();

	private  List<T> treelist = new ArrayList<T>();
	
	private List<T> source;
	
	//private static String option ="<option value='%s'>%s</option>\n";

//	private  StringBuffer select=new StringBuffer();
	
	public Tree(List<T> source) {
		//System.out.println("source:"+source);
		this.source = source;
//		initMap();
	}

//	private void initMap() {
//		if (!source.isEmpty()) {
//			for (T juri : source) {
//				map.put(juri.getId(), juri);
//			}
//		}
//	}
	
	
    /*对象树结构*/
	public List<T> objectTree() {
		if (!source.isEmpty()) {
			for (T t : source) {
				if (StringUtils.equals(t.getPid(), null)) {
					t.setChildren((List<EntityTree>)childrenTree(t.getId()));
					treelist.add(t);
				}
			}
			return treelist;
		}
		return new ArrayList<T>();
	}

	private List<T> childrenTree(String id) {
		if (!source.isEmpty() && id!=null) {
			List<T> children = new ArrayList<T>();
			for (T t : source) {
				if (!StringUtils.isBlank(t.getPid()) && id.equals(t.getPid())) {
					t.setChildren((List<EntityTree>)childrenTree(t.getId()));
					children.add(t);
				}
			}
			return children;
		}
		return new ArrayList<T>();
	}
	
	/**json结构树*/
//	public String getZTree(){
//		return JSONArray.fromObject(source).toString();
//	}
	

	


}
