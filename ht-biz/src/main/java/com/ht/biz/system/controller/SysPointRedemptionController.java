package com.ht.biz.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.RewardRuleService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.honeymanager.UserWaterReward;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.RewardUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Controller
@RestController
@RequestMapping(value="/sys/ht-biz/syspoint")
@Api(value = "SysPointRedemptionController", description = "后台流水账管理")
public class SysPointRedemptionController extends BaseController{
	
	@Autowired
    private PointRedemptionService pointRedemptionService;
	
	@Autowired
    private RewardRuleService rewardService;
	
	@ApiOperation(value="honey值流水账")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			List<PageData> list=pointRedemptionService.findList(page);
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="honey值流水账明细")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			UserWaterReward u=new UserWaterReward();
			if(StringUtils.isNoneBlank(id)){
				u=pointRedemptionService.getById(id);
			}
			respon.success(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="honey值流水账保存")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody UserWaterReward u){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNoneBlank(u.getId())){
				u.setLastmodified(new Date());
				pointRedemptionService.updateById(u);
			}else{
				u.setCreatedate(new Date());
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					u.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					u.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				pointRedemptionService.save(u);
			}
			respon.success(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="honey值流水账删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "ids", value = "数据id集合",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNoneBlank(ids)){
				for(String id:ids.split(",")){
					pointRedemptionService.removeById(id);
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
	@ApiOperation(value="内部员工添加honey")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "ids", value = "用户id集合",  dataType = "string"),
	})
	@PostMapping("addUserHoney")
	public Respon addUserHoney(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			String ids=pd.getString("ids")==null?"":String.valueOf(pd.getString("ids"));
			if(StringUtils.isNoneBlank(ids)){
				SysUser user=LoginUserUtils.getLoginUser();
				QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
				qw.eq("code", Code.add_honey.name());
				RewardRule rr=rewardService.getOne(qw,false);
				for(String id:ids.split(",")){
					RewardUtil.disHoney(rr, id, user.getUserId(), user.getCompanyid());
					RewardUtil.addPaymentOrder("线下支付", "t_paymentorder", null, 0d,Integer.parseInt(rr.getReturnValue()), 1,id);
				}
				respon.success("添加成功");
			}else{
				respon.error();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
