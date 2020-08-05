package com.ht.biz.controller.app;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.biz.service.PolicyDigService;
import com.ht.biz.service.PolicyMatchService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.policymatch.PolicyMatch;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MatchRecordUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/app/policymatch")
@Api(value="policyMatchAppController",description = "政策匹配前端管理")
public class PolicyMatchAppController extends BaseController{
	
	private static final String speProvince="北京市天津市上海市重庆市深圳市";
	
	@Autowired
	private PolicyMatchService policyMatchService;
	
	@ApiOperation(value="跳转到政策匹配页面")
	@GetMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("/app/policymatch/"+page);
	}
	
	@ApiOperation(value="政策匹配查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "companyName", value = "公司名称(必填)",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "matchType", value = "匹配分类(1初步匹配2精细匹配)",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省(必填)",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "scaleCode", value = "申报企业规模",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "applyField", value = "申报领域",  dataType = "String"),
	})
	//初步匹配
	@PostMapping(value="/list")
	public Respon list(MyPage page) throws Exception{
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			String provinces=pd.get("province")==null?"":String.valueOf(pd.get("province"));
			String companyName=pd.get("companyName")==null?"":String.valueOf(pd.get("companyName"));
			int flag=0;//页数等于1
			if(page.getCurrent()>1){
				SysUser user=LoginUserUtils.getLoginUser();
				if(user==null){
					flag=1;//页数大于1未登录
				}else{
					flag=2;//页数大于1已登录
				}
			}
			if(flag==1){
				respon.loginerror("未登录");
			}else if(StringUtils.equals("", companyName)||StringUtils.equals("", provinces)){
				respon.error("非法访问");
			}else{
				if(StringUtils.contains(speProvince, pd.getString("province"))){
					pd.add("province", pd.getString("province"));
					pd.add("city", pd.getString("area"));
					pd.add("area", null);
				}
				pd.add("sql", policyMatchService.getSql(pd.getString("province"), pd.getString("city"), pd.getString("area")));
				pd.add("matchType", 1);
				page=getMyPage(pd);
				page.setRecords(policyMatchService.findlist(page));
				pd.remove("sql");
				MatchRecordUtil.save(pd.getString("companyName"), pd.getString("province"), pd.getString("city"), pd.getString("area"), null, pd.getString("applyField"), pd.getString("applyScale1"), null, null);
				respon.success(page);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	//精细匹配
	@PostMapping(value="/amatchlist")
	public Respon matchlist(MyPage page) throws Exception{
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			SysUser user=LoginUserUtils.getLoginUser();;
			if(user!=null){
				if(StringUtils.contains(speProvince, pd.getString("province"))){
					pd.add("province", pd.getString("province"));
					pd.add("city", pd.getString("area"));
					pd.add("area", null);
				}
				pd.add("sql", policyMatchService.getSql(pd.getString("province"), pd.getString("city"), pd.getString("area")));
				pd.add("matchType", 2);
				page=getMyPage(pd);
				page.setRecords(policyMatchService.findlist(page));
				pd.remove("sql");
				MatchRecordUtil.save(pd.getString("companyName"), pd.getString("province"), pd.getString("city"), pd.getString("area"), user==null?null:user.getPhone(), pd.getString("applyField"), pd.getString("scaleCode"), user==null?null:user.getUserId(), user==null?null:user.getCompanyid());
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
	@ApiOperation(value="政策匹配详情接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping(value="/detail")
	public Respon detail(String id){
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			PolicyMatch p=new PolicyMatch();
			if(!StringUtils.isBlank(id)){
				p=policyMatchService.findById(pd);
			}
			respon.success(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error(e);
		}
		return respon;
	}
}
