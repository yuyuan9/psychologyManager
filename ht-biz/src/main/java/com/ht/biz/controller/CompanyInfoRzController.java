package com.ht.biz.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.CompanyInfoRzService;
import com.ht.biz.service.PoliProRecordService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.entity.biz.solr.companypolicy.CompNew;
import com.ht.entity.biz.solr.companypolicy.CompanyInfoRz;
import com.ht.entity.biz.solr.policylib.PoliProRecord;
import com.ht.entity.biz.solr.projectlib.Projectlib;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/compinfo")
@Api(value="HisSelfTestingController",description = "高企数据管理")
public class CompanyInfoRzController extends BaseController{
	
	@Autowired
	private CompanyInfoRzService companyInfoRzService;
	
	@Autowired
	private PoliProRecordService poliProRecordService;
	
	@ApiOperation(value="高企数据列表")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "type", value = "认定类型",  dataType = "String"),
	})
	@PostMapping("list")
	public Respon list(MyPage<CompanyInfoRz> page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			Map<String,Object > map2=getLoginInfo();
			if(map2!=null){
				QueryWrapper<PoliProRecord> qw=new QueryWrapper<PoliProRecord>();
				qw.eq("createid", map2.get("userId"));
				qw.eq("local", "高企");
				qw.like("createdate", DateUtil.dateToStr(new Date(), 11));
				Integer counts=poliProRecordService.count(qw);
				counts=counts==null?0:counts;
				page=getMyPage(pd);
				if(counts<Math.abs(Const.company_info_rz)){
					String keyword=pd.get("keyword")==null?"":String.valueOf(pd.get("keyword"));
					QueryWrapper<CompanyInfoRz> qws=new QueryWrapper<CompanyInfoRz>();
					if(StringUtils.isNotBlank(keyword)){
						String province=pd.get("province")==null?"":String.valueOf(pd.get("province"));
						String city=pd.get("city")==null?"":String.valueOf(pd.get("city"));
						String year=pd.get("year")==null?"":String.valueOf(pd.get("year"));
						String type=pd.get("type")==null?"":String.valueOf(pd.get("type"));
						qws.and(i -> i
							.like("companyname", keyword)
							.or()
							.like("number", keyword)
							.or()
							.like("type", keyword)
							.or()
							.like("batch", keyword)
							);
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
							qws.in("type", type);
						}else{
							qws.in("type", "认定","复审","公示");
						}
						qws.orderByDesc("year");
						respon.success(companyInfoRzService.page(page, qws));
						//-----------------记录用户足迹----------------//
							PoliProRecord p=new PoliProRecord();
							p.setCounts(Double.valueOf(page.getTotal()));
							p.setIp(RequestUtils.getIp());
							p.setInterfaceName("/ht-biz/compinfo/list");
							p.setLocal("高企");
							p.setCreatedate(new Date());
							p.setCreateid(map2.get("userId")==null?"":String.valueOf(map2.get("userId")));
							p.setRegionid(map2.get("companyid")==null?"":String.valueOf(map2.get("companyid")));
							poliProRecordService.save(p);
						//-----------------记录用户足迹----------------//
					}else{
						respon.success("无结果");
					}
				}else{
					respon.error("今日查询次数已用完");
				}
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
	//高企详情查立项
	@ApiOperation(value="高企详情查立项")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "companyname", value = "公司名称",  dataType = "String"),
	})
	@GetMapping("projectlib")
	public Respon projectlib(String companyname){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(companyname)){
				List list=SolrUtils.getQuery(Const.solrUrl, Const.solrCore_projectlib, "companyName", companyname, new Projectlib());
				respon.success(list);
				//-----------------记录用户足迹----------------//
				PoliProRecord p=new PoliProRecord();
				p.setCounts(Double.valueOf(list.size()));
				p.setIp(RequestUtils.getIp());
				p.setInterfaceName("/ht-biz/compinfo/projectlib");
				p.setLocal(companyname);
				p.setCreatedate(new Date());
				Map<String,Object > map2=getLoginInfo();
				p.setCreateid(map2.get("userId")==null?"":String.valueOf(map2.get("userId")));
				p.setRegionid(map2.get("companyid")==null?"":String.valueOf(map2.get("companyid")));
				poliProRecordService.save(p);
			//-----------------记录用户足迹----------------//
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	//高企详情查企业库
	@ApiOperation(value="高企详情查企业库")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "companyname", value = "公司名称",  dataType = "String"),
	})
	@GetMapping("compnew")
	public Respon compnew(String companyname){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(companyname)){
				List list=SolrUtils.getQuery(Const.solrUrl, Const.solrCore_compnew, "companyname", companyname, new CompNew());
				respon.success(list);
				//-----------------记录用户足迹----------------//
				PoliProRecord p=new PoliProRecord();
				p.setCounts(Double.valueOf(list.size()));
				p.setIp(RequestUtils.getIp());
				p.setInterfaceName("/ht-biz/compinfo/compnew");
				p.setLocal(companyname);
				p.setCreatedate(new Date());
				Map<String,Object > map2=getLoginInfo();
				p.setCreateid(map2.get("userId")==null?"":String.valueOf(map2.get("userId")));
				p.setRegionid(map2.get("companyid")==null?"":String.valueOf(map2.get("companyid")));
				poliProRecordService.save(p);
			//-----------------记录用户足迹----------------//
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	//获取用户
	//高企详情查企业库当天查询次数
	@ApiOperation(value="获取用户高企详情查询次数(20后禁止查看详情)")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "companyname", value = "公司名称",  dataType = "String"),
	})
	@GetMapping("counts")
	public Respon counts(String companyname){
		Respon respon=this.getRespon();
		QueryWrapper<PoliProRecord> qw;
		try {
			Map<String,Object > map2=getLoginInfo();
			qw = new QueryWrapper<PoliProRecord>();
			qw.eq("createid", map2.get("userId"));
			qw.eq("local", companyname);
			qw.like("createdate", DateUtil.dateToStr(new Date(), 11));
			Integer counts=poliProRecordService.count(qw);
			respon.success(counts);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
