package com.ht.table;

import java.lang.reflect.Field;
import java.util.Date;

import com.ht.commons.utils.ReflectHelper;


public class CreateMapper {

	private String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" "
			+ "	\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";

	private Class clazz;

	private String key = "id";

	private String result = "<result column=\"%s\" property=\"%s\"/>";

	private String update = "<if test=\"%s !=null and %s !=''\">%s=#{%s}";
	private String ifstr = "</if>";

	private String n = "\n";

	private String prefix = CreateUtils.prefix;

	public CreateMapper(Class clazz) {
		this.clazz = clazz;
	}

	public String createMapperXml() {
		StringBuffer xml = new StringBuffer();
		xml.append(head).append(n);
		xml.append("<mapper namespace=\"" + getNamespace() + "\">").append(n);
		xml.append(getResultMap()).append(n);
		xml.append(getFindById()).append(n);
		xml.append(getFindlistPage()).append(n);
		xml.append(getInsert()).append(n);
		xml.append(getUpdate()).append(n);
		xml.append(getDeleted()).append(n);
		xml.append("</mapper>");
		return xml.toString();
	}
	
	public String getFindById(){
		StringBuffer xml = new StringBuffer();
		xml.append("<select id=\"findById\" resultMap=\""+clazz.getSimpleName()+"ResultMap\" parameterType=\"java.lang.String\">").append(n);
		xml.append(" select * from "+CreateUtils.getTableName(prefix, clazz)+" t where t.id=#{id}").append(n);
		xml.append("</select>").append(n);
		return xml.toString();
	}
	
	public String getFindlistPage(){
		StringBuffer xml = new StringBuffer();
		xml.append("<select id=\"findlistPage\" resultMap=\""+clazz.getSimpleName()+"ResultMap\" parameterType=\"page\">").append(n);
		xml.append(" select * from "+CreateUtils.getTableName(prefix, clazz)+" t where 1=1 order by t.createdate desc ").append(n);
		xml.append("</select>").append(n);
		return xml.toString();
	}
	
	

	private String getNamespace() {
		return clazz.getName() + "Mapper";
	}

	private String getInsert() {
		StringBuffer xml = new StringBuffer();
		xml.append(" <insert id=\"save\" parameterType=\"" + this.clazz.getName() + "\">").append(n);
		xml.append("insert into ").append(CreateUtils.getTableName(prefix, clazz)).append("(").append(n);
		Field[] fields = ReflectHelper.getAllField(clazz);
		int i = 0;
		for (Field f : fields) {
			i++;
			if (checkField(f)) {
				xml.append(f.getName());
				if (i != fields.length) {
					xml.append(",").append(n);
				} else {
					xml.append(n);
				}
			}
		}
		xml.append(")").append(n);
		xml.append("values(").append(n);

		int j = 0;
		for (Field f : fields) {
			j++;
			if (checkField(f)) {
				xml.append("#{" + f.getName() + "}");
				if (j != fields.length) {
					xml.append(",").append(n);
				} else {
					xml.append(n);
				}
			}
		}

		xml.append(")").append(n);
		xml.append("</insert>").append(n);

		return xml.toString();

	}

	private String getUpdate() {
		StringBuffer xml = new StringBuffer();

		xml.append("<update id=\"edit\" parameterType=\"" + clazz.getName() + "\">").append(n);
		xml.append("update ").append(CreateUtils.getTableName(prefix, clazz)).append(n);
		xml.append("<set>").append(n);
		Field[] fields = ReflectHelper.getAllField(clazz);
		int i = 0;
		for (Field f : fields) {
			i++;

			if (checkField(f)) {
				if (!key.equals(f.getName())) {
					xml.append(String.format(update, f.getName(), f.getName(), f.getName(), f.getName()));
					if (i != fields.length) {
						xml.append("," + ifstr);
					} else {
						xml.append(ifstr);
					}
					xml.append(n);
				}
			}
		}
		xml.append("</set>").append(n);
		xml.append(" where id=#{id} ").append(n);
		xml.append("</update>").append(n);
		return xml.toString();
	}
	
	public String getDeleted(){
		StringBuffer xml = new StringBuffer();
		xml.append("<delete id=\"deleted\" parameterType=\"java.lang.String\" >").append(n);
		xml.append("delete from "+CreateUtils.getTableName(prefix, clazz)+" where id=#{id}").append(n);
		xml.append("</delete>") ;
		return xml.toString();
	}

	private String getResultMap() {
		StringBuffer xml = new StringBuffer();

		xml.append("<resultMap type=\"" + clazz.getName() + "\" id=\"" + clazz.getSimpleName() + "ResultMap\">")
				.append(n);
		xml.append("<id column=\"id\" property=\"id\"/>").append(n);
		Field[] fields = ReflectHelper.getAllField(clazz);
		for (Field f : fields) {
			if (!key.equals(f.getName())) {
				xml.append(String.format(result, f.getName(), f.getName())).append(n);
			}
		}
		xml.append("</resultMap>").append(n);

		return xml.toString();
	}

	private boolean checkField(Field f) {
		String typeName = f.getGenericType().getTypeName();
		if (typeName.equals(Integer.class.getName()) || typeName.equals(Boolean.class.getName())
				|| typeName.equals(String.class.getName()) || typeName.equals(Double.class.getName())
				|| typeName.equals(Float.class.getName()) || typeName.equals(Short.class.getName())
				|| typeName.equals(Long.class.getName()) || typeName.equals(Character.class.getName())
				|| typeName.equals(Date.class.getName())) {
			return true;
		} else {
			return false;
		}
	}


}
