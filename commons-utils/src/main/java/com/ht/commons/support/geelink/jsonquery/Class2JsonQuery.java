package com.ht.commons.support.geelink.jsonquery;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.ht.commons.support.geelink.entity.Librarygelink;
import org.apache.commons.lang.StringUtils;

import com.ht.commons.support.geelink.annotation.HighLightParam;
import com.ht.commons.support.geelink.annotation.ReturnParam;
import com.ht.commons.support.geelink.entity.Policy;
import com.ht.commons.support.geelink.entity.GeelinkPolicyDig;
import com.ht.commons.utils.ReflectHelper;

public class Class2JsonQuery {
	
	private Class clazz;
	
	private Integer row=10;
	
	private Integer start=0;

	public Class2JsonQuery(Class clazz) {
		this.clazz=clazz;
	}
	
	public String queryStr(String query) {
		String fmt="{%s,%s,%s,%s}";
		return String.format(fmt, getCollection(),getQueryParam(query),getHighLightParam(),getReturnParam());
	}
	
	private String getQueryParam(String query) {
		StringBuffer str = new StringBuffer();
		str.append("\"queryParam\": {");
		str.append(" \"query\": \""+query+"\" ");
		str.append("}");
		return str.toString();
	}
	
	private String getCollection() {
		StringBuffer str= new StringBuffer();
		str.append("\"collection\":");
		if(this.clazz!=null) {
			if(this.clazz.getSimpleName().equals(GeelinkPolicyDig.class.getSimpleName())) {
				str.append("\"policyExpress\"");
			}else if(this.clazz.getSimpleName().equals(Librarygelink.class.getSimpleName())) {
				str.append("\"library\"");
			}else if(this.clazz.getSimpleName().equals(Policy.class.getSimpleName())) {
				str.append("\"policy\"");
			}
		}
		
		return str.toString();
	}
	
	private String getHighLightParam() {
		StringBuffer retLightParam = new StringBuffer();
		if(clazz!=null) {
			retLightParam.append("\"highLightParam\":{  \"startTag\": \"<em>\",  \"endTag\": \"</em>\",  \"field\": [ ");
			retLightParam.append(getField(HighLightParam.class.getSimpleName()));
			retLightParam.append("] }");
		}
		return retLightParam.toString();
	}
	
	/**
	 * 获取返回属性
	 * @return
	 */
	private String getReturnParam() {
		StringBuffer retfield = new StringBuffer();
		if(clazz!=null) {
			retfield.append("\"returnParam\":{ \"field\":[");
			retfield.append(getField(ReturnParam.class.getSimpleName()));
			retfield.append("]");
			retfield.append(",\"row\":").append(this.getRow());
			retfield.append(",\"start\":").append(this.getStart());
			retfield.append("}");
		}
		return retfield.toString();
	}
	
	private String getField(String type) {
		StringBuffer fieldstr = new StringBuffer();
		Field[] fields=ReflectHelper.getFieldAll(clazz);
		for(Field field:fields) {
			Annotation[] as=field.getAnnotations();
			for(Annotation a:as) {
				if(a instanceof ReturnParam && ReturnParam.class.getSimpleName().equals(type)) {
					fieldstr.append("\"").append(field.getName()).append("\",");
				}else if(a instanceof HighLightParam && HighLightParam.class.getSimpleName().equals(type)) {
					fieldstr.append("\"").append(field.getName()).append("\",");
				}
			}
		}
		return StringUtils.removeEnd(fieldstr.toString(), ",");
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public static void main(String[] args) {
		Class2JsonQuery cj = new Class2JsonQuery(GeelinkPolicyDig.class);
		System.out.println(cj.getCollection());
		System.out.println(cj.getHighLightParam());
		System.out.println(cj.queryStr("广州"));
		//System.out.println(cj.getHighLightParam());
	}

}
