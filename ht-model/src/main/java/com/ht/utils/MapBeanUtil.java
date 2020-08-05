package com.ht.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.ReflectHelper;

public class MapBeanUtil {

	public  Map<String, Object> beanToMapNew(Object object) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = ReflectHelper.getFieldAll(object.getClass());
		for (Field field : fields) {

			field.setAccessible(true);
			String typeName = field.getGenericType().getTypeName();
			if (typeName.equals(Boolean.class.getName())) {
				if (field.get(object).toString().equals(Boolean.TRUE.toString())) {
					map.put(field.getName(), "是");
				} else {
					map.put(field.getName(), "否");
				}
			} else if (typeName.equals(Integer.class.getName()) 
					|| typeName.equals(String.class.getName()) || typeName.equals(Double.class.getName())
					|| typeName.equals(Float.class.getName()) || typeName.equals(Short.class.getName())
					|| typeName.equals(Long.class.getName()) || typeName.equals(Character.class.getName())
					 ) {
				
				map.put(field.getName(), field.get(object));
			} else if(typeName.equals(Date.class.getName())){
				Date o=(Date)field.get(object);
				if(o!=null){
					map.put(field.getName(), DateUtil.dateToStr((Date)field.get(object), 11));
				}
			}
		}

		return map;
	}

	public  Map<String, Object> beanAllToMap(Object object) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = ReflectHelper.getFieldAll(object.getClass());
		for (Field field : fields) {

			field.setAccessible(true);
			if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
				if (field.get(object)==null) {
					map.put(field.getName(), "");
				} else if(field.get(object).toString().equals("true")){
					map.put(field.getName(), "是");
				}else{
					map.put(field.getName(), "否");
				}
			} else if(field.getGenericType().toString().equals("class java.util.Date")){
				Date o=(Date)field.get(object);
				if(o!=null){
					map.put(field.getName(), DateUtil.dateToStr((Date)field.get(object), 11));
				}
			} else {
				map.put(field.getName(), field.get(object));
			}
		}

		return map;
	}

	public  Map<String, Object> beanToMap(Object object) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Class cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {

			field.setAccessible(true);
			if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
				if (field.get(object).toString().equals("true")) {
					map.put(field.getName(), "是");
				} else {
					map.put(field.getName(), "否");
				}
			} else {
				map.put(field.getName(), field.get(object));
			}
		}

		return map;
	}

	public  Map<String, String> beanToMap0(Object object) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		Class cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {

			field.setAccessible(true);

			if (field.getGenericType().toString().equals("class java.lang.Boolean")) {

				if (field.get(object).toString().equals("true")) {
					map.put(field.getName(), "是");
				} else {
					map.put(field.getName(), "否");
				}
			} else {
				map.put(field.getName(), String.valueOf(field.get(object)));
			}
		}
		return map;
	}

	public  Object mapToBean(Map<String, Object> map, Class cls)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Object object = cls.getInterfaces();
		for (String key : map.keySet()) {
			Field field = cls.getDeclaredField(key);
			field.setAccessible(true);
			field.set(object, map.get(key));

		}

		return object;

	}

	public  Map<String, Object> bean2Map(Object object) throws IllegalArgumentException, IllegalAccessException {

		Map<String, Object> map = new HashMap<String, Object>();
		Class cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {

			field.setAccessible(true);
			map.put(field.getName(), field.get(object));

		}
		return map;
	}
	public  Map<String, Object> beanToMaplist(List list,String name) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			Field[] fields = ReflectHelper.getFieldAll(list.get(i).getClass());
			for (Field field : fields) {
				if(!StringUtils.isBlank(name)){
					map.put(name+(i+1), i+1);
				}
				field.setAccessible(true);
				String typeName = field.getGenericType().getTypeName();
				if (typeName.equals(Boolean.class.getName())) {
					if (field.get(list.get(i)).toString().equals(Boolean.TRUE.toString())) {
						map.put(name+field.getName()+(i+1), "是");
					} else {
						map.put(name+field.getName()+(i+1), "否");
					}
				} else if (typeName.equals(Integer.class.getName()) 
						|| typeName.equals(String.class.getName()) || typeName.equals(Double.class.getName())
						|| typeName.equals(Float.class.getName()) || typeName.equals(Short.class.getName())
						|| typeName.equals(Long.class.getName()) || typeName.equals(Character.class.getName())
						 ) {
					
					map.put(name+field.getName()+(i+1), field.get(list.get(i)));
				} else if(typeName.equals(Date.class.getName())){
					map.put(name+field.getName()+(i+1), field.get(list.get(i))==null?"无":DateUtil.dateToStr((Date)field.get(list.get(i)), 11));
				}
			}
		}
		return map;
	}
}
