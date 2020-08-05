package com.ht.biz.system.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.Package2CIService;
import com.ht.biz.service.Package2PolicyService;
import com.ht.biz.service.PolicyPackageSendService;
import com.ht.biz.service.PolicyPackageService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.SMSConfig;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.policydig.Package2CI;
import com.ht.entity.policydig.Package2Policy;
import com.ht.entity.policydig.PolicyPackage;
import com.ht.entity.policydig.PolicyPackageSend;
import com.ht.utils.SendCodeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/syspack")
@Api(value="SysPolicyPackageController",description = "政策包后台管理")
public class SysPolicyPackageController extends BaseController{
	
	@Autowired
    private PolicyPackageService policyPackageService;
	
	@Autowired
	private Package2PolicyService package2PolicyService;
	
	@Autowired
	private PolicyPackageSendService policyPackageSendService;
	
	@Autowired
	private Package2CIService package2CIService;
	
	@ApiOperation(value="后台政策推送包列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省份" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "status", value = "状态", dataType = "int"),
	})
	@PostMapping(value="/list")
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			page.setPd(pd);
			page.setRecords(policyPackageService.findlistPage(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送包单条数据查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "包id",  dataType = "String"),
	})
	@GetMapping(value="/edit")
	public Respon edit(String id){
		Respon respon = this.getRespon();
		PolicyPackage policyPackage=new PolicyPackage();
		try {
			if(StringUtils.isBlank(id)||StringUtils.equals("insert", id)){
				policyPackage.setId(UuidUtil.get32UUID());
			}else{
				policyPackage=policyPackageService.findById(id);
				List<PageData> policydigs=package2PolicyService.findByPackageId(id);//查询关联政策
				List<PageData> customers=package2CIService.findByPackageId(id);//查询关联客户
				StringBuffer subpo=new StringBuffer("");
				StringBuffer subci=new StringBuffer("");
				for(int i=0;i<policydigs.size();i++){
					PageData p=policydigs.get(i);
					subpo.append(i==(policydigs.size()-1)?p.get("titleid"):(p.get("titleid")+","));
				}
				for(int i=0;i<customers.size();i++){
					PageData p=customers.get(i);
					subci.append(i==(policydigs.size()-1)?p.get("ciId"):(p.get("ciId")+","));
				}
				policyPackage.setPolicydigIds(subpo.toString());
				policyPackage.setCustomerIds(subci.toString());
			}
			respon.success(policyPackage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送包单条数据保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "policyPackage", value = "包id",  dataType = "policyPackage"),
        @ApiImplicitParam(paramType="query",name = "policydigIds", value = "关联政策ids",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "customerIds", value = "关联政策ids",  dataType = "String"),
	})
	@PostMapping(value="/save")
	public Respon save(@RequestBody PolicyPackage policyPackage) throws Exception{
		Respon respon = this.getRespon();
		try {
			PolicyPackage p=policyPackageService.findById(policyPackage.getId());
			if(p==null){
				policyPackage.setCreatedate(new Date());
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					policyPackage.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					policyPackage.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				policyPackageService.save(policyPackage);
			}else{
				policyPackage.setLastmodified(new Date());
				policyPackageService.edit(policyPackage);
			}
			respon.success(policyPackage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送包单条数据删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "包id",  dataType = "String"),
	})
	@GetMapping(value="/deleted")
	public Respon deleted(String id){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(id)){
				policyPackageService.deleted(id);
				package2PolicyService.deleteByPackageId(id);//删除关联政策
				package2CIService.deleteByPackageId(id);//删除关联客户
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台政策推送包发送接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "包id",  dataType = "String"),
	})
	@GetMapping(value="/send")
	public Respon send(String id){
		//boolean sendSuccess = sendSmsUtil.send(pd.getString("phone"), template, new String[] { pd.getString("province")+pd.getString("city")+pd.getString("area")+",", DateUtil.formatDatess(policyPackage.getSendTime()),pd.getString("ciIdUrl") }, false,SendSmsUtil.system);
		Respon respon = this.getRespon();
		PageData pd=this.getPageData();
		List<PageData> list=null;
		if(!StringUtils.isBlank(id)){
			try {
				PolicyPackage p=policyPackageService.findById(id);
				if(p.getStatus()==2){
					pd.add("sendStatus", "0");
					pd.add("packageId", p.getId());
					MyPage page=getMyPage(pd);
					list=policyPackageSendService.findlistPage(page);
				}else if(p.getStatus()==3){
					list=package2CIService.findByPackageId(p.getId());
				}else{
					respon.success("已全部发送成功");
				}
				if(list!=null){
					//发送短信
					if(p.getMsg()){
						policyPackageService.sendMsg(list, p, policyPackageSendService, 1, getLoginInfo());
					}
					//发送邮箱(待开发)
//					if(p.getEmail()){
//						
//					}
				}
				respon.success("成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				respon.error();
			}
		}
		return respon;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
