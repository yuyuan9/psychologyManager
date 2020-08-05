package com.ht.biz.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.PoliProRecordService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.policylib.PoliProRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/poliprorecord")
@Api(value="PoliProRecordController",description = "政策库、立项库访问记录")
public class PoliProRecordController extends BaseController{
	@Autowired
	private PoliProRecordService poliProRecordService;
	
	@ApiOperation(value="用户访问记录接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "local", value = "政策库、立项库",  dataType = "String"),
	})
	@GetMapping("list")
	public Respon list() throws Exception{
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			String local=pd.get("local")!=null?String.valueOf(pd.get("local")):"";
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				QueryWrapper<PoliProRecord> qw=new QueryWrapper<PoliProRecord>();
				qw.eq("createid", map.get("userId"));
				qw.eq("local", local);
				qw.like("createdate", DateUtil.dateToStr(new Date(), 11));
				Integer counts=poliProRecordService.count(qw);
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
