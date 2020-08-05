package com.ht.biz.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.OrderUserCompService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.OrderUserComp;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/ht-biz/oucomp")
@Api(value = "OrderUserCompController", description = "前端下单人信息管理")
public class OrderUserCompController extends BaseController{
	@Autowired
	private OrderUserCompService orderUserCompService;
	
	@ApiOperation(value="下单人信息管理")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "defaults", value = "defaults=1查询默认下单人信息,不传时查询所有信息",  dataType = "String"),
	})
	@GetMapping("list")
	public Respon list(MyPage page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				QueryWrapper<OrderUserComp> qw=new QueryWrapper<OrderUserComp>();
				qw.eq("createid", user.getUserId());
				if(pd.get("defaults")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("defaults")))){
					qw.eq("defaults", pd.get("defaults"));
				}
				qw.orderByDesc("defaults","createdate");
				orderUserCompService.page(page, qw);
				respon.success(page);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="下单人信息查看")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			OrderUserComp orderUserComp=null;
			if(StringUtils.isNotBlank(id)){
				orderUserComp=orderUserCompService.getById(id);
			}
			respon.success(orderUserComp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="下单人信息保存")
	@ApiImplicitParams({
        //@ApiImplicitParam(paramType="query",name = "serviceId", value = "服务商id识别，传递该值表示查询服务商的订单，不传查询普通用户的订单",  dataType = "String"),
	})
	@PostMapping("save")
	public Respon save(OrderUserComp orderUserComp){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isBlank(orderUserComp.getId())){
				orderUserComp.setCreatedate(new Date());
				orderUserComp.setCreateid(LoginUserUtils.getLoginUser().getUserId());
				orderUserComp.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
				orderUserCompService.save(orderUserComp);
			}else{
				orderUserComp.setLastmodified(new Date());
				orderUserCompService.updateById(orderUserComp);
			}
			respon.success(orderUserComp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="下单人信息删除")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping("deleted")
	public Respon deleted(String id){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(id)){
				orderUserCompService.removeById(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="设为默认")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "设为默认的数据id",  dataType = "String"),
	})
	@GetMapping("defaulted")
	public Respon defaulted(String id){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			if(StringUtils.isNotBlank(id)){
				pd.add("id", id);
				pd.add("createid", LoginUserUtils.getLoginUser().getUserId());
				orderUserCompService.updateDefaults(pd);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="下单时用户新填写的下单信息")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "companyName", value = "判断企业名称是否存在",  dataType = "String"),
	})
	@PostMapping("ordersave")
	public Respon ordersave(OrderUserComp orderUserComp){
		Respon respon=this.getRespon();
		try {
			orderUserComp.setCreatedate(new Date());
			orderUserComp.setCreateid(LoginUserUtils.getLoginUser().getUserId());
			orderUserComp.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
			QueryWrapper<OrderUserComp> qw=new QueryWrapper<OrderUserComp>();
			qw.eq("companyName", orderUserComp.getCompanyName());
			OrderUserComp ouc=orderUserCompService.getOne(qw, false);
			if(ouc==null){
				orderUserCompService.save(orderUserComp);
			}else{
				orderUserComp.setId(ouc.getId());
				orderUserComp.setLastmodified(new Date());
				orderUserCompService.updateById(orderUserComp);
			}
			respon.success("添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
