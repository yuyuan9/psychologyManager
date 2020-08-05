package com.ht.biz.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.CastBoxService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.entity.biz.solr.recore.CastBox;
import com.ht.entity.biz.solr.recore.CastBox.Box;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/castbox")
@Api(value="CastBoxController",description = "收费弹框记录")
public class CastBoxController extends BaseController{
	
	@Autowired
	private CastBoxService castBoxService;
	
	@ApiOperation(value="查询用户是否点击过")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "code", value = "每个弹框对应code值",  dataType = "String"),
	})
	@GetMapping("userbox")
	public Respon userBox(String code){
		Respon respon=this.getRespon();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				QueryWrapper<CastBox> qw=new QueryWrapper<CastBox>();
				qw.eq("userId", user.getUserId());
				qw.eq("code", code);
				qw.like("createdate", DateUtil.dateToStr(new Date(), 11));
				CastBox c=castBoxService.getOne(qw, false);
				if(c!=null){
					respon.success(c);
				}else{
					respon.error(c);
				}
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
	@ApiOperation(value="保存用户击过的收费框")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "code", value = "每个弹框对应code值",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "module", value = "每个弹框对应模块",  dataType = "String"),
	})
	@PostMapping("save")
	public Respon save(CastBox c){
		Respon respon=this.getRespon();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			c.setUserId(user.getUserId());
			c.setCreatedate(new Date());
			c.setCreateid(user.getUserId());
			c.setRemark(Box.getValue(c.getCode()));
			c.setRegionid(user.getCompanyid());
			c.setPhone(user.getPhone());
			castBoxService.save(c);
			respon.success(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="删除用户击过的收费框")
	@ApiImplicitParams({
	})
	@GetMapping("deleted")
	public Respon deleted(){
		Respon respon=this.getRespon();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				QueryWrapper<CastBox> qw=new QueryWrapper<CastBox>();
				qw.eq("userId", user.getUserId());
				castBoxService.remove(qw);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respon;
	}
}
