package com.ht.biz.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(value="/ht-biz/unit")
@Api(value="UnitController",description = "主管单位前端获取")
public class UnitController extends BaseController{
	
	@Autowired
	private UnitService unitService;
	
	@ApiOperation(value="主管单位数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "region", value = "所属省市区",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "afterUnitName", value = "主管部门名称",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			String[] ss=new String[]{"国家","省","市","区"};
			List<Unit> lists=new ArrayList<Unit>();
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
			List<Unit> list=unitService.list(qw);
			for(String s:ss){
				for(Unit u:list){
					if(StringUtils.equals(s, u.getRegion())){
						lists.add(u);
					}
				}
			}
			list=null;
			respon.success(lists);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
