package com.ht.commons.support.mysql;

public class Database {

	private String databaseName = "mysql";
	private String driverClass = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://47.92.70.12:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true";
	//private String url = "jdbc:mysql://47.92.70.12:3306/gaoqiyun_springboot?useUnicode=true&characterEncoding=utf-8&useSSL=true";
	private String username = "root";
	private String password = "gaoqiyun@123";

	
	public Database(){
		
	}
	
	public Database(boolean dataupload){
		if(dataupload){
			this.url="jdbc:mysql://47.92.70.12:3306/gaoqiyun_springboot?useUnicode=true&characterEncoding=utf-8&useSSL=true";
		}else{
			this.url="jdbc:mysql://47.92.70.12:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true";
		}
	}
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
