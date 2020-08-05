package com.ht.biz.controller.app;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.biz.service.ProjectlibService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/app/projectlib")
@Api(value="projectlibAppController",description = "手机端立项库接口管理")
public class ProjectlibAppController extends BaseController{
	
	private static final String speProvince="北京市天津市上海市重庆市";
	
	@Autowired
	private ProjectlibService projectlibService;
	
	@ApiOperation(value="跳转到高企云立项库页面")
	@GetMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("/app/projectlib/"+page);
	}
	
	@ApiOperation(value="立项库数据接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        //@ApiImplicitParam(paramType="query",name = "year1", value = "年份",  dataType = "String"),
        //@ApiImplicitParam(paramType="query",name = "year2", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year", value = "单年份选择",  dataType = "String"),
	})
	@PostMapping (value="list")
	public Respon list(MyPage<Projectlib> page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			//Map<String,String> map=null;
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
				String fq=projectlibService.getAppQueryStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("year"));
				//System.out.println("fq="+fq);
				page=getMyPage(pd);
				page=SolrUtils.getList(page, new Projectlib(), true,Const.solrCore_projectlib,"*:*",fq,"yearProject desc",pd.getString("keyword"),false);
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
	@ApiOperation(value="立项库详情接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping(value="/detail")
	public Respon detail(String id) {
		Respon respon=this.getRespon();
		try {
			Projectlib p=new Projectlib();
			if(StringUtils.isNotBlank(id)){
				p=(Projectlib) SolrUtils.getById(Const.solrUrl, Const.solrCore_projectlib, id, p);
			}
			respon.success(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
