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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.RechargeService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.Recharge;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/sysrecharge")
@Api(value="SysRechargeController",description = "充值板块后台管理")
public class SysRechargeController extends BaseController{
	@Autowired
    private RechargeService rechargeService;
	@ApiOperation(value="后台充值板块列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "String"),
	})
	@PostMapping(value="/list")
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage<Recharge> page=getMyPage(pd);
			QueryWrapper<Recharge> qw=new QueryWrapper<Recharge>();
			qw.orderByAsc("honey");
			page=(MyPage<Recharge>) rechargeService.page(page,qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台充值板块单条数据查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping(value="/edit")
	public Respon edit(String id){
		Respon respon = this.getRespon();
		Recharge recharge=new Recharge();
		try {
			if(!StringUtils.isBlank(id)){
				recharge=rechargeService.getById(id);
			}
			respon.success(recharge);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台充值板块单条数据保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "recharge", value = "充值",  dataType = "PTemplate"),
	})
	@PostMapping(value="/save")
	public Respon save(@RequestBody Recharge recharge) throws Exception{
		Respon respon = this.getRespon();
		try {
			recharge.setNewmoney(recharge.getOldmoney()*recharge.getDiscount()*0.1-0.1);
			if(StringUtils.isBlank(recharge.getId())){
				recharge.setCreatedate(new Date());
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					recharge.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					recharge.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				rechargeService.save(recharge);
			}else{
				recharge.setLastmodified(new Date());
				rechargeService.updateById(recharge);
			}
			respon.success(recharge);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台充值板块单条数据删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "模板id",  dataType = "String"),
	})
	@GetMapping(value="/deleted")
	public Respon deleted(String ids){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(ids)){
				for(String id:ids.split(",")){
					rechargeService.removeById(id);
				}
				
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
}
