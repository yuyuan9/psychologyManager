package com.ht.biz.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.PaymentOrderService;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.RechargeService;
import com.ht.biz.service.RewardRuleService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.PaymentOrder;
import com.ht.entity.biz.honeymanager.Recharge;
import com.ht.entity.biz.honeymanager.PaymentOrder.bustype;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.entity.shiro.constants.UserType;
import com.ht.utils.RewardUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/syspayment")
@Api(value="SysPaymentOrderController",description = "支付后台管理")
public class SysPaymentOrderController extends BaseController{
	
	@Autowired
	private PaymentOrderService paymentOrderService;
	
	@Autowired
	private RechargeService rechargeService;
	
	@Autowired
	private RewardRuleService rewardService;
	
	@Autowired
	private PointRedemptionService uwrService;
	
	@ApiOperation(value="支付")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			List<PageData> list=paymentOrderService.findList(page);
			for(PageData pds:list){
				String usertype=pds.get("userType")==null?"":String.valueOf(pds.get("userType"));
				String businessType=pds.get("businessType")==null?"":String.valueOf(pds.get("businessType"));
				pds.add("userType", UserType.getValue(usertype));
				if(StringUtils.isNotBlank(businessType)){
					pds.add("businessType", bustype.getValue(bustype.valueOf(businessType).value, "${honeys}", pds.get("honeys")==null?"0":(pds.get("honeys")+"")));
				}
			}
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="支付明细")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			PaymentOrder po=new PaymentOrder();
			if(StringUtils.isNoneBlank(id)){
				po=paymentOrderService.getById(id);
			}
			respon.success(po);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="支付保存")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody PaymentOrder po){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNoneBlank(po.getId())){
				po.setLastmodified(new Date());
				paymentOrderService.updateById(po);
			}else{
				po.setCreatedate(new Date());
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					po.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					po.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				paymentOrderService.save(po);
			}
			respon.success(po);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="支付删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "ids", value = "数据id集合",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNoneBlank(ids)){
				for(String id:ids.split(",")){
					paymentOrderService.removeById(id);
				}
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="支付异常处理")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("errDeal")
	public Respon errDeal(String id){
		Respon respon=this.getRespon();
		try {
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				if(StringUtils.isNotBlank(id)){
					PaymentOrder p=paymentOrderService.getById(id);
					if(StringUtils.equals(p.getPaymentCash(), "4")){
						Recharge r=rechargeService.getById(p.getBusinessId());
						RewardUtil.disHoney(String.valueOf(Code.honey_recharge), Double.valueOf(r.getHoney()), p.getCreateid(), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
					}
					p.setStatus(1);
					paymentOrderService.updateById(p);
				}
				respon.success(id);
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
	@ApiOperation(value="支付微信累计交易")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "alipay", value = "支付宝",  dataType = "string"),
		@ApiImplicitParam(paramType="query",name = "WeChat", value = "微信",  dataType = "string"),
	})
	@GetMapping("cumulative")
	public Respon cumulative(){
		Respon respon=this.getRespon();
		try {
			Map<String,Integer> map=new HashMap<String,Integer>();
			Integer alipay=paymentOrderService.selectAmount("alipay");
			alipay=alipay==null?0:alipay;
			Integer WeChat=paymentOrderService.selectAmount("WeChat");
			WeChat=WeChat==null?0:WeChat;
			map.put("alipay", alipay);map.put("WeChat", WeChat);
			respon.success(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respon;
	}
}
