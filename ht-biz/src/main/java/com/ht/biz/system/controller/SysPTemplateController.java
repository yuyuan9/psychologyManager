package com.ht.biz.system.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.PTemplateService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.CustomerRecord;
import com.ht.entity.policydig.PTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/sysptem")
@Api(value="SysPTemplateController",description = "政策推送模板后台管理")
public class SysPTemplateController extends BaseController{
	@Autowired
    private PTemplateService pTemplateService;
	@ApiOperation(value="后台政策推送模板列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "String"),
	})
	@PostMapping(value="/list")
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			page.setPd(pd);
			page.setRecords(pTemplateService.findlistPage(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送模板单条数据查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "模板id",  dataType = "String"),
	})
	@GetMapping(value="/edit")
	public Respon edit(String id){
		Respon respon = this.getRespon();
		PTemplate pTemplate=new PTemplate();
		try {
			if(!StringUtils.isBlank(id)){
				pTemplate=pTemplateService.findById(id);
			}
			respon.success(pTemplate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送模板单条数据保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "PTemplate", value = "模板",  dataType = "PTemplate"),
	})
	@PostMapping(value="/save")
	public Respon save(@RequestBody PTemplate pTemplate) throws Exception{
		Respon respon = this.getRespon();
		try {
			if(StringUtils.isBlank(pTemplate.getId())){
				pTemplate.setCreatedate(new Date());
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					pTemplate.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					pTemplate.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				pTemplateService.save(pTemplate);
			}else{
				pTemplate.setLastmodified(new Date());
				pTemplateService.edit(pTemplate);
			}
			respon.success(pTemplate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送模板单条数据删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "模板id",  dataType = "String"),
	})
	@GetMapping(value="/deleted")
	public Respon deleted(String id){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(id)){
				pTemplateService.deleted(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
}
