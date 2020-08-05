package com.ht.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.PolicyDigService;
import com.ht.biz.service.SubscribeService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyDig;
import com.ht.entity.policydig.Subscribe;
import com.ht.utils.CommonsUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/ht-biz/subscribe")
@Api(value="SubscribeController",description = "政策速递订阅")
public class SubscribeController extends BaseController{
	
	@Autowired
	private SubscribeService subscribeService;
	
	@Autowired
    private PolicyDigService policyDigService;
	
	@ApiOperation(value="政策速递查询用户是否订阅")
	@GetMapping(value="/getSubscribe")
	public Respon getSubscribe() throws Exception{
		Respon respon = this.getRespon();
		Map<String ,Object > map=getLoginInfo();
		Subscribe subscribe=null;
		if(map!=null){
			QueryWrapper<Subscribe> qw=new QueryWrapper<Subscribe>();
			qw.eq("createid", map.get("userId"));
			qw.orderByDesc("createdate");
			subscribe=subscribeService.getOne(qw,false);
			respon.success(subscribe);
		}else{
			respon.loginerror(null);
		}
		return respon;
	}
	
	@ApiOperation(value="政策速递添加或者修改用户订阅")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "region", value = "地区集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "productType", value = "政策分类集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "tecnology", value = "技术领域集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "vip", value = "是否是vip（0不是，1是）",  dataType = "object"),
	})
	@GetMapping(value="/addSubscribe")
	public Respon addSubscribe(Subscribe subscribe) throws Exception{
		Respon respon = this.getRespon();
		try {
			Map<String ,Object > map=getLoginInfo();
			if(map!=null){
//				QueryWrapper<Subscribe> qw=new QueryWrapper<Subscribe>();
//				qw.eq("createid", map.get("userId"));
//				Subscribe s=subscribeService.getOne(qw);
				if(!StringUtils.isBlank(subscribe.getId())){
					subscribe.setLastmodified(new Date());
					subscribeService.updateById(subscribe);
				}else{
					subscribe.setCreatedate(new Date());
					subscribe.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					subscribe.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
					subscribeService.save(subscribe);
				}
				respon.success(subscribe);
			}else{
				respon.loginerror(null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="政策速订阅数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "region", value = "地区集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "productType", value = "政策分类集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "tecnology", value = "技术领域集合",  dataType = "object"),
        @ApiImplicitParam(paramType="query",name = "vip", value = "是否是vip（0不是，1是）",  dataType = "object"),
	})
	@GetMapping(value="/getlist")
	public Respon getlist(MyPage<PolicyDig> page){
		Respon respon = this.getRespon();
		PageData pd = this.getPageData();
		try {
			QueryWrapper<PolicyDig> qw=new QueryWrapper<PolicyDig>();
			qw.select("id","seoword","status","title","datetime","province","city","area",
					"source","url","productType","issueCompany","nature","onShow",
					"top","beginDate","endDate","browsecount","field","originalDate",
					"forward","collection","industry","tecnology","tecnologyFirst");
			if(pd.get("region")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("region")))){
				String region=String.valueOf(pd.get("region"));
				String[] regions=region.split(",");
				StringBuffer sub=new StringBuffer();
				sub.append("(");
				for(int k=0;k<regions.length;k++){
					String s=regions[k];
					if(k>0){
						sub.append(" or ");
					}
					sub.append("province LIKE CONCAT(CONCAT('%', '"+s+"'),'%')");
					sub.append(" or city LIKE CONCAT(CONCAT('%', '"+s+"'),'%')");
					sub.append(" or area LIKE CONCAT(CONCAT('%', '"+s+"'),'%')");
				}
				sub.append(")");
				pd.add("region", sub.toString());
			}
			if(pd.get("nature")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("nature")))){
				String nature=String.valueOf(pd.get("nature"));
				nature="'"+nature.replace(",", "','")+"'";
				pd.add("nature", nature);
			}
			if(pd.get("productType")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("productType")))){
				String productType=String.valueOf(pd.get("productType"));
				productType="'"+productType.replace(",", "','")+"'";
				pd.add("productType", productType);
			}
			if(pd.get("tecnology")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("tecnology")))){
				String tecnology=String.valueOf(pd.get("tecnology"));
				tecnology="'"+tecnology.replace(",", "','")+"'";
				pd.add("tecnology", tecnology);
			}
			page=getMyPage(pd);
			page.setRecords(policyDigService.subscribeList(page));
			CommonsUtil.filterDate(page.getRecords());
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	
	
	
}
