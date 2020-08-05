package com.ht.biz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.PolicyMatchService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.excel.Excel;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.entity.biz.policymatch.PolicyMatch;
import com.ht.entity.biz.policymatch.PolicyMatch.Match;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MatchRecordUtil;
import com.ht.utils.RewardUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/policymatch")
@Api(value="PolicyMatchController",description = "政策匹配前端管理")
public class PolicyMatchController extends BaseController{

	@Autowired
	private PolicyMatchService policyMatchService;
	
	@Autowired
    private PointRedemptionService pointRedemptionService;
	
	@GetMapping("/{page}")
	public ModelAndView page(@PathVariable String page){
		return this.setViewName("match/"+page);
	}
	
	@ApiOperation(value="政策匹配申报企业规模查询接口(手机端)")
	@ApiImplicitParams({
        //@ApiImplicitParam(paramType="query",name = "id", value = "模板id",  dataType = "String"),
	})
	@GetMapping(value="/scalelist")
	public Respon scalelist() throws Exception{
		Respon respon = this.getRespon();
		List<PageData> pds=new ArrayList<PageData>();
		PageData pd=new PageData();
		pd.add("sv", "sv0");
		pd.add("value", "");
		pd.add("text", "点击选择");
		pds.add(pd);
        Match[] ms=Match.values();
		for(Match m:ms){
			pd=new PageData();
			pd.add("sv", m.name());
			pd.add("value", m.getAs2());
			pd.add("text", m.getAs3());
			pds.add(pd);
		}
		respon.setData(pds);
		return respon;
	}
	
	@ApiOperation(value="政策匹配申报企业规模查询接口(pc端)")
	@ApiImplicitParams({
        //@ApiImplicitParam(paramType="query",name = "id", value = "模板id",  dataType = "String"),
	})
	@GetMapping(value="/pclist")
	public Respon pclist() throws Exception{
		Respon respon = this.getRespon();
		List<PageData> pds=new ArrayList<PageData>();
        Match[] ms=Match.values();
        PageData pd=null;
		for(Match m:ms){
			pd=new PageData();
			pd.add("sv", m.name());
			pd.add("value", m.getAs2());
			pd.add("text", m.getAs3());
			pds.add(pd);
		}
		respon.setData(pds);
		return respon;
	}
	
	@ApiOperation(value="政策匹配查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "companyName", value = "公司名称",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "matchType", value = "匹配分类(1初步匹配2精细匹配)",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "scaleCode", value = "申报企业规模",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "applyField", value = "申报领域",  dataType = "String"),
	})
	@PostMapping(value="/list")
	public Respon list(MyPage page) throws Exception{
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			String matchType=pd.get("matchType")==null?"1":String.valueOf(pd.get("matchType"));
			String provinces=pd.get("province")==null?"":String.valueOf(pd.get("province"));
			String companyName=pd.get("companyName")==null?"":String.valueOf(pd.get("companyName"));
			int flag=1;//区域匹配
			SysUser user=null;
			if(StringUtils.equals(matchType, "2")){
				user=LoginUserUtils.getLoginUser();
				if(user==null){
					flag=0;
				}else{
					flag=2;
				}
			}
			if(flag==0){
				respon.loginerror("未登录");
			}else if(StringUtils.equals("", companyName)||StringUtils.equals("", provinces)){
				respon.error("非法访问");
			}else{
				String sql=policyMatchService.getSql(pd.getString("province"), pd.getString("city"), pd.getString("area"));
				pd.add("sql", sql);
				page=getMyPage(pd);
				page.setRecords(policyMatchService.findlist(page));
				pd.remove("sql");
				MatchRecordUtil.save(pd.getString("companyName"), pd.getString("province"), pd.getString("city"), pd.getString("area"), user==null?null:user.getPhone(), pd.getString("applyField"), pd.getString("scaleCode"), user==null?null:user.getUserId(), user==null?null:user.getCompanyid());
				respon.success(page);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="政策匹配导出接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "companyName", value = "公司名称",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "matchType", value = "匹配分类(1初步匹配2精细匹配)",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "scaleCode", value = "申报企业规模",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "applyField", value = "申报领域",  dataType = "String"),
	})
	@GetMapping(value="/export")
	public void export(MyPage page,HttpServletResponse response) throws Exception{
		PageData pd=this.getPageData();
		try {
			Map<String,Object > map2=getLoginInfo();
			if(map2!=null){
				Double honeytotal = pointRedemptionService.getWaterTotal(String.valueOf(map2.get("userId")));
				honeytotal=honeytotal==null?0:honeytotal;
				String sql=policyMatchService.getSql(pd.getString("province"), pd.getString("city"), pd.getString("area"));
				pd.add("sql", sql);
				page=getMyPage(pd);
				List<PolicyMatch> list=policyMatchService.findlist(page);
				if(honeytotal>=list.size()*10){
					String matchType=pd.get("matchType")==null?"1":String.valueOf(pd.get("matchType"));
					String[] Columns=null,titles=null;
					if(StringUtils.equals(matchType, "2")){
						Columns=new String[]{"type","projectName","applyCon","applyMean","applyTime","applyField2","applyScale2"};
						titles=new String[]{"项目分类","项目名称","申报条件","申报意义","申报时间","申报领域","申报企业规模"};
					}else{
						Columns=new String[]{"type","projectName","applyCon","applyMean","applyTime"};
						titles=new String[]{"项目分类","项目名称","申报条件","申报意义","申报时间"};
					}
					RewardUtil.disHoney(String.valueOf(Code.policy_match_export), Double.valueOf(-list.size()*10), String.valueOf(map2.get("userId")), String.valueOf(map2.get("userId")), String.valueOf(map2.get("regionId")));
					Excel.excelAppoint(response, list, new PolicyMatch(), ".xlsx", null, "政策匹配结果导出表", Columns, titles);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
