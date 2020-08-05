package com.ht.biz.system.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.GuaranteeMoneyService;
import com.ht.biz.service.SerprociderService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.GuaranteeMoney;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/guaran")
@Api(value="SysGuaranteeMoneyController",description = "后台保障金管理")
public class SysGuaranteeMoneyController extends BaseController{
	
	@Autowired
	private GuaranteeMoneyService guaranteeMoneyService;
	
	@Autowired
    private SerprociderService serprociderService;
	
	@ApiOperation(value="保障金数据")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "banme", value = "服务商名称",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			pd.add("date", DateUtil.dateToStr(new Date(), 11));
			MyPage page=getMyPage(pd);
			List<PageData> list=guaranteeMoneyService.findlist(page);
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="保障金数据")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "ids", value = "服务商id集合",  dataType = "string"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			String ids=pd.get("ids")==null?"":String.valueOf(pd.get("ids"));
			if(StringUtils.isNotBlank(ids)){
				GuaranteeMoney gtm=null;
				for(String id:ids.split(",")){
					gtm=guaranteeMoneyService.getoneById(id);
					if(gtm==null){
						gtm=new GuaranteeMoney();
						gtm.setProject("入驻服务商保障计划");
						gtm.setStatus(1);
						gtm.setBeginDate(new Date());
						gtm.setEndDate(DateUtil.addYear(new Date(), 1));
						gtm.setCreatedate(new Date());
						gtm.setCreateid(id);
						gtm.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
						guaranteeMoneyService.save(gtm);
						pd.add("id", id);pd.add("isbond", 1);
						serprociderService.updateIsblond(pd);
					}
				}
			}
			respon.success(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="撤销保障")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
		@ApiImplicitParam(paramType="query",name = "status", value = "状态(1保障中，2已过期)",  dataType = "string"),
		@ApiImplicitParam(paramType="query",name = "createid", value = "关联服务商id",  dataType = "string"),
	})
	@PostMapping("disguaran")
	public Respon disguaran(@RequestBody GuaranteeMoney gtm){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			guaranteeMoneyService.updateById(gtm);
			pd.add("id", gtm.getCreateid());
			pd.add("isbond", gtm.getStatus()==1?1:0);
			serprociderService.updateIsblond(pd);
			respon.success(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
