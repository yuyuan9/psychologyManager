package com.ht.commons.support.geelink.jsonquery;

/**
 * 查询自动封装
 * @author jied
 *
 */
public class GeeLinkQuery<T> {
	
	private Class clazz;
	
	private GeeLinkQuery() {}
	
	public GeeLinkQuery(Class clazz) {
		this.clazz=clazz;
	}
	
	public String getQuery() {
		Class2JsonQuery query = new Class2JsonQuery(clazz);
		return null;
		
	}

}
