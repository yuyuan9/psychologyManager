package com.ht.biz.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.OrderService;
import com.ht.biz.service.ProductService;
import com.ht.biz.service.SerprociderService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.msg.WorkReminder.Work;
import com.ht.entity.biz.product.Order;
import com.ht.entity.biz.product.Product;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.usertype.ServiceProvider;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/ht-biz/myorder")
@Api(value = "OrderMyController", description = "前端订单管理")
public class OrderMyController extends BaseController{
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SerprociderService serprociderService;
	
	@Autowired
    private ProductService productService;
	
	@ApiOperation(value="我的订单记录")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "serviceId", value = "服务商id识别，传递该值表示查询服务商的订单，不传查询普通用户的订单",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "status", value = "status=0待服务status=1交易完成status=2拒接服务",  dataType = "String"),
	})
	@PostMapping("list")
	public Respon list(MyPage page,String serviceId){
 		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				if(StringUtils.isNotBlank(serviceId)){
					pd.add("serviceId", user.getUserId());
				}else{
					pd.add("userId", user.getUserId());
				}
				if(pd.get("status")!=null&&StringUtils.equals("", String.valueOf(pd.get("status")))){
					pd.remove("status");
				}
				page=getMyPage(pd);
				List<PageData> list=orderService.findlist(page);
				page.setRecords(list);
				List<PageData> lists=orderService.listCounts(pd);
				respon.setReserveData(lists);
			}
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="前端订单数据详情")
	@ApiImplicitParams({
	    @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			Order order=null;
			if(StringUtils.isNotBlank(id)){
				order=orderService.getById(id);
				QueryWrapper<ServiceProvider> qw=new QueryWrapper<ServiceProvider>();
				qw.eq("createid", order.getCreateid());
				respon.setReserveData(serprociderService.getOne(qw, false));
			}
			respon.success(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="订单修改")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "订单id",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "status", value = "订单状态(status=0待服务status=1交易完成status=2拒接服务)",  dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "userDeleted", value = "普通用户删除标识0表示未删除1表示已经删除",  dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "serviceDeleted", value = "服务商删除标识0表示未删除1表示已经删除",  dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "refuse", value = "拒接理由",  dataType = "String"),
	})
	@PostMapping("save")
	public Respon save(Order order){
		Respon respon=this.getRespon();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(StringUtils.isNotBlank(order.getId())){
				order.setLastmodified(new Date());
				orderService.updateOrder(order);
				//普通用户订单删除(取消订单)
				if(order.getUserDeleted()==1){
					MsgUtil.addMsg(Work.user_order_dispose.name(), null, user.getUserId(), user.getUserId(), user.getCompanyid());
				}
				//服务商接单成功
				if(order.getStatus()==1){
					Order o=orderService.getById(order.getId());
					MsgUtil.addMsg(Work.user_receive_complete.name(), null, o.getCreateid(), o.getCreateid(), null);
					MsgUtil.addMsg(Work.sprovider_receive_complete.name(), null, o.getServiceId(), o.getServiceId(), null);
				}
				//服务商拒绝接单
				if(order.getStatus()==2){
					Order o=orderService.getById(order.getId());
					MsgUtil.addMsg(Work.user_order_refuse.name(), null, o.getCreateid(), o.getCreateid(), null);
					MsgUtil.addMsg(Work.sprovider_order_refuse.name(), null, o.getServiceId(), o.getServiceId(), null);
				}
			}else{
				Product p=productService.getById(order.getProductId());
				order.setNumber(Const.getOrderNo());
				order.setCreatedate(new Date());
				order.setCreateid(user.getUserId());
				order.setServiceId(p.getCreateid());
				order.setRegionid(user.getCompanyid());
				orderService.save(order);
				MsgUtil.addMsg(Work.user_order_success.name(), null, user.getUserId(), user.getUserId(), user.getCompanyid());
				MsgUtil.addMsg(Work.sprovider_order_success.name(), "/ht-biz/service/index/order", p.getCreateid(), p.getCreateid(), null);
			}
			respon.success(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="前端订单数据删除")
	@ApiImplicitParams({
	    @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String id){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(id)){
				orderService.removeById(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
