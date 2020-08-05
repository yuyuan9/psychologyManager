package com.ht.biz.controller.app;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.CompanyInfoRzService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.companypolicy.CompanyInfoRz;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/ht-biz/app/highcompany")
@Api(value="highCompanyAppController",description = "手机端高企接口管理")
public class HighCompanyAppController extends BaseController{
	
	private static final String speProvince="北京市天津市上海市重庆市";
	
	@Autowired
	private CompanyInfoRzService companyInfoRzService;
	
	@ApiOperation(value="跳转到高企页面")
	@GetMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("/app/highcompany/"+page);
	}
	
	@ApiOperation(value="高企数据列表")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "type", value = "认定类型",  dataType = "String"),
	})
	@RequestMapping(value="list",method= {RequestMethod.GET,RequestMethod.POST})
	public Respon list(MyPage<CompanyInfoRz> page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			int flag=0;//页数等于1
			if(page.getCurrent()>1){
				SysUser user=LoginUserUtils.getLoginUser();
				if(user==null){
					flag=1;//页数大于1未登录
				}else{
					flag=2;//页数大于1已登录
				}
			}
			if(flag!=1){
				if(StringUtils.contains(speProvince, pd.getString("province"))){
					pd.add("province", pd.getString("province"));
					pd.add("city", pd.getString("area"));
					pd.add("area", null);
				}
				String keyword=pd.get("keyword")==null?"":String.valueOf(pd.get("keyword"));
				String province=pd.get("province")==null?"":String.valueOf(pd.get("province"));
				String city=pd.get("city")==null?"":String.valueOf(pd.get("city"));
				String year=pd.get("year")==null?"":String.valueOf(pd.get("year"));
				String type=pd.get("type")==null?"":String.valueOf(pd.get("type"));
				page=getMyPage(pd);
				QueryWrapper<CompanyInfoRz> qws=new QueryWrapper<CompanyInfoRz>();
				if(StringUtils.isNotBlank(keyword)){
					qws.and(i -> i
							.like("companyname", keyword)
							.or()
							.like("number", keyword)
							.or()
							.like("type", keyword)
							.or()
							.like("batch", keyword)
							);
				}
				if(StringUtils.isNotBlank(province)){
					qws.like("province", province);
				}
				if(StringUtils.isNotBlank(city)){
					qws.like("city", city);
				}
				if(StringUtils.isNotBlank(year)){
					qws.like("year", year);
				}
				if(StringUtils.isNotBlank(type)){
					qws.like("type", type);
				}else{
					qws.in("type", "认定","复审","公示");
				}
				qws.orderByDesc("year");
				companyInfoRzService.page(page, qws);
				respon.success(page);
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="高企数据详情接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping(value="/detail")
	public Respon detail(String id) {
		Respon respon=this.getRespon();
		try {
			CompanyInfoRz c=null;
			if(StringUtils.isNotBlank(id)){
				c=companyInfoRzService.getById(id);
			}
			respon.success(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}

}
