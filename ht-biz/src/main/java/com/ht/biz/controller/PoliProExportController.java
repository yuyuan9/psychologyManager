package com.ht.biz.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.PoliProExportService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.policylib.PoliProExport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/poliproexport")
@Api(value="PoliProExportController",description = "政策库、立项库导出限制")
public class PoliProExportController extends BaseController{
	
	@Autowired
	private PoliProExportService poliProExportService;
	
	@ApiOperation(value="用户已导出条数接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "local", value = "政策库、立项库",  dataType = "String"),
	})
	@GetMapping("getcounts")
	public Respon getcounts() throws Exception{
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		Integer counts=0;
		try {
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				String local=pd.get("local")!=null?String.valueOf(pd.get("local")):"";
				pd.add("createid", map.get("userId"));
				pd.add("local", local);
				pd.add("createdate", DateUtil.dateToStr(new Date(), 11));
				counts=poliProExportService.selectCounts(pd);
				counts=counts==null?0:counts;
				respon.success(counts);
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
	
}
