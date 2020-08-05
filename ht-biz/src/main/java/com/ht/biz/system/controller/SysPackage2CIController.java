package com.ht.biz.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.Package2CIService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.Package2CI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/syspackci")
@Api(value="SysPackage2CIController",description = "政策包关联客户后台管理")
public class SysPackage2CIController extends BaseController{
	
	@Autowired
	private Package2CIService package2CIService;
	
	@ApiOperation(value="后台政策推送政策包关联客户列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "政策包id",  dataType = "String"),
	})
	@GetMapping(value="list")
	public Respon list(String id){
		Respon respon=this.getRespon();
		List<PageData> list=new ArrayList<PageData>();
		try {
			if(!StringUtils.isBlank(id)){
				list=package2CIService.findByPackageId(id);
			}
			respon.success(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送政策包关联客户保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "政策id封装",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "packageId", value = "政策包id",  dataType = "String"),
	})
	@PostMapping(value="save")
	public Respon save(@RequestBody PageData pd) throws Exception{
		Respon respon=this.getRespon();
		try {
			Package2CI p=new Package2CI();
			if(pd.get("ids")!=null&&pd.get("packageId")!=null){
				for(String id:String.valueOf(pd.get("ids")).split(",")){
					p.setCiId(id);
					p.setPackageId(String.valueOf(pd.get("packageId")));
					Object object=package2CIService.getshortUrl("http://api.t.sina.com.cn/short_url/shorten.json?source=3271760578&url_long=http://39.108.193.166:8085/ht-biz/policydig/app/index?packageId="+String.valueOf(pd.get("packageId"))+"@"+id);
					p.setCiIdUrl(object==null?"":String.valueOf(object));
					if(package2CIService.findByIdAndPackageId(p)==null){
						p.setCreatedate(new Date());
						Map<String ,Object > map=getLoginInfo();
						if(map!=null){
							p.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
							p.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
						}
						package2CIService.insert(p);
					}
					p=new Package2CI();
				}
			}
			
			respon.success(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送政策包关联客户删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "package2CI数据封装",  dataType = "String"),
	})
	@GetMapping(value="deleted")
	public Respon deleted(String id) throws Exception{
		Respon respon=this.getRespon();
		try {
			if(!StringUtils.isBlank(id)){
				package2CIService.deleted(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送政策包关联客户根据政策包删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "packageId", value = "政策包packageId",  dataType = "String"),
	})
	@GetMapping(value="deleteByPackageId")
	public Respon deleteByPackageId(String packageId) throws Exception{
		Respon respon=this.getRespon();
		try {
			if(!StringUtils.isBlank(packageId)){
				package2CIService.deleteByPackageId(packageId);
			}
			respon.success(packageId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
}
