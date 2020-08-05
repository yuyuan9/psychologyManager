package com.ht.table;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ht.commons.utils.ReflectHelper;


/*
 * 根据类生成创表sql
 */
public class CreateTableSql {
	
	private String key = "id";
	
	private Class clazz;

	private String prefix = CreateUtils.prefix;

	public CreateTableSql(Class clazz) {
		this.clazz = clazz;
	}
	
	private String createField(Field f) {
		String s=StringUtils.EMPTY;
		String type = f.getGenericType().toString();
		type=StringUtils.removeStart(type, "class ");
		//System.out.println(Boolean.class.getName());
		
		//System.out.println(f.getGenericType().toString());
		//System.out.println(f.getGenericType().toString().equals(Boolean.class.getName()));
		if(type.equals(Integer.class.getName())
				|| type.equals(Boolean.class.getName())){
			if(key.equals(f.getName())){
				s= f.getName()+" int(11) NOT NULL AUTO_INCREMENT ";
			}else{
				s= f.getName()+" int  DEFAULT NULL ";
			}
			
		}else if(type.equals(String.class.getName())){
			s=f.getName()+" varchar(255) NULL ";
		}else if(type.equals(Double.class.getName())
				|| type.equals(Float.class.getName())){
			s=f.getName()+" double DEFAULT NULL ";
		}else if(type.equals(Date.class.getName())){
			s=f.getName()+" timestamp NULL DEFAULT NULL ";
		}
		
		return StringUtils.isNotBlank(s)?s+",":StringUtils.EMPTY;
	}
	
	public String createTable(){
		Field[] fields = ReflectHelper.getAllField(clazz);
		StringBuffer sql= new StringBuffer();
		sql.append(dropTable()).append("\n");
		sql.append("CREATE TABLE ").append(CreateUtils.getTableName(this.prefix,this.clazz)).append("(").append("\n");
		for(Field f:fields){
			String ftr=createField(f);
			if(!StringUtils.isBlank(ftr)){
				sql.append(ftr).append("\n");
			}
		}
		sql.append(" PRIMARY KEY (`id`) ").append("\n");
		sql.append(" ) ").append("\n");
		sql.append(" ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8; ").append("\n");
		return sql.toString();
	}
	
	private String dropTable(){
		StringBuffer sql= new StringBuffer();
		sql.append("DROP TABLE IF EXISTS ").append(CreateUtils.getTableName(this.prefix,this.clazz)).append(";");
		return sql.toString();
	}

	/*public String getTableName(String prefix) {
		String name = this.clazz.getSimpleName();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length(); i++) {
			if (Character.isUpperCase(name.charAt(i))) {
				sb.append("_" + StringUtils.lowerCase(name.charAt(i) + ""));
			} else {
				sb.append(name.charAt(i));
			}
		}
		return prefix + sb.toString();
	}*/
	

}
