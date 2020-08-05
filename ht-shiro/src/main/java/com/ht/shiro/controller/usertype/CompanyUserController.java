package com.ht.shiro.controller.usertype;

import java.util.Map;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.usertype.CompanyUser;
import com.ht.shiro.service.SysUserService;
import com.ht.shiro.service.usertype.CompanyUserService;
import com.ht.utils.BaseEntityUtil;
import com.ht.vo.shiro.CompanyUserVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ht-shiro/compuser")
@Api(value="CompanyUserController",description = "企业用户")
public class CompanyUserController extends BaseController {
	
	@Autowired
	CompanyUserService companyUserService;

	@ApiOperation(value="查询企业用户集合", notes="查询企业用户集合")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
			@ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
			@ApiImplicitParam(name = "keyword", value = "关键字查询", dataType = "String")
	})
	@GetMapping(value="/list")
	public Respon list() {
		Respon respon = this.getRespon();
		try {
			MyPage page = this.getMyPage(getPageData());
			Page<PageData>	 list=companyUserService.findlist(page);
			respon.success(list);
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}
	@GetMapping(value="/findById")
	public Respon findById(String id) {
		Respon respon = this.getRespon();
		try {
			PageData pd = new PageData(  );
			pd.put( "id",id );
			PageData user =	companyUserService.findById(pd);
			respon.success( user );
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}

	@PostMapping(value="/update")
	@ApiOperation(value="修改", notes="修改")
	public Respon update(CompanyUser companyUser) {
		Respon respon = this.getRespon();
		try {
		    if(companyUserService.saveOrUpdate(companyUser)){
		    	respon.success( "修改成功" );
		    }else{
		    	respon.success( "修改失败" );
		    }
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}
	

}
