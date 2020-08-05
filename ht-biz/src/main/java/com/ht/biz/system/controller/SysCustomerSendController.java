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

import com.ht.biz.service.CustomerSendService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.CustomerSend;
import com.ht.entity.policydig.PTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/syscustsend")
@Api(value="SysCustomerSendController",description = "政策推送客户访问后台管理")
public class SysCustomerSendController extends BaseController{
	@Autowired
    private CustomerSendService customerSendService;
	
	@ApiOperation(value="后台政策推送客户访问列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "String"),
	})
	@PostMapping(value="/list")
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			page.setPd(pd);
			page.setRecords(customerSendService.findlistPage(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送客户访问单条数据保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "PTemplate", value = "模板id",  dataType = "PTemplate"),
	})
	@PostMapping(value="/save")
	public Respon save(@RequestBody CustomerSend CustomerSend) throws Exception{
		Respon respon = this.getRespon();
		try {
			CustomerSend.setCreatedate(new Date());
			Map<String ,Object > map=getLoginInfo();
			if(map!=null){
				CustomerSend.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
				CustomerSend.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
			}
			customerSendService.insert(CustomerSend);
			respon.success(CustomerSend);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送客户访问单条数据删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "模板id",  dataType = "String"),
	})
	@GetMapping(value="/deleted")
	public Respon deleted(String id){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(id)){
				customerSendService.deleted(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
}
