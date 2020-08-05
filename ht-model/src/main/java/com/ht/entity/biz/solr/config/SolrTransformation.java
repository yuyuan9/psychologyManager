package com.ht.entity.biz.solr.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.codehaus.jackson.map.ObjectMapper;

import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.ReflectHelper;


public class SolrTransformation {
	
	//数据上传至solr引擎（自动封装属性）
	public SolrInputDocument getSolrInputDocument(Object obj) throws Exception{
		SolrInputDocument sid=new SolrInputDocument();
		Field[] fields=ReflectHelper.getAllField(obj.getClass());
		Object object=null;
		for(Field f:fields){
			//System.out.println(f.getName()+"==========="+ReflectHelper.getValueByFieldName(obj, f.getName())+"========="+f.getType().getName());
			object=ReflectHelper.getValueByFieldName(obj, f.getName());
			if(StringUtils.equals(f.getType().getName(), "java.lang.String")){
				sid.addField(f.getName(), object==null?"":object);
			}else if(StringUtils.equals(f.getType().getName(), "java.util.Date")){
				sid.addField(f.getName(), object==null?"":DateUtil.dateToStr(DateUtil.StrTodate(String.valueOf(object), "EEE MMM dd HH:mm:ss z yyyy"),12));
			}else if(StringUtils.equals(f.getType().getName(), "java.lang.Boolean")||StringUtils.equals(f.getType().getName(), "boolean")){
				sid.addField(f.getName(), object==null?false:object);
			}else{
				sid.addField(f.getName(), object==null?0:object);
			}
		}
		return sid;
	}
	//批量上传数据至引擎
	public List<SolrInputDocument> getSolrInputDocumentlist(List list) throws Exception{
		List<SolrInputDocument> sids=new ArrayList<SolrInputDocument>();
		for(Object o:list){
			sids.add(getSolrInputDocument(o));
		}
		return sids;
	}
	//从solr引擎获取单条数据（自动封装）
	public Object getSolrDocument(SolrDocument solrDocument,Object obj) throws Exception{
		Field[] fields=ReflectHelper.getAllField(obj.getClass());
		for(Field f:fields){
			//System.out.println(f.getName()+"==========="+solrDocument.get(f.getName())+"========="+f.getType().getName());
			Object object=solrDocument.get(f.getName());
			if(StringUtils.equals(f.getType().getName(), "java.lang.String")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),object==null?"":object);
			}else if(StringUtils.equals(f.getType().getName(), "java.util.Date")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),object==null?null:DateUtil.StrTodate(String.valueOf(object), "yyyy-MM-dd HH:mm:ss"));
			}else if(StringUtils.equals(f.getType().getName(), "java.lang.Integer")||StringUtils.equals(f.getType().getName(), "int")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),(object==null||StringUtils.equals("", String.valueOf(object)))?(int)0:Integer.parseInt(String.valueOf(object)));
			}else if(StringUtils.equals(f.getType().getName(), "java.lang.Boolean")||StringUtils.equals(f.getType().getName(), "boolean")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),object==null?false:object);
			}else if(StringUtils.equals(f.getType().getName(), "java.lang.Double")||StringUtils.equals(f.getType().getName(), "double")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),(object==null||StringUtils.equals("", String.valueOf(object)))?0d:Double.valueOf(String.valueOf(object)));
			}else if(StringUtils.equals(f.getType().getName(), "java.lang.Long")||StringUtils.equals(f.getType().getName(), "long")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),(object==null||StringUtils.equals("", String.valueOf(object)))?(long)0:Long.valueOf(String.valueOf(object)));
			}else if(StringUtils.equals(f.getType().getName(), "java.lang.Float")||StringUtils.equals(f.getType().getName(), "float")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),(object==null||StringUtils.equals("", String.valueOf(object)))?(float)0:Float.valueOf(String.valueOf(object)));
			}else if(StringUtils.equals(f.getType().getName(), "java.lang.Short")||StringUtils.equals(f.getType().getName(), "short")){
				ReflectHelper.setValueByFieldName(obj, f.getName(),(object==null||StringUtils.equals("", String.valueOf(object)))?(short)0:Short.valueOf(String.valueOf(object)));
			}
		}
		return obj;
	}
	/*
	 * Highlight标红数据数组
	 * hight：为true时标红处理，false时不做处理
	 * 从引擎上获取集合数据（自动封装）
	 */
	public  List getSolrDocumentList(SolrDocumentList sids,Object obj,String[] Highlight,Boolean hight) throws Exception{
		List list=new ArrayList();
		ObjectMapper mapper=new ObjectMapper();
		for(SolrDocument sid:sids){
			sids.get(0);
			obj=getSolrDocument(sid,obj);
			//判断是否有标红数据
			if(Highlight!=null&&hight){
				String value=mapper.writeValueAsString(obj);
				for(String s:Highlight){
					value=value.replaceAll(s, "<font color='red'>"+s+"</font>");
				}
				obj=mapper.readValue(value, obj.getClass());
				value=null;
			}
			list.add(obj);
			obj=obj.getClass().newInstance();
		}
		return list;
	}
	public MyPage getPage(List list,Integer counts,MyPage page){
		page.setTotal(counts);
		page.setPages(counts/page.getSize()==0?counts/page.getSize():counts/page.getSize()+1);
		page.setRecords(list);
		return page;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.class.getTypeName());
		
	}
}
