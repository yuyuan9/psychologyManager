package com.ht.biz.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.CatalogService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.entity.biz.catalog.Catalog;
import com.ht.entity.biz.catalog.CatalogTree;
import com.ht.entity.biz.vo.VantOption;
import com.ht.entity.policydig.PolicyDig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping(value="/ht-biz/catalog")
@Api(value="CatalogController",description = "技术领域等数据查询前端管理")
public class CatalogController extends BaseController{
	
	@Autowired
	private CatalogService catalogService;

	@ApiOperation(value="处理技术领域等下拉框")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "type", value = "compreg(注册类型)，industry(产业领域)，product(产品类型)，techfield(技术领域)",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "grade", value = "目录级别(1、2、3)",  dataType = "String"),
	})
	@GetMapping("getjson")
	public Respon getjson(String type,String grade) throws Exception{
		Respon respon=this.getRespon();
		List<Catalog> list=getList(type,grade);
		respon.success(list);
		return respon;
	}
	
	
	@GetMapping("getAppJson")
	public Respon getAppJson(String type,String grade)throws Exception{
		Respon respon=this.getRespon();
		VantOption option = null;
		List<VantOption> list = new ArrayList<VantOption>();
		List<Catalog> listcl=getList(type,grade);
		option = new VantOption();
		option.setText("点击选择");
		option.setValue("");
		list.add(option);
		for(Catalog c : listcl) {
			option = new VantOption();
			option.setText(c.getName());
			option.setValue(c.getName());
			if(!StringUtils.equals(c.getName(), "普惠性")){
				list.add(option);
			}
		}
		return respon.success(list);
	}
	
	private List<Catalog> getList(String type,String grade)throws Exception{
		QueryWrapper<Catalog> qw=new QueryWrapper<Catalog>();
		qw.orderByAsc("id");
		if(StringUtils.isNoneBlank(grade)){
			qw.eq("grade", grade);
			qw.and(i -> i.eq("type", type).or().eq("type", "all"));
		}else{
			qw.eq("type", type);
		}
		List<Catalog> list=catalogService.list(qw);
		list=new CatalogTree(list).getList();
		return list;
	}
}
