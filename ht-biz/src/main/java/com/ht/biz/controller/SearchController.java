package com.ht.biz.controller;

import java.util.List;

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
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.entity.policydig.PolicyDig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/ht-biz/search")
@Api(value = "SearchController", description = "全站搜索管理")
public class SearchController extends BaseController{
	
	@Autowired
    private PolicyDigService policyDigService;
	
	@ApiOperation(value="全站搜索页面跳转")
	@GetMapping(value="/index/{page}")
	public ModelAndView page(@PathVariable String page) {
		return this.setViewName("search/"+page);
	}
	@ApiOperation(value="全站搜索数据列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
		@ApiImplicitParam(paramType="query",name = "sign", value = "版块标识（product：产品||policydig：政策速递||library：文库）",  dataType = "String"),
	})
	@PostMapping("list")
	public Respon list(MyPage<PolicyDig> page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			List<PolicyDig> list=null;
			page=getMyPage(pd);
//			String keyword=pd.get("keyword")==null?"":String.valueOf(pd.get("keyword"));
//			if(StringUtils.isNotBlank(keyword)){
//				pd.add("keyword", RequestUtils.getParticiple(keyword, "%"));
//			}
			list=policyDigService.searchlist(page);	
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
