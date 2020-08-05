package com.ht.biz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.GoldcoinRuleRecordService;
import com.ht.biz.service.LevelService;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.RewardRuleService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.GoldcoinRuleRecord;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.honeymanager.UserWaterReward;
import com.ht.entity.biz.sys.Level;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Api(value = "PointRedemptionController", description = "前端流水账管理")

@RequestMapping(value="/ht-biz/honeymanager")
public class PointRedemptionController  extends BaseController {
    @Autowired
    private PointRedemptionService pointRedemptionService;
    
    @Autowired
    private LevelService levelService;
    
    @Autowired
	private GoldcoinRuleRecordService goldcoinRuleRecordService;
    
    @Autowired
	private RewardRuleService rewardRuleService;
    
    @GetMapping("/getHoneyTotal")
    @ApiOperation(value="获取用户honey",notes="")
    public Respon getHoneyTotal() throws Exception{
        Respon respon = this.getRespon();
        SysUser user = LoginUserUtils.getLoginUser();
        if(user != null){
            Double honeytotal = pointRedemptionService.getWaterTotal(user.getUserId());
            respon.success(honeytotal);
        }else{
            respon.error(  );
        }
        return respon;
    }
    
    @GetMapping("/getHoneyByCode")
    @ApiOperation(value="获取某一部分honey值",notes="")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "code", value = "honey值规则code值",  dataType = "string"),
	})
    public Respon getHoneyByCode(String code) throws Exception{
        Respon respon = this.getRespon();
        try {
			QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
			qw.eq("code", code);
			RewardRule rr=rewardRuleService.getOne(qw, false);
			respon.success(rr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
        return respon;
    }
    
    @ApiOperation(value="用户honey值账单明细")
	@ApiImplicitParams({
        //@ApiImplicitParam(paramType="query",name = "path", value = "文件路径",  dataType = "string"),
	})
    @GetMapping("/getUserWater")
    public Respon getUserWater(MyPage page) throws Exception{
    	Respon respon = this.getRespon();
    	Map<String,Object> map=getLoginInfo();
    	PageData pd=this.getPageData();
    	if(map!=null){
    		pd.add("userId", map.get("userId"));
    		page.setPd(pd);
    		List<PageData> list=pointRedemptionService.selectRewrd(page);
    		page.setRecords(list);
    		respon.success(page);
    	}else{
    		respon.loginerror("未登录");
    	}
		return respon;
    }
    @ApiOperation(value="获取用户等级")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "type", value = "type=1普通用户，type=2服务商或者专家",  dataType = "string"),
	})
    @GetMapping("/getUserLevel")
    public Respon getUserLevel(String type) throws Exception{
    	Respon respon = this.getRespon();
    	Map<String,Object> map=getLoginInfo();
    	if(map!=null){
    		Double honeytotal=pointRedemptionService.getWaterTotal(String.valueOf(map.get("userId")));
    		honeytotal=honeytotal==null?0:honeytotal;
    		if(StringUtils.isBlank(type)){
    			type="1";
    		}
    		QueryWrapper<Level> qw=new QueryWrapper<Level>();
    		qw.eq("type", type);
    		qw.le("point", honeytotal);
    		qw.orderByDesc("point");
    		Level level=levelService.getOne(qw, false);
    		respon.success(level);
    	}else{
    		respon.loginerror("未登录");
    	}
		return respon;
    }
    
    @ApiOperation(value="获取用户金币记录详情")
	@ApiImplicitParams({
       // @ApiImplicitParam(paramType="query",name = "path", value = "文件路径",  dataType = "string"),
	})
    @GetMapping("/getGoldcoin")
    public Respon getGoldcoin(MyPage page) throws Exception{
    	Respon respon = this.getRespon();
    	PageData pd=this.getPageData();
    	Map<String,Object> map=getLoginInfo();
    	if(map!=null){
    		pd.add("userId", map.get("userId"));
    		page=getMyPage(pd);
    		page.setRecords(goldcoinRuleRecordService.findGoldcoin(page));
    		respon.success(page);
    	}else{
    		respon.loginerror("未登录");
    	}
		return respon;
    }
    @ApiOperation(value="获取用户金币值总数")
	@ApiImplicitParams({
       // @ApiImplicitParam(paramType="query",name = "path", value = "文件路径",  dataType = "string"),
	})
    @GetMapping("/getGoldCounts")
    public Respon getGoldCounts() throws Exception{
    	Respon respon = this.getRespon();
    	PageData pd=this.getPageData();
    	Map<String,Object> map=getLoginInfo();
    	try {
    		Map<String,Object> map2=new HashMap<String,Object>();
			if(map!=null){
				//未兑换
				pd.add("userId", map.get("userId"));
				pd.add("exchange", 0);
				Double d1=goldcoinRuleRecordService.findCounts(pd);
				d1=d1==null?0d:d1;
				//已兑换
				pd.add("exchange", 1);
				Double d2=goldcoinRuleRecordService.findCounts(pd);
				d2=d2==null?0d:d2;
				map2.put("d1", d1);
				map2.put("d2", d2);
				map2.put("d3", d1+d2);//总和
				respon.success(map2);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
    }
}
