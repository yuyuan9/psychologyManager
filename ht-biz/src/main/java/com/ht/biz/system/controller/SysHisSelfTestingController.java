package com.ht.biz.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.HisSelfTestingService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.excel.Excel;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.freeassess.HisSelfTesting;
import com.ht.entity.biz.honeymanager.PaymentOrder.bustype;
import com.ht.entity.shiro.constants.UserType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/syshisself")
@Api(value="SysHisSelfTestingController",description = "免费评估后台管理")
public class SysHisSelfTestingController extends BaseController{
	
	@Autowired
	private HisSelfTestingService hisSelfTestingService;
	
	@ApiOperation(value="后台免费评估查询接口",httpMethod ="POST",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "total", value = "得分",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage<HisSelfTesting> page=getMyPage(pd);
			List<HisSelfTesting> list=hisSelfTestingService.findList(page);
			for(HisSelfTesting h:list){
				if(StringUtils.isNotBlank(h.getCreateid())){
					h.setCreateid(UserType.getValue(h.getCreateid()));
				}
				if(StringUtils.isNotBlank(h.getProductServ())){
					String[] str=h.getProductServ().split("\\*");
					h.setProductServ(str[str.length-1]);
				}
			}
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="后台免费评估单条数据查询接口",httpMethod ="GET",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			HisSelfTesting h=new HisSelfTesting();
			if(StringUtils.isNotBlank(id)){
				h=hisSelfTestingService.getById(id);
			}
			respon.success(h);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="后台免费评估数据删除接口",httpMethod ="GET",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "数据id集合",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(ids)){
				for(String s:ids.split(",")){
					hisSelfTestingService.removeById(s);
				}
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="后台免费评估导出接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "数据id",  dataType = "string"),
	})
	@GetMapping("export")
	public void export(String ids,HttpServletResponse response,String suffix,String fileName,String workName) throws Exception{
		if(StringUtils.isNotBlank(ids)){
			List<String> listStr=new ArrayList<String>();
			for(String s:ids.split(",")){
				listStr.add(s);
			}
			List<HisSelfTesting> list=(List<HisSelfTesting>) hisSelfTestingService.listByIds(listStr);
			for(HisSelfTesting h:list){
				if(StringUtils.isNotBlank(h.getProductServ())){
					String[] str=h.getProductServ().split("\\*");
					h.setProductServ(str[str.length-1]);
				}
			}
			String[] Columns=new String[]{"companyName","orgcode","createdate","province","city","area","productServ","total","intellectualProperty","totalAssets"};
			String[] titles=new String[]{"企业名称","社会统一信用代码","评估时间","省","市","区","技术领域","得分","知识产权","财务成长性（得分）"};
			Excel.excelAppoint(response, list, new HisSelfTesting(), suffix, fileName, workName, Columns, titles);
		}
	}
}
