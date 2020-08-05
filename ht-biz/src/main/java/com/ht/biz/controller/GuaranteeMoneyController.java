package com.ht.biz.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.GuaranteeMoneyService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.GuaranteeMoney;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/guaran")
@Api(value="GuaranteeMoneyController",description = "前端保障金管理")
public class GuaranteeMoneyController extends BaseController{
	
	@Autowired
	private GuaranteeMoneyService guaranteeMoneyService;
	
	@ApiOperation(value="用户保证金记录")
	@ApiImplicitParams({
        //@ApiImplicitParam(paramType="query",name = "id", value = "正速递id",  dataType = "String"),
	})
	@GetMapping("list")
	public Respon list(MyPage page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				pd.add("createid", user.getUserId());
				page=getMyPage(pd);
				List<GuaranteeMoney> list=guaranteeMoneyService.findusergtm(page);
				page.setRecords(list);
				respon.success(page);
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
	
}
