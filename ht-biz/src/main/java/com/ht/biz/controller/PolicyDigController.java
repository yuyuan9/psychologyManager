package com.ht.biz.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ht.commons.support.geelink.entity.GeelinkPolicyDig;
import com.ht.commons.support.geelink.entity.GeelinkResoupage;
import com.ht.commons.support.geelink.jsonquery.Geelinkdatacreate;
import com.ht.commons.support.sms.sdk.utils.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.biz.service.CustomerSendService;
import com.ht.biz.service.Package2PolicyService;
import com.ht.biz.service.PolicyDigService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.commons.utils.Tools;
import com.ht.entity.policydig.CustomerSend;
import com.ht.entity.policydig.PolicyDig;
import com.ht.utils.CommonsUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/ht-biz/policydig")
@Api(value="PolicyDigController",description = "政策速递前端管理")
public class PolicyDigController extends BaseController{
	
	@Autowired
    private PolicyDigService policyDigService;
	
	@Autowired
	private Package2PolicyService package2PolicyService;
	
	@Autowired
	private CustomerSendService customerSendService;


	@Value("${api.policyDig}")
	private boolean policyDig;
	//推送政策速递数据
	@RequestMapping(value={"addgeelinkPolicyDig"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	public String addgeelinkPolicyDig() throws IOException {
		List<GeelinkPolicyDig> list = policyDigService.geelinkfindall();
		for (GeelinkPolicyDig info : list) {
			List<Object> listk = new ArrayList<>(  );
			listk.add(info);
			Geelinkdatacreate cd = new Geelinkdatacreate();
			String str = cd.jsondataPolicyDigtow(listk);
			System.out.println(str  );
		}

		return null;
	}


	/*
	 * 跳转至政策速递页面
	 */
	@ApiOperation(value="跳转到政策速递页面",notes="")
    @GetMapping(value="/{page}")
    public ModelAndView page(@PathVariable String page,HttpServletRequest request) {
		if(Tools.pcphone(request)){
            return this.setViewName("policydig/"+page);
        }else{
            return this.setViewName("app/policydig/"+page);
        }
    }
	/*
	 * 跳转至政策速递手机端页面
	 */
	@ApiOperation(value="跳转到政策速递手机版页面",notes="")
	@GetMapping(value="/app/{page}")
    public ModelAndView apppage(@PathVariable String page) {
        return this.setViewName("policydig/app/"+page);
    }
	
	@ApiOperation(value="前端政策速递列表")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省份" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "regions", value = "（0表示用户点击页面省市区复选框，1表示没有点击）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "countrys", value = "前端国家复选框（值为国家）" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "provinces", value = "前端省份复选框（值为省）" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "citys", value = "前端城市复选框（值为市）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "areas", value = "前端区级复选框（值为区）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "datetime1", value = "开始发布时间", dataType = "date"),
        @ApiImplicitParam(paramType="query",name = "datetime2", value = "结束发布时间", dataType = "date"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "productType", value = "政策类型", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "issueCompany", value = "发布单位", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "industry", value = "行业领域", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "apply", value = "申报通知中状态查询（apply==1申报中apply==0申报终止）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "browsecount", value = "决定数据排序（随意传递一个值）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "timetype", value = "小程序范围（0全部，1近一天，2近一周，3近十天，4近一月）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "total", value = "查询总数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "memory", value = "开启记忆搜索（值为1时寻找cookies,值为2时保存记忆,空值或者其他值时其他接口调用）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "size", value = "每页条数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "pages", value = "总页数", dataType = "int"),
	})
	@PostMapping(value="/list")
	public Respon list(MyPage<PolicyDig> page,String memory,HttpServletRequest request,HttpServletResponse response){
		Respon respon = this.getRespon();
		PageData pd = this.getPageData();
		try {
			pd.add("onShow", "1");
			pd.add("totalday", DateUtil.dateToStr(new Date(),11));
			policyDigService.getCookies(pd,request,memory,response);
			String sql=policyDigService.getPcStr(pd.getString("regions"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("countrys"), pd.getString("provinces"), pd.getString("citys"), pd.getString("areas"));
			pd.add("sql", sql);
			page=getMyPage(pd);
			List<PolicyDig> list=policyDigService.findV3listPage(page);
			CommonsUtil.filterDate(list);
		/*	page.setRecords(list);*/
			respon.success(list,page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}


//	private Integer nature=0;//性质分类1:申报通知（指南）|2:政府文件（管理办法）|3:公示名录|4:政策解读|5:新闻资讯|6:其他
	public String getltyp(int type)
	{
		String ltype = "";
		switch (type)
		{
			case 1:
				ltype = "分类/申报通知";
				break;
			case 2:
				ltype = "分类/政府文件";
				break;
			case 3:
				ltype = "分类/公示名录";
				break;
			case 4:
				ltype = "分类/政策解读";
				break;
			case 5:
				ltype = "分类/新闻资讯";
				break;
			case 6:
				ltype = "分类/其他";
				break;
			default:
				ltype = "";
		}

		return ltype;
	}
	//政策类型 1:高新技术企业|2:研发机构|3:科技立项|4:科技成果|5:知识产权|6:科技财税|7:人才团队|8:其他

	public String getductype(int type)
	{
		String ltype = "";
		switch (type)
		{
			case 1:
				ltype = "分类/高新技术企业";
				break;
			case 2:
				ltype = "分类/研发机构";
				break;
			case 3:
				ltype = "分类/科技立项";
				break;
			case 4:
				ltype = "分类/科技成果";
				break;
			case 5:
				ltype = "分类/知识产权";
				break;
			case 6:
			ltype = "分类/科技财税";
			break;
			case 7:
				ltype = "分类/人才团队";
				break;
			case 8:
				ltype = "分类/其他";
				break;
			default:
				ltype = "";
		}

		return ltype;
	}
	@ApiOperation(value="前端政策速递推荐阅读")
	@GetMapping(value="/findlist")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "province", value = "省份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类",  dataType = "String"),
	})
	public Respon findlist(MyPage<PolicyDig> page){
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			page=getMyPage(pd);
			page.setRecords(policyDigService.findlist(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="前端政策单条速递查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "正速递id",  dataType = "String"),
	})
	@GetMapping(value="/findById")
	public Respon findById(String id){
		Respon respon = this.getRespon();
		PolicyDig policyDig=new PolicyDig();
		try {
			if(!StringUtils.isBlank(id)){
				policyDig=policyDigService.findById(id);
				policyDig.setBrowsecount(policyDig.getBrowsecount()+1);
				policyDigService.edit(policyDig);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		respon.success(policyDig);
		return respon;
	}
	
	@ApiOperation(value="政策包访记录")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "packageId", value = "政策包id",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "customerId", value = "客户id",  dataType = "String"),
	})
	@GetMapping(value="/interval")
	public Respon interval(){
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		try {
			List<PageData> list=package2PolicyService.findByPackageId(pd.getString("packageId"));
			CustomerSend customerSend=new CustomerSend();
			customerSend.setIp(RequestUtils.getIp());
			customerSend.setInterfaceName("/ht-biz/policydig/interval");
			customerSend.setPackageId(pd.getString("packageId"));
			customerSend.setCustomerId(pd.getString("customerId"));
			customerSend.setCreatedate(new Date());
			customerSendService.insert(customerSend);
			respon.success(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		
		return respon;
	}
	@ApiOperation(value="个人中心最新政策")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "apply", value = "申报通知状态 apply==1申报中apply==0申报终止",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "size", value = "每页个数",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类",  dataType = "String"),
	})
	@PostMapping(value="/centerlist")
	public Respon centerlist(MyPage<PolicyDig> page){
		Respon respon = this.getRespon();
		PageData pd = this.getPageData();
		try {
			pd.add("onShow", "1");
			pd.add("totalday", DateUtil.dateToStr(new Date(),11));
			page=getMyPage(pd);
			List<PolicyDig> list=policyDigService.centerlist(page);
			CommonsUtil.filterDate(list);
		/*	page.setRecords(list);*/
			respon.success(list,page);
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
