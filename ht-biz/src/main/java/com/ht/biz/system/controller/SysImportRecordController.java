package com.ht.biz.system.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.ht.biz.service.ImportRecordService;
import com.ht.biz.service.PolicylibService;
import com.ht.biz.service.ProjectlibService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.recore.ImportRecord;

import ch.qos.logback.classic.pattern.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/import")
@Api(value="SysImportRecord",description = "政策库、立项库后台导入记录")
public class SysImportRecordController extends BaseController{
	
	@Autowired
	private ImportRecordService importRecordService;
	
	@Autowired
	private PolicylibService policylibService;
	
	@Autowired
	private ProjectlibService projectlibService;
	
	@ApiOperation(value="政策库、立项库后台导入记录")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "createdate", value = "导入时间",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "importLocal", value = "导入库",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			if(pd.get("createdate")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("createdate")))){
				String createdate1=String.valueOf(pd.get("createdate"));
				Date date=DateUtil.StrTodate(createdate1, "yyyy-MM-dd");
				date=DateUtil.addDay(date);
				pd.add("createdate", DateUtil.dateToStr(date, 11));
			}
			MyPage<ImportRecord> page=getMyPage(pd);
			page.setRecords(importRecordService.findlist(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respon;
	}
	
	@ApiOperation(value="政策库、立项库后台导入记录删除")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "数据id集合（用英文版逗号隔开）",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids) throws Exception{
		Respon respon=this.getRespon();
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			if(StringUtils.isNotBlank(ids)){
				for(String id:ids.split(",")){
					map.put("hisImportId", id);
					ImportRecord im=importRecordService.getById(id);
					if(StringUtils.equals("政策库", im.getImportLocal())){
						SolrUtils.deleteByQuery(Const.solrUrl, Const.solrCore_policylib, "hisImportId", id);
						policylibService.removeByMap(map);
					}else if(StringUtils.equals("立项库", im.getImportLocal())){
						SolrUtils.deleteByQuery(Const.solrUrl, Const.solrCore_projectlib, "hisImportId", id);
						projectlibService.removeByMap(map);
					}
					importRecordService.removeById(id);
				}
				respon.success(ids);
			}else{
				respon.error("数据有误");
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
	
}
