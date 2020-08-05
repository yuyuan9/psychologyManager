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
import com.ht.biz.service.PolicyMatchService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.policymatch.PolicyMatch;
import com.ht.entity.biz.policymatch.PolicyMatch.Match;
import com.ht.entity.biz.policymatch.PolicyMatchVo;
import com.ht.entity.policydig.PolicydigVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/policymatch")
@Api(value="SysPolicyMatchController",description = "政策匹配后台管理")
public class SysPolicyMatchController extends BaseController{
	
	@Autowired
	private PolicyMatchService policyMatchService;
	
	@ApiOperation(value="后台政策匹配列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "projectName", value = "项目名称",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "matchType", value = "匹配分类(1区域匹配2领域规模匹配)",  dataType = "String"),
	})
	@PostMapping(value="/list")
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			QueryWrapper<PolicyMatch> qw=new QueryWrapper<PolicyMatch>();
			if(pd.get("projectName")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("projectName")))){
				qw.like("projectName", pd.get("projectName"));
			}
			if(pd.get("matchType")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("matchType")))){
				qw.eq("matchType", pd.get("matchType"));
			}
			qw.orderByDesc("createdate");
			policyMatchService.page(page, qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策匹配数据查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
	})
	@GetMapping(value="/edit")
	public Respon edit(String id){
		Respon respon = this.getRespon();
		PolicyMatch p=null;
		try {
			if(!StringUtils.isBlank(id)){
				p=policyMatchService.getById(id);
				p.setPmv(new PolicyMatchVo(p.getApplyField1(),p.getApplyField2(),p.getScaleCode(),p.getApplyScale1(),p.getApplyScale2(),p.getApplyScale3()));
			}
			respon.success(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策匹配数据保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "policyMatch", value = "",  dataType = "PTemplate"),
	})
	@PostMapping(value="/save")
	public Respon save(@RequestBody PolicyMatch policyMatch) throws Exception{
		Respon respon = this.getRespon();
		try {
			Match.setMatch(policyMatch);
			if(StringUtils.isBlank(policyMatch.getId())){
				policyMatch.setId("0");
				policyMatch.setCreatedate(new Date());
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					policyMatch.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					policyMatch.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				policyMatchService.save(policyMatch);
			}else{
				policyMatch.setLastmodified(new Date());
				policyMatchService.updateById(policyMatch);
			}
			respon.success(policyMatch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策匹配数据删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "数据id集合",  dataType = "String"),
	})
	@GetMapping(value="/deleted")
	public Respon deleted(String ids){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(ids)){
				for(String id:ids.split(",")){
					policyMatchService.removeById(id);
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
