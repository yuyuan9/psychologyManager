package com.ht.biz.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.CustomerInfoService;
import com.ht.biz.service.CustomerRecordService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.policydig.CustomerRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/syscustrec")
@Api(value="SysCustomerRecordController",description = "客户信息上传记录后台管理")
public class SysCustomerRecordController extends BaseController{
	@Autowired
    private CustomerRecordService customerRecordService;
	
	@Autowired
    private CustomerInfoService customerInfoService;
	
	@ApiOperation(value="后台客户信息上传记录查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "fileName", value = "上传文件名" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "addr", value = "上传地区" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "createdate1", value = "上传开始时间" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "createdate2", value = "上传结束时间" ,dataType = "String"),
	})
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage<CustomerRecord> page=getMyPage(pd);
			if(pd.get("createdate1")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("createdate1")))){
				String createdate1=String.valueOf(pd.get("createdate1"));
				Date date=DateUtil.StrTodate(createdate1, "yyyy-MM-dd");
				date=DateUtil.addDay(date);
				pd.add("createdate1", DateUtil.dateToStr(date, 11)+" 00:00:00");
			}
			if(pd.get("createdate2")!=null&&!StringUtils.isBlank(String.valueOf(pd.get("createdate2")))){
				String createdate1=String.valueOf(pd.get("createdate2"));
				Date date=DateUtil.StrTodate(createdate1, "yyyy-MM-dd");
				date=DateUtil.addDay(date);
				pd.add("createdate2", DateUtil.dateToStr(date, 11)+" 23:59:59");
			}
			//page.setPd(pd);
			page.setRecords(customerRecordService.findlistPage(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台单条客户信息上传记录保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "CustomerRecord", value = "CustomerRecord数据封装" ,dataType = "CustomerRecord"),
	})
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Respon save(@RequestBody CustomerRecord customerRecord){
		Respon respon = this.getRespon();
		try {
			customerRecord.setId(UuidUtil.get32UUID());
			customerRecord.setCreatedate(new Date());
			Map<String ,Object > map=getLoginInfo();
			if(map!=null){
				customerRecord.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
				customerRecord.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
			}
			customerRecordService.insert(customerRecord);
			respon.success(customerRecord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台单条客户信息上传记录删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "客户信息上传记录id" ,dataType = "String"),
	})
	@RequestMapping(value="/deleted",method=RequestMethod.GET)
	public Respon deleted(String id){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(id)){
				customerRecordService.deleted(id);
				customerInfoService.deleteByCustomerRecordId(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
			e.printStackTrace();
		}
		return respon;
	}
}
