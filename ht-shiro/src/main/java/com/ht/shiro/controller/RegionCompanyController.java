package com.ht.shiro.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.ExecutionError;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.StringUtil;
import com.ht.entity.shiro.SysPermission;
import com.ht.entity.shiro.SysRole;
import com.ht.entity.shiro.SysRolePermission;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.entity.shiro.regioncompany.RegionCompany;
import com.ht.shiro.service.SysPermissionService;
import com.ht.shiro.service.SysRolePermissionService;
import com.ht.shiro.service.SysRoleService;
import com.ht.shiro.service.regioncompany.RegionCompanyService;
import com.ht.utils.BaseEntityUtil;

import io.swagger.annotations.Api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jied
 */
@RestController
@RequestMapping("/ht-shiro/sysrc")
@Api(value="RegionCompanyController",description = "区域公司管理")
public class RegionCompanyController extends BaseController {

    @Autowired
    private RegionCompanyService regionCompanyService;
    
    /**
     * 查询用户列表  
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Respon list()throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		MyPage page = this.getMyPage(this.getPageData());
    		Page<RegionCompany> list=(Page<RegionCompany>)regionCompanyService.findListPage(page);
    		respon.success(list);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
    
    
    
    /**
     * 编辑修改角色
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @ResponseBody
    public Respon save(@RequestBody RegionCompany regionCompany)throws Exception{
    	boolean check=false; 
    	Respon respon = this.getRespon();
    	try {
    		BaseEntityUtil.getBaseEntity(regionCompany);
    		if(null!=regionCompanyService.getById( regionCompany.getId() )){ //编辑
				check=regionCompanyService.saveOrUpdate(regionCompany);
			}else{//新增
				if(null!=regionCompanyService.getbynumber(regionCompany.getNumber())){
					return 	respon.error("区域编号不能重复");
				}else{
					check=regionCompanyService.saveOrUpdate(regionCompany);
				}
			}
        	if(check) {
        		respon.success(null);
        	}else {
        		respon.error();
        	}
    	}catch(Exception e) {
    		respon.error();
    	}
    	return respon;
    }
    
    
    
    @GetMapping("/deletes/{ids}")
    public Respon deletes(@PathVariable String ids)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		String[] arrid=StringUtil.getStringArray(ids);
    		for(String id:arrid) {
    			regionCompanyService.removeById(id);
    		}
    		respon.success(null);
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }
    
    
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Respon delete(@PathVariable String id)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		boolean check=regionCompanyService.removeById(id);
    		if(check) {
    			respon.success(null);
    		}else {
    			respon.error();
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }
    
   
  
    
    

}
