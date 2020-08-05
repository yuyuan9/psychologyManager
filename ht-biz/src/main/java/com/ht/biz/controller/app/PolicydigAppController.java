package com.ht.biz.controller.app;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.biz.service.PolicyDigService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyDig;
import com.ht.utils.CommonsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/ht-biz/app/policydig")
@Api(value="policydigAppController",description = "手机端政策速递接口管理")
public class PolicydigAppController extends BaseController{
	
	private static final String speProvince="北京市天津市上海市重庆市深圳市";
	
	@Autowired
    private PolicyDigService policyDigService;
	
	@ApiOperation(value="跳转到高企云政策速递页面")
	@GetMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("/app/policydig/"+page);
	}
	
	@ApiOperation(value="政策速递列表")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "province", value = "省份" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "total", value = "查询总数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "size", value = "每页条数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "pages", value = "总页数", dataType = "int"),
	})
	@PostMapping (value="list")
	public Respon list(MyPage<PolicyDig> page,String memory,HttpServletRequest request,HttpServletResponse response){
		Respon respon = this.getRespon();
		PageData pd = this.getPageData();
		try {
			if(StringUtils.contains(speProvince, pd.getString("province"))){
				pd.add("province", pd.getString("province"));
				pd.add("city", pd.getString("area"));
				pd.add("area", null);
			}
			policyDigService.getCookies(pd, request, memory, response);
			String sql=policyDigService.getphoneStr(pd.getString("province"), pd.getString("city"), pd.getString("area"));
			pd.add("sql", sql);
			page=getMyPage(pd);
			List<PolicyDig> list=policyDigService.findV3listPage(page);
			CommonsUtil.filterDate(list);
			pd.remove("sql");
			respon.success(list,page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="政策速递详情接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "正速递id",  dataType = "String"),
	})
	@GetMapping(value="/detail")
	public Respon detail(String id){
		Respon respon = this.getRespon();
		PolicyDig policyDig=new PolicyDig();
		try {
			if(!StringUtils.isBlank(id)){
				policyDig=policyDigService.findById(id);
				policyDig.setBrowsecount(policyDig.getBrowsecount()+1);
				policyDigService.edit(policyDig);
				if(policyDig.getEndDate()!=null){
					int flag=Long.valueOf(DateUtil.getDaySub(DateUtil.dateToStr(policyDig.getEndDate(),11),DateUtil.dateToStr(new Date(),11))).intValue();
					policyDig.setStatus(flag==0?1:flag);
				}else{
					policyDig.setStatus(0);
				}
			}
			respon.success(policyDig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="政策速递最新数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "正速递id",  dataType = "String"),
	})
	@GetMapping(value="/newlist")
	public Respon newlist(MyPage<PolicyDig> page){
		Respon respon = this.getRespon();
		PageData pd = this.getPageData();
		try {
			String sql=policyDigService.getphoneStr(pd.getString("province"), pd.getString("city"), pd.getString("area"));
			pd.add("sql", sql);
			page=getMyPage(pd);
			List<PolicyDig> list=policyDigService.appnewlist(page);
			//List<PolicyDig> list=page.getRecords();
			CommonsUtil.filterDate(list);
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
