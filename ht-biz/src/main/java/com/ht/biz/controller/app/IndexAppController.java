package com.ht.biz.controller.app;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ht.biz.service.PolicyDigService;
import com.ht.biz.service.PolicyMatchService;
import com.ht.biz.service.PolicylibService;
import com.ht.biz.service.ProjectlibService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.companypolicy.CompanyInfoRz;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.policydig.PolicyDig;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.CommonsUtil;
import com.ht.utils.GaodeRegionUtil;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MatchRecordUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/app/index")
@Api(value="indexAppController",description = "手机端页面接口管理")
public class IndexAppController extends BaseController{
	
	private static final String speProvince="北京市天津市上海市重庆市深圳市";
	
	@Autowired
    private PolicyDigService policyDigService;
	
	@ApiOperation(value="跳转到高企云手机版首页页面")
	@GetMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("/app/index/"+page);
	}
	
	@ApiOperation(value="获取id定位(定位到市)")
	@GetMapping(value="/getIpAddr")
	public Respon getIpAddr(String ip,HttpServletRequest request,HttpServletResponse response){
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			policyDigService.getCookies(pd, request, "1", response);
			respon.success(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="记录用户选择省市区")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "province", value = "省份" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区", dataType = "String"),
	})
	@GetMapping("record")
	public Respon record(HttpServletResponse response){
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			Cookie province=new Cookie("province",URLEncoder.encode(StringUtils.isBlank(pd.getString("province"))?"":pd.getString("province"),"UTF-8"));
			Cookie city=new Cookie("city",URLEncoder.encode(StringUtils.isBlank(pd.getString("city"))?"":pd.getString("city"),"UTF-8"));
			Cookie area=new Cookie("area",URLEncoder.encode(StringUtils.isBlank(pd.getString("area"))?"":pd.getString("area"),"UTF-8"));
			province.setMaxAge(60*60*24*30);city.setMaxAge(60*60*24*30);area.setMaxAge(60*60*24*30);
			province.setPath("/");city.setPath("/");area.setPath("/");
			response.addCookie(province);response.addCookie(city);response.addCookie(area);
			respon.success("记忆成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
}
