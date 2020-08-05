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
import com.ht.biz.service.GoldcoinRuleRecordService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.GoldcoinRuleRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/gocore")
@Api(value="SysGoldcoinRuleRecordController",description = "金币消费详情管理")
public class SysGoldcoinRuleRecordController extends BaseController {
	
	@Autowired
	private GoldcoinRuleRecordService goldcoinRuleRecordService;
	
	@ApiOperation(value="金币值消费详情")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage<GoldcoinRuleRecord> page=getMyPage(pd);
			QueryWrapper<GoldcoinRuleRecord> qw=new QueryWrapper<GoldcoinRuleRecord>();
			String deleted=pd.get("deleted")==null?"0":String.valueOf(pd.get("deleted"));
			qw.eq("deleted", deleted);
			page=(MyPage<GoldcoinRuleRecord>) goldcoinRuleRecordService.page(page,qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="金币值消费详情明细")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			GoldcoinRuleRecord gc=new GoldcoinRuleRecord();
			if(StringUtils.isNotBlank(id)){
				gc=goldcoinRuleRecordService.getById(id);
			}
			respon.success(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="金币值消费详情保存")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "RewardRule", value = "数据id",  dataType = "string"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody GoldcoinRuleRecord gc){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(gc.getId())){
				gc.setLastmodified(new Date());
				goldcoinRuleRecordService.updateById(gc);
			}else{
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					gc.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					gc.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				gc.setCreatedate(new Date());
				goldcoinRuleRecordService.save(gc);
			}
			respon.success(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="金币值消费详情删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(ids)){
				for(String id:ids.split(",")){
					goldcoinRuleRecordService.removeById(id);
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
}
