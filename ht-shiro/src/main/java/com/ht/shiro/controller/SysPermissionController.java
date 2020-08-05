package com.ht.shiro.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SysPermission;
import com.ht.shiro.service.SysPermissionService;
import com.ht.utils.TreeEntityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/ht-shiro/sysperm")
@Api(value="SysPermissionController",description = "后台资源管理")
public class SysPermissionController extends BaseController {

    @Autowired
    private SysPermissionService sysPermissionService;
    
    /**
     * 查询用户列表
     * @return
     */
    
    @ApiOperation(value="资源权限列表", notes="查询资源权限列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pd", value = "Map集合可以传各类参数", required = false, dataType = "String"),
    })
    @GetMapping("/list")
    @ResponseBody
    public Respon list()throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		System.out.println(getPageData());
    		MyPage mypage= this.getMyPage(getPageData());
    		Page<SysPermission> list=(Page<SysPermission>) sysPermissionService.findListpage(mypage);
    		respon.success(list);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
    
    @ApiOperation(value="资源权限树形列表", notes="资源权限树形列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pd", value = "Map集合可以传各类参数", required = false, dataType = "String"),
    })
    @GetMapping("/treelist")
    public Respon treelist()throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		MyPage mypage= this.getMyPage(getPageData());
    		List<SysPermission> list=(List<SysPermission>) sysPermissionService.findList(mypage);
    		System.out.println(list.size());
    		List<TreeEntity> source = new ArrayList<TreeEntity>();
    		source.addAll(list);
    		List<TreeEntity> treelist = new TreeBuilder(source).buildTree();
    		respon.success(treelist);
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
    
    @ApiOperation(value="资源权限列表", notes="查询资源权限列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "path", value = "前端路由", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "component", value = "组件  配置调整到后台的文件夹", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "redirect", value = "重定向", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "name", value = "名称，后台使用", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "title", value = "标题", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "icon", value = "后台图标", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "url", value = "统一资源管理", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "menu", value = "0为功能权限，1为菜单", required = false, dataType = "Integer"),
        @ApiImplicitParam(paramType="query", name = "remark", value = "备注", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "sort", value = "排序", required = false, dataType = "Integer"),
    })
    @PostMapping("/save")
    @ResponseBody
    public Respon save(@RequestBody SysPermission permission)throws Exception{
    	System.out.println("begin");
    	boolean check=false;
    	Respon respon = this.getRespon();
    	try {
    		TreeEntityUtil.getTreeEntity(permission);
    		check=sysPermissionService.saveOrUpdate(permission);
        	if(check) {
        		respon.success(null);
        	}else {
        		respon.error();
        	}
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error();
    	}
    	return respon;
    }
    
    
    @ApiOperation(value="资源详细", notes="根据id查询资源详细")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "Integer"),
    })
    @GetMapping("/detail/{id}")
    @ResponseBody
    public Respon detail(@PathVariable Integer id)throws Exception{
    	Respon respon = this.getRespon();
    	try { 
    		SysPermission permission = sysPermissionService.getById(id);
    		respon.success(permission);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
    
  
    @ApiOperation(value="删除资源", notes="根据id删除资源")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "Integer"),
    })
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Respon delete(@PathVariable Integer id)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		boolean check=sysPermissionService.removeById(id);
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
