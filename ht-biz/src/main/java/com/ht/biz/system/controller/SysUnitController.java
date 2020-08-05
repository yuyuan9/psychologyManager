package com.ht.biz.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.UnitService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.unit.Unit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/sysunit")
@Api(value="SysUnitController",description = "主管单位后台管理")
public class SysUnitController  extends BaseController{
	
	@Autowired
	private UnitService unitService;
	
	@ApiOperation(value="主管单位数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "region", value = "所属省市区",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "afterUnitName", value = "主管部门名称",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			String[] ss=new String[]{"国家","省","市","区"};
			List<Unit> lists=new ArrayList<Unit>();
			MyPage<Unit> page=getMyPage(pd);
			QueryWrapper<Unit> qw=new QueryWrapper<Unit>();
			if(pd.get("afterUnitName")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("afterUnitName")))){
				qw.and(i ->i.like("afterUnitName", pd.get("afterUnitName")));
			}
			if(pd.get("region")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("region")))){
				qw.and(i ->i.like("region", pd.get("region")));
			}
			if(pd.get("beforeUnitName")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("beforeUnitName")))){
				qw.and(i ->i.like("beforeUnitName", pd.get("beforeUnitName")));
			}
			if(pd.get("alias")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("alias")))){
				qw.and(i ->i.like("alias", pd.get("alias")));
			}
			page=(MyPage<Unit>) unitService.page(page, qw);
			List<Unit> list=page.getRecords();
			for(String s:ss){
				for(Unit u:list){
					if(StringUtils.equals(s, u.getRegion())){
						lists.add(u);
					}
				}
			}
			list=null;
			page.setRecords(lists);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="获取主管单位数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		Unit unit=new Unit();
		try {
			if(!StringUtils.isBlank(id)){
				unit=unitService.getById(id);
			}
			respon.success(unit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="保存主管单位数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "beforeUnitName", value = "前端展示",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "afterUnitName", value = "后台展示",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "alias", value = "别称",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "region", value = "所属省市区",  dataType = "string"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody Unit unit){
		Respon respon=this.getRespon();
		try {
			Map<String ,Object > map=getLoginInfo();
			if(!StringUtils.isBlank(unit.getId())){
				unit.setLastmodified(new Date());
				unitService.updateById(unit);
			}else{
				unit.setCreatedate(new Date());
				if(map!=null){
					unit.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					unit.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				unitService.save(unit);
			}
			respon.success(unit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="删除主管单位数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String id){
		Respon respon=this.getRespon();
		try {
			if(!StringUtils.isBlank(id)){
				unitService.removeById(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
