package com.ht.biz.system.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.PolicyDigService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyDig;
import com.ht.entity.policydig.PolicydigVo;

import ch.qos.logback.classic.pattern.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/syspolicydig")
@Api(value="PolicyDigController",description = "政策速递后台管理")
public class SysPolicyDigController extends BaseController{
	@Autowired
    private PolicyDigService policyDigService;
	private static final String speProvince="北京市天津市上海市重庆市深圳市";
	/*
	 * 后台政策速递列表接口（分页）
	 */
	@ApiOperation(value="后台政策速递列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省份" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "datetime1", value = "开始发布时间", dataType = "date"),
        @ApiImplicitParam(paramType="query",name = "datetime2", value = "结束发布时间", dataType = "date"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "status", value = "状态", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "productType", value = "政策类型", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "source", value = "来源", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "total", value = "查询总数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "size", value = "每页条数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "pages", value = "总页数", dataType = "int"),
	})
	
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			//System.out.println(pd);
			MyPage page=getMyPage(pd);
			if(pd.get("datetime1")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("datetime1")))){
				String createdate1=String.valueOf(pd.get("datetime1"));
				Date date=DateUtil.StrTodate(createdate1, "yyyy-MM-dd");
				date=DateUtil.addDay(date);
				pd.add("datetime1", DateUtil.dateToStr(date, 11)+" 00:00:00");
			}
			if(pd.get("datetime2")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("datetime2")))){
				String createdate1=String.valueOf(pd.get("datetime2"));
				Date date=DateUtil.StrTodate(createdate1, "yyyy-MM-dd");
				date=DateUtil.addDay(date);
				pd.add("datetime2", DateUtil.dateToStr(date, 11)+" 23:59:59");
			}
			page.setPd(pd);
			page.setRecords(policyDigService.findlistPage(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		
		return respon;
	}
	
	@ApiOperation(value="后台政策单条速递查询接口",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "前端传递id",  dataType = "String"),
	})
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public Respon edit(String id) throws Exception{
		Respon respon = this.getRespon();
		PolicyDig policyDig=new PolicyDig();
		int i=0;
		try {
			if(!StringUtils.isBlank(id)){
				policyDig=policyDigService.findById(id);
				if(StringUtils.isNotBlank(policyDig.getTecnology())){
					policyDig.setPv(new PolicydigVo(policyDig.getTecnology(),policyDig.getProduct(),policyDig.getIndustry(),null));
				}
				QueryWrapper<PolicyDig> qw=new QueryWrapper<PolicyDig>();
				qw.eq("title", policyDig.getTitle());
				qw.eq("status", 1);
				i=policyDigService.count(qw);
			}
			respon.success(policyDig);
			respon.setReserveData(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	
	@ApiOperation(value="后台政策单条速递保存接口",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "policyDig", value = "policyDig数据封装",  dataType = "policyDig"),
	})
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Respon save(@RequestBody PolicyDig policyDig) throws Exception{
		Respon respon = this.getRespon();
		try {
			//特殊领域赋值（记录一级领域），方便查询
			policyDig.setFirst();
			if(StringUtils.isNotBlank(policyDig.getProvince())&&speProvince.contains(policyDig.getProvince())){
				policyDig.setCity(policyDig.getProvince());
			}
			if(StringUtils.isBlank(policyDig.getId())){
				policyDig.setCreatedate(new Date());
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					policyDig.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					policyDig.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				policyDigService.insert(policyDig);
			}else{
				policyDig.setLastmodified(new Date());
				policyDigService.edit(policyDig);
			}
			respon.success(policyDig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
	
	@ApiOperation(value="后台政策单条速递删除接口",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "policyDig", value = "policyDig数据封装",  dataType = "policyDig"),
        @ApiImplicitParam(paramType="query",name = "status", value = "status==2，完全从数据库移除；status！=2，修改其状态为2",  dataType = "policyDig"),
	})
	
	@RequestMapping(value="/deleted",method=RequestMethod.GET)
	public Respon deleted(PolicyDig policyDig) throws Exception{
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(policyDig.getId())){
				if(policyDig.getStatus()==2){
					policyDigService.deleted(policyDig);
				}else{
					policyDig.setStatus(2);
					policyDigService.edit(policyDig);
				}
			}
			respon.success(policyDig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	
	@ApiOperation(value="后台政策速递批量删除接口",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "policyDig", value = "policyDig数据封装",  dataType = "policyDig"),
        @ApiImplicitParam(name = "status", value = "status==2，完全从数据库移除；status！=2，修改其状态为2",  dataType = "policyDig"),
	})
	
	@RequestMapping(value="/deletedAll",method=RequestMethod.GET)
	public Respon deleted(String ids,String status) throws Exception{
		Respon respon = this.getRespon();
		PageData pd = this.getPageData();
		try {
			if(!StringUtils.isBlank(ids)&&!StringUtils.isBlank(status)){
				if(StringUtils.equals("2", status)){
					for(String id:ids.split(",")){
						policyDigService.deleteById(id);
					}
				}else{
					pd.add("status", "2");
					ids=ids.replace(",", "','");
					ids="'"+ids+"'";
					pd.add("ids", ids);
					policyDigService.updateStatus(pd);
				}
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策单条速递置顶接口",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "top", value = "policyDig数据封装",  dataType = "policyDig"),
	})
	@RequestMapping(value="/top",method=RequestMethod.POST)
	public Respon top(@RequestBody PolicyDig policyDig) throws Exception{
		Respon respon = this.getRespon();
		try {
			if(policyDig.getTop()==1){
				policyDig.setOriginalDate(new Date());
			}else{
				policyDig.setOriginalDate(null);
			}
			policyDigService.updateTop(policyDig);
			respon.success(policyDig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策速递当天更新条数",notes="返回json")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
	})
	@GetMapping(value="/updateToday")
	public Respon updateToday(){
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			QueryWrapper<PolicyDig> qw=new QueryWrapper<PolicyDig>();
			qw.like("createdate", DateUtil.dateToStr(new Date(), 11));
			if(pd.get("province")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("province")))){
				qw.like("province", pd.get("province"));
			}
			if(pd.get("city")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("city")))){
				qw.like("city", pd.get("city"));
			}
			if(pd.get("area")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("area")))){
				qw.like("area", pd.get("area"));
			}
			Integer counts=policyDigService.count(qw);
			counts=counts==null?0:counts;
			respon.success(counts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

}
