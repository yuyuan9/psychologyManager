package com.ht.biz.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.utils.LoginUserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.ht.biz.service.SysRegionSetService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.SysRegionSet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/ht-biz/sysregionset")
@Api(value = "SysRegionSetController", description = "省份设置")
public class SysRegionSetController extends BaseController {
	
	@Autowired
	private SysRegionSetService sysRegionSetService;
	
	 /**
     * 查询用户列表  
     * @return
     */
    @GetMapping("/list")
	public  Respon list()throws Exception{
		Respon respon = this.getRespon();
		try {
			PageData pd = this.getPageData();
			QueryWrapper que = new QueryWrapper(  );
			if(StringUtils.isNotBlank( pd.getString( "name" ) )){
				que.like( "name", pd.getString( "name" ));
			}
			List<SysRegionSet> list= sysRegionSetService.list(que);
			TreeBuilder treeBuilder = new TreeBuilder(new ArrayList<TreeEntity>(list));
			String city = treeBuilder.buildJsonTree();
			respon.success(city);
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}

   //查询第一级
	@GetMapping("/firstregion")
	public  Respon firstregion()throws Exception{
		Respon respon = this.getRespon();
		try {
			QueryWrapper que = new QueryWrapper(  );
            que.isNull( "pid" );
			que.eq( "active",1 );
			que.orderByDesc( "sort" );
			List<SysRegionSet> list= sysRegionSetService.list(que);
			respon.success(list);
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}

	@GetMapping("/chileregion")
	public  Respon chileregion(String id)throws Exception{
		Respon respon = this.getRespon();
		try {
			QueryWrapper que = new QueryWrapper(  );
			que.eq( "pid",id );
			que.eq( "active",1 );
			que.orderByDesc( "sort" );
			List<SysRegionSet> list= sysRegionSetService.list(que);
			respon.success(list);
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}
	@GetMapping("/findall")
	public  Respon findall() {
		Respon respon = this.getRespon();
		try {
			QueryWrapper<SysRegionSet> que = new QueryWrapper(  );
			que.eq( "active",1 );
			que.orderByDesc( "sort" );
			List<SysRegionSet> list =sysRegionSetService.list(que);

			respon.success(list);
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}
    @ApiOperation(value="区域设置新增修改，传id修改，不传id新增")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "name", value = "名称", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "regionid", value = "区域编号，从系统区域查询上来", dataType = "String"),
	})
    @PostMapping("/save")
    public Respon save(@RequestBody SysRegionSet sysregionset) {
    	Respon respon = this.getRespon();
    	try {
    		if(null==sysregionset.getId()){
    			sysregionset.setCreateid( LoginUserUtils.getUserId() );
				sysregionset.setCreatedate( new Date(  ) );
			}
    		sysRegionSetService.saveOrUpdate(sysregionset);
    		respon.success("修改成功");
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
    
    @ApiOperation(value="区域设置删除")
   	@ApiImplicitParams({
           @ApiImplicitParam(paramType="query",name = "id", value = "id", dataType = "String"),
   	})
    @GetMapping("/delete/{id}")
    public Respon delete(@PathVariable String id) {
    	Respon respon = this.getRespon();
    	try {
			QueryWrapper<SysRegionSet> que = new QueryWrapper(  );
			que.eq( "pid",id );
			int count = sysRegionSetService.count(  que);
			if(count>0){
				return respon.error( "请删除子节点" );
			}
    		sysRegionSetService.removeById(id);
    		respon.success(null);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }
//    @ApiOperation(value="区域设置批量删除")
//   	@ApiImplicitParams({
//           @ApiImplicitParam(paramType="query",name = "ids", value = "ids", dataType = "String"),
//   	})
//    @GetMapping("/deletes/{ids}")
//    public Respon deletes(@PathVariable String ids) {
//    	Respon respon = this.getRespon();
//    	try {
//    		List<Integer> list=new ArrayList<Integer>();
//    		String[] arr = StringUtil.getStringArray(ids);
//    		for(String id : arr) {
//    			list.add(Integer.valueOf(id));
//    		}
//    		sysRegionSetService.removeByIds(list);
//    		respon.success(null);
//    	}catch(Exception e) {
//    		respon.error(e);
//    	}
//    	return respon;
//    }

}
