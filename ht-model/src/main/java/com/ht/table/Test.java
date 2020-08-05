package com.ht.table;

import com.ht.entity.biz.citytree.SysRegionSet;
import com.ht.entity.shiro.SysPermission;
import com.ht.entity.shiro.SysRolePermission;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.SysUserRole;
import com.ht.entity.shiro.regioncompany.RegionCompany;
import com.ht.entity.shiro.usertype.CompanyBank;
import com.ht.entity.shiro.usertype.CompanyUser;

public class Test {
	public static void main(String[] args) {
		
		create(CompanyUser.class);
		//create(SysUser.class);

	}

	private static void create(Class clz) {
		createTable1(clz);
		System.out.println();
		System.out.println();
		System.out.println("=====================");
		System.out.println();
		System.out.println();
		//createMapper1(clz);
	}

	private static void createMapper1(Class clz) {
		CreateMapper map = new CreateMapper(clz);
		System.out.println(map.createMapperXml());
	}

	
	private static void createTable1(Class clz) {
		CreateTableSql sql = new CreateTableSql(clz);

		System.out.println(sql.createTable());
	}

	

}
