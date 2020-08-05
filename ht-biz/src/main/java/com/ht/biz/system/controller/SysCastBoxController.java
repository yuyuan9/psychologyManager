package com.ht.biz.system.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.CastBoxService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.recore.CastBox;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/castbox")
@Api(value="CastBoxController",description = "后台用户收费弹框记录")
public class SysCastBoxController extends BaseController{
	
	@Autowired
	private CastBoxService castBoxService;
	
	@ApiOperation(value="后台所有用户收费弹框记录")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "code", value = "每个弹框对应code值",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "module", value = "每个弹框对应模块",  dataType = "String"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage<CastBox> page=getMyPage(pd);
			QueryWrapper<CastBox> qw=new QueryWrapper<CastBox>();
			if(pd.get("code")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("code")))){
				qw.like("code", pd.get("code"));
			}
			if(pd.get("module")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("module")))){
				qw.like("module", pd.get("module"));
			}
			if(pd.get("phone")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("phone")))){
				qw.like("phone", pd.get("phone"));
			}
			page=(MyPage<CastBox>) castBoxService.page(page, qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="后台用户收费弹框记录删除")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "code", value = "每个弹框对应code值",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "module", value = "每个弹框对应模块",  dataType = "String"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(ids)){
				for(String id:ids.split(",")){
					castBoxService.removeById(id);
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
