package com.ht.shiro.controller.usertype;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.usertype.CompanyBank;
import com.ht.shiro.service.usertype.CompanyBankService;
import com.ht.utils.BaseEntityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ht-shiro/compbank")
@Api(value="CompanyBankController",description = "企业银行管理")
public class CompanyBankController extends BaseController {
	
	@Autowired
	CompanyBankService companybankService;
	
	@ApiOperation(value="企业银行列表", notes="查询企业银行列表")
	   @ApiImplicitParams({
	     @ApiImplicitParam(paramType="query", name = "companyuserid", value = "关联企业用户id查询列表信息", required = false, dataType = "String"),
	})
	@GetMapping(value="/list")
	public Respon list() {
		Respon respon = this.getRespon();
		try {
			MyPage page = this.getMyPage(this.getPageData());
			Page<CompanyBank> list=companybankService.findListpage(page);
			respon.success(list);
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="企业银行保存", notes="企业银行信息添加")
	   @ApiImplicitParams({
	     @ApiImplicitParam(paramType="query", name = "companyuserid", value = "企业用户外键", required = true, dataType = "String"),
	     @ApiImplicitParam(paramType="query", name = "name", value = "银行名称", required = true, dataType = "String"),
	     @ApiImplicitParam(paramType="query", name = "no", value = "银行账户", required = true, dataType = "String"),
	     @ApiImplicitParam(paramType="query", name = "payee", value = "收款人", required = true, dataType = "String"),
	})
	@PostMapping(value="/save")
	public Respon save(@RequestBody CompanyBank companybank)throws Exception{
		Respon respon = this.getRespon();
		try {
			BaseEntityUtil.getBaseEntity(companybank);
			boolean save=companybankService.saveOrUpdate(companybank);
			respon.success(save);
		}catch(Exception e) {
			respon.error(e); 
		} 
		return respon;
	}
	
	@GetMapping(value="/delete/{id}")
	public Respon delete(@PathVariable String id)throws Exception{
		Respon respon = this.getRespon();
		try {
			boolean save=companybankService.removeById(id);
			respon.success(save);
		}catch(Exception e) {
			respon.error(e);
		}
		
		return respon;
	}
	

	
	

}
