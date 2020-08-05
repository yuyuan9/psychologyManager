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
import com.ht.shiro.service.SysPermissionService;
import com.ht.shiro.service.SysRolePermissionService;
import com.ht.shiro.service.SysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

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
@RequestMapping("/ht-shiro/sysrole")
@Api(value="SysRoleController",description = "后台角色管理")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysPermissionService sysPermissionService;
    
    /**
     * 查询用户列表
     * @return
     */
    @ApiOperation(value="获取普通用户列表", notes="查询普通用户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pd", value = "Map集合", required = false, dataType = "PageData"),
    })
    @GetMapping("/list")
    @ResponseBody
    public Respon list()throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		MyPage mypage= this.getMyPage(getPageData());
    		Page<SysRole> list=(Page<SysRole>)sysRoleService.findListpage(mypage);
    		respon.success(list);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
    
    
    
    /**
     * 编辑修改角色
     * @param role
     * @return
     * @throws Exception
     */
    @ApiOperation(value="获取普通用户列表", notes="查询普通用户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "role", value = "名称", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "code", value = "编码", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "description", value = "描述", required = false, dataType = "String"),
    })
    @PostMapping("/save")
    @ResponseBody
    public Respon save(@RequestBody SysRole role)throws Exception{
    	boolean check=false; 
    	Respon respon = this.getRespon();
    	try {
    		check=sysRoleService.saveOrUpdate(role);
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
    
    /**
     *  角色授权资源
     *  以分号分隔开来如permid=1;2;3 或者 数组
     * 先删除所有这个角色资源关联，然后再新增
     * @param roleid 角色id
     * @param permissionid 资源id  可以是数组，可以是;号分隔的字符串
     * @return
     * @throws Exception
     */
    @ApiOperation(value="根据角色id关联资源权限", notes="角色管理资源权限")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "roleid", value = "角色id", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "permissionid", value = "资源id（数组）", required = true, dataType = "String[]"),
    })
    @GetMapping("/saveRolePermission/{roleid}/{permissionid}")
    @ResponseBody
    public Respon saveRolePermission(@PathVariable String roleid,@PathVariable String ...permissionid)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		if(permissionid.length==1) {
    			permissionid=StringUtil.stringArraySemicolon(permissionid[0]);
    		}
    		boolean check=sysRolePermissionService.saveRolePermission(roleid,permissionid);
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
    
    @ApiOperation(value="根据角色id查看详细", notes="查看详细")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "id", value = "角色id", required = true, dataType = "Integer"),
    })
    @GetMapping("/detail/{id}")
    @ResponseBody
    public Respon detail(@PathVariable Integer id)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		SysRole role=sysRoleService.getById(id);
    		respon.success(role);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
    
    @ApiOperation(value="根据id删除角色", notes="根据角色id删除角色信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "ids", value = "角色id集合以,或者;分割", required = true, dataType = "String"),
    })
    @GetMapping("/deletes/{ids}")
    @ResponseBody
    public Respon deletes(@PathVariable String ids)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		String[] arr=StringUtil.getStringArray(ids);
    		List<Integer> idarr = new ArrayList<Integer>();
    		for(String id:arr) {
    			idarr.add(Integer.valueOf(id));
    		}
    		boolean check=sysRoleService.removeByIds(idarr);
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
    
    @ApiOperation(value="根据id删除角色", notes="根据角色id删除角色信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "Integer"),
    })
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Respon delete(@PathVariable Integer id)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		boolean check=sysRoleService.deleted(id);
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
    
   
    /**
     * 根据角色id查询资源权限
     * @param roleid
     * @return
     * @throws Exception
     */
    @ApiOperation(value="根据角色id获取权限资源", notes="根据角色id查询资源")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "roleid", value = "角色id", required = true, dataType = "String"),
    })
    @GetMapping("/getPermByRoleId/{roleid}")
    @ResponseBody
    public Respon getPermByRoleId(@PathVariable String roleid)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		List<SysPermission> list=sysPermissionService.findallPermsByRoleId(Integer.valueOf(roleid));
    	    respon.success(list);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
    
    @ApiOperation(value="根据角色id禁用启用", notes="根据角色id禁用启用")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "id", value = "角色id", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "active", value = "boolean 禁用启用状态", required = true, dataType = "String"),
    })
    @GetMapping("/onoff/{id}/{active}")
    @ResponseBody
    public Respon onoff(@PathVariable String id,@PathVariable Boolean active)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		SysRole sysRole=sysRoleService.getById(id);
    		if(active!=null) {
    			sysRole.setActive(active); 
    			sysRoleService.saveOrUpdate(sysRole);
    			respon.success(null);
    		}else {
    			respon.error("状态为空，拒绝修改");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }
    
    

}
