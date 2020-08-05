package com.ht.biz.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.PoliProExportService;
import com.ht.biz.service.PoliProRecordService;
import com.ht.biz.service.ProjectlibService;
import com.ht.biz.service.RewardRuleService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.excel.Excel;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.entity.biz.solr.policylib.PoliProExport;
import com.ht.entity.biz.solr.policylib.PoliProRecord;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;
import com.ht.utils.RewardUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
@RestController
@RequestMapping(value="/ht-biz/projectlib")
@Api(value="ProjectlibController",description = "立项库前端管理")
public class ProjectlibController extends BaseController{
	
	@Autowired
	private ProjectlibService projectlibService;
	
	@Autowired
	private PoliProRecordService poliProRecordService;
	
	@Autowired
	private PoliProExportService poliProExportService;
	
	@Autowired
    private PointRedemptionService pointRedemptionService;
	
	@Autowired
	private RewardRuleService rewardRuleService;
	
	@ApiOperation(value="前端立项库数据接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
		@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year1", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year2", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "name", value = "项目名称",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "special", value = "专题",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "directorUnit", value = "主管单位",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "regions", value = "0表示点击框地区，1表示正常地区选择",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "countrys", value = "点击框国家",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "provinces", value = "点击框省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "citys", value = "点击框市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "areas", value = "点击框区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "currcord", value = "点击翻页记录",  dataType = "String"),
	})
	@PostMapping("list")
	public Respon list(MyPage<Projectlib> page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			Map<String,Object > map2=getLoginInfo();
			if(map2!=null){
				if(!StringUtils.isBlank(pd.getString("keyword"))){
					Double honeytotal = pointRedemptionService.getWaterTotal(String.valueOf(map2.get("userId")));
					honeytotal=honeytotal==null?0:honeytotal;
					page=getMyPage(pd);
					int flag=0;
					QueryWrapper<RewardRule> qws=new QueryWrapper<RewardRule>();
					qws.eq("code", Code.poli_pro_counts);
					RewardRule rr=rewardRuleService.getOne(qws, false);
					if(pd.get("currcord")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("currcord")))){
						flag=1;
						if(honeytotal>=Math.abs(Double.valueOf(rr.getReturnValue()))){
							flag=2;
							RewardUtil.disHoney(rr, String.valueOf(map2.get("userId")), String.valueOf(map2.get("userId")), String.valueOf(map2.get("regionId")));
						}
					}
					if(flag==1){
						respon.error("honey值不足");
					}else{
						String regions=StringUtils.isBlank(pd.getString("regions"))?"1":String.valueOf(pd.get("regions"));
						String fq=projectlibService.getPcQueryStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("year1"), pd.getString("year2"),regions,pd.getString("countrys"),pd.getString("provinces"),pd.getString("citys"),pd.getString("areas"));
						//System.out.println("fq="+fq);
						page=SolrUtils.getList(page, new Projectlib(), true,Const.solrCore_projectlib,"*:*",fq,"yearProject desc",pd.getString("keyword"),true);
						//-----------------记录用户足迹----------------//
						PoliProRecord p=new PoliProRecord();
						p.setCounts(Double.valueOf(page.getTotal()));
						p.setIp(RequestUtils.getIp());
						p.setInterfaceName("/ht-biz/projectlib/list");
						p.setLocal("立项库");
						p.setCreatedate(new Date());
						p.setCreateid(map2.get("userId")==null?"":String.valueOf(map2.get("userId")));
						p.setRegionid(map2.get("companyid")==null?"":String.valueOf(map2.get("companyid")));
						poliProRecordService.save(p);
						//-----------------记录用户足迹----------------//
						respon.success(page);
					}
				}
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respon;
	}
	
	@ApiOperation(value="前端立项库详情数据接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(id)){
				Projectlib p=(Projectlib) SolrUtils.getById(Const.solrUrl, Const.solrCore_projectlib, id,new Projectlib());
				respon.success(p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value="前端立项库导出接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "正速递id",  dataType = "String"),
	})
	@GetMapping("export")
	public void export(HttpServletResponse response,String suffix,String fileName,String workName,MyPage<Projectlib> page) throws Throwable{
		PageData pd=this.getPageData();
		try {
			Map<String,Object > map2=getLoginInfo();
			if(map2!=null){
				pd.add("createid", map2.get("userId"));
				pd.add("local", "立项库");
				pd.add("createdate", DateUtil.dateToStr(new Date(), 11));
				Integer counts=poliProExportService.selectCounts(pd);
				counts=counts==null?0:counts;
				Double honeytotal = pointRedemptionService.getWaterTotal(String.valueOf(map2.get("userId")));
				honeytotal=honeytotal==null?0:honeytotal;
				counts=100-counts;
				if(counts>0){
					String regions=StringUtils.isBlank(pd.getString("regions"))?"1":String.valueOf(pd.get("regions"));
					String fq=projectlibService.getPcQueryStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("year1"), pd.getString("year2"),regions,pd.getString("countrys"),pd.getString("provinces"),pd.getString("citys"),pd.getString("areas"));
					page.setSize(counts);
					page=SolrUtils.getList(page, new Projectlib(), false,Const.solrCore_projectlib,"*:*",fq,"yearProject desc",pd.getString("keyword"),false);
					List<Projectlib> list=page.getRecords();
					counts=counts>list.size()?list.size():counts;
					double d=(counts/2)*1.0;
					if(honeytotal>=d){
						String[] Columns=new String[]{"companyName","name","region","yearProject","type","special","quota"};
						String[] titles=new String[]{"企业名称","项目名称","地区","年度","项目类别","专题","立项额度"};
						//-----------------记录用户导出----------------//
						PoliProExport p=new PoliProExport();
						p.setCounts(counts);
						p.setLocal("立项库");
						p.setCreatedate(new Date());
						p.setCreateid(map2.get("userId")==null?"":String.valueOf(map2.get("userId")));
						p.setRegionid(map2.get("companyid")==null?"":String.valueOf(map2.get("companyid")));
						poliProExportService.save(p);
						//-----------------记录用户导出----------------//
						RewardUtil.disHoney(String.valueOf(Code.poli_pro_export), Double.valueOf(-d), String.valueOf(map2.get("userId")), String.valueOf(map2.get("userId")), String.valueOf(map2.get("regionId")));
						Excel.excelAppoint(response, list, new Projectlib(), suffix, fileName, "立项查询结果导出表", Columns, titles);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 微信小程序接口
	 */
	@PostMapping("winxinlist")
	public Respon winxinlist(String userId,MyPage page,String companyid){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			if(!StringUtils.isBlank(userId)){
				pd.add("userId", "admin");
				page=getMyPage(pd);
				String fq=null;
				if(StringUtils.isNotBlank(pd.getString("id"))){
					fq=projectlibService.getQueryById(pd.getString("id"));
				}else{
					fq=projectlibService.getSpQueryStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("year"));
				}
				//System.out.println("fq="+fq);
				page=SolrUtils.getList(page, new Projectlib(), false,Const.solrCore_projectlib,"*:*",fq,"yearProject desc",pd.getString("keyword"),false);
				//-----------------记录用户足迹----------------//
				PoliProRecord p=new PoliProRecord();
				p.setCounts(Double.valueOf(page.getTotal()));
				p.setIp(RequestUtils.getIp());
				p.setInterfaceName("/ht-biz/policylib/projectlib");
				p.setLocal("立项库");
				p.setCreatedate(new Date());
				p.setCreateid(userId);
				p.setRegionid(companyid);
				poliProRecordService.save(p);
				//-----------------记录用户足迹----------------//
				respon.success(page);
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
