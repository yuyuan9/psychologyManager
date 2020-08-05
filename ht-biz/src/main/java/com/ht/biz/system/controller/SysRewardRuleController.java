package com.ht.biz.system.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.RewardRuleService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.RewardRule;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/sysrerule")
@Api(value="SysRewardRuleController",description = "honey值规则后台管理")
public class SysRewardRuleController extends BaseController {
	
	@Autowired
	private RewardRuleService rewardRuleService;
	
	@ApiOperation(value="honey值规则")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "deleted", value = "是否启用（启用传1，不启用传0）",  dataType = "string"),
		@ApiImplicitParam(paramType="query",name = "name", value = "标题名称",  dataType = "string"),
		@ApiImplicitParam(paramType="query",name = "modular", value = "模块",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage<RewardRule> page=getMyPage(pd);
			QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
			String deleted=pd.get("deleted")==null?"0":String.valueOf(pd.get("deleted"));
			qw.eq("deleted", deleted);
			if(pd.get("name")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("name")))){
				qw.like("name", pd.get("name"));
			}
			if(pd.get("modular")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("modular")))){
				qw.like("modular", pd.get("modular"));
			}
			qw.orderByAsc("sort");
			page=(MyPage<RewardRule>) rewardRuleService.page(page,qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="honey值规则明细")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			RewardRule rr=new RewardRule();
			if(StringUtils.isNotBlank(id)){
				rr=rewardRuleService.getById(id);
			}
			respon.success(rr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="honey值规则保存")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "RewardRule", value = "数据id",  dataType = "string"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody RewardRule rr){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(rr.getId())){
				rr.setLastmodified(new Date());
				rewardRuleService.updateById(rr);
			}else{
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					rr.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					rr.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				rewardRuleService.save(rr);
			}
			respon.success(rr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="honey值规则删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(ids)){
				for(String id:ids.split(",")){
					rewardRuleService.removeById(id);
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
	@ApiOperation(value="根据code值查询相关honey值")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "code", value = "数据code",  dataType = "string"),
	})
	@GetMapping("findByCode")
	public Respon findByCode(String code){
		Respon respon=this.getRespon();
		try {
			RewardRule rr=new RewardRule();
			if(StringUtils.isNotBlank(code)){
				QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
				qw.eq("code", code);
				rr=rewardRuleService.getOne(qw, false);
			}
			respon.success(rr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
