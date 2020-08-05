package com.ht.biz.system.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.MatchRecordService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.policymatch.MatchRecord;
import com.ht.utils.MatchRecordUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/matchrecord")
@Api(value="SysMatchRecordController",description = "政策匹配记录后台管理")
public class SysMatchRecordController extends BaseController{
	@Autowired
	private MatchRecordService matchRecordService;
	
	@ApiOperation(value="后台政策匹配记录接口")
	@ApiImplicitParams({
        //@ApiImplicitParam(paramType="query",name = "projectName", value = "项目名称",  dataType = "String"),
	})
	@PostMapping(value="/list")
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			QueryWrapper<MatchRecord> qw=new QueryWrapper<MatchRecord>();
			qw.orderByDesc("createdate");
			matchRecordService.page(page, qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="后台政策匹配记录删除")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "",  dataType = "string"),
	})
	@GetMapping(value="/deleted")
	public Respon save(String ids) throws Exception{
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(ids)){
				for(String id:ids.split(",")){
					matchRecordService.removeById(id);
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
//	@GetMapping(value="/add")
//	public Respon save() throws Exception{
//		Respon respon = this.getRespon();
//		MatchRecordUtil.save("10", "10", "10", "10", "10", "10", "10", "1", "1");
//		MatchRecordUtil.save("11", "11", "11", "11", "11", "11", "11", "1", "1");
//		return respon;
//	}
}
