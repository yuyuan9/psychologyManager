package com.ht.biz.service.impl.hisself.estimate;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ht.commons.utils.ReflectHelper;




/**
 * 读取 XML
 * @author jied
 *
 */
public class ReadXml<T> {
	
	private Class<?> clazz;
	
	public static List xmllist;
	
	/*默认文件*/
	private String getDefault(){
		return StringUtils.lowerCase(clazz.getSimpleName())+".xml";
	}
	
	private static ReadXml readXML=null;
	
	
	
	public ReadXml(Class<?> _clazz){
		this.clazz=_clazz;
		xmlConvert();
	}
	
	public static ReadXml getInstance(Class<?> _clazz){
		readXML=null;
		if(readXML==null) {
			synchronized (ReadXml.class) {
				readXML=new ReadXml(_clazz);
			}
		}
		return readXML;
	}
	
	
	public InputStream getInputStream(){
		return getInputStream(getDefault());
	}
	
	public InputStream getInputStream(String filePath) {
		InputStream is=null;
		if(StringUtils.isBlank(filePath)){
			is=clazz.getResourceAsStream(getDefault());
		}else{
			is=clazz.getResourceAsStream(filePath);
		}
		return is;
	}
	
	public List getList(){
		return xmllist;
	}
	
	//@Override
	public List xmlConvert() {
		List list=null;
		try {
			SAXReader reader = new SAXReader();  
		    InputStream is =getInputStream();
			Document document = reader.read(is);
			Element root = document.getRootElement();
			list=readElement(root);
			xmllist=list;
			
			if(is!=null){
				is.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	private List readElement(Element el) throws Exception{
		List<T> list = new ArrayList<T>();
		
		List<Element> eles=el.elements();
		
		for(Element e : eles){
			Object obj = this.clazz.newInstance();
			Field[] fields=obj.getClass().getDeclaredFields();
			List<Element> childs=e.elements();
			for(Field f:fields){
				for(Element child:childs){
					String attr=child.attribute("name").getText();
					if(StringUtils.equalsIgnoreCase(attr, f.getName())){
						ReflectHelper.setFieldValue(obj, f.getName(), child.getTextTrim());
					}
				}
			}
			
			list.add((T)obj);
		}
		
		return list;
		
	}
	
	
	
	
	
	
	
	
	

}
