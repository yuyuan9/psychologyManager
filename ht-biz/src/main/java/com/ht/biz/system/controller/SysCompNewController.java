package com.ht.biz.system.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.ht.biz.service.CompNewService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.companypolicy.CompNew;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/highcom")
@Api(value="SysCompNewController",description = "企业数据管理")
public class SysCompNewController extends BaseController{
	
	@Autowired
	private CompNewService compNewService;
	
	@ApiOperation(value="高企查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			List<PageData> list=compNewService.findlist(page);
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="高企数据修改")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "companyname", value = "公司名称",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "number", value = "编号",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "remark", value = "备注",  dataType = "String"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			compNewService.editCompany(pd);
			respon.success(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="高企数据删除")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "数据ids",  dataType = "String"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(ids)){
				for(String id:ids.split(",")){
					compNewService.deleted(id);
				}
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="企业库查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "high", value = "（高企或者重新认定）",  dataType = "String"),
	})
	@PostMapping("compNewlist")
	public Respon compNewlist(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		MyPage page;
		try {
			page = getMyPage(pd);
			StringBuffer subq=new StringBuffer();
			StringBuffer subfq=new StringBuffer();
			String keyword=pd.get("keyword")==null?"":String.valueOf(pd.get("keyword"));
			String high=pd.get("high")==null?"":String.valueOf(pd.get("high"));
			if(!StringUtils.isBlank(keyword)){
				subq.append("companyname:*"+keyword+"*");
			}else{
				subq.append("*:*");
			}
			if(!StringUtils.isBlank(high)){
				subfq.append("high:"+high);
			}
			Map<String,Object > map2=getLoginInfo();
			SolrUtils.getList(page, new CompNew(), true,Const.solrCore_compnew,subq.toString(),subfq.toString(),null,keyword,false);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
}
