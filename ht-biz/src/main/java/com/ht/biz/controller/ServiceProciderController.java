package com.ht.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.JsonObject;
import com.ht.biz.service.SerprociderService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.entity.shiro.usertype.ServiceProvider;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;

import freemarker.template.utility.StringUtil;
import io.swagger.annotations.*;


import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jied
 *
 */
@RestController
@RequestMapping(value="/ht-biz/service")
@Api(value="IndexController",description = "前台服务商")
public class ServiceProciderController extends BaseController{


	@ApiOperation(value="跳转到服务商页面",notes="跳转到服务商页面")
	@GetMapping(value="/index/{page}")
	public ModelAndView page(@PathVariable String page) {
		return this.setViewName("service/"+page);
	}


	@Autowired
	private SerprociderService serprociderService;
	@ApiImplicitParams({
			@ApiImplicitParam(name = "stats", value = "审批状态，驳回再提条申请时传入 stats=1",  dataType = "String")


	})
	@PostMapping("savaService")
	@ApiOperation(value="修改店铺信息",notes="修改店铺信息")
	public Respon savaService(@ApiParam(name = "ServiceProvider对象", value = "传入json格式", required = true)  ServiceProvider serviceProvider) throws IllegalAccessException {

		Respon respon = new Respon();
		PageData pd = new PageData(  );
		if(null!=LoginUserUtils.getLoginUser()){
			pd.put( "userId" ,LoginUserUtils.getLoginUser().getUserId() );
			if(null!=serprociderService.findById(pd )){//修改
				serviceProvider.setId(serprociderService.findById(pd ).getString("id"));
				if(serprociderService.updateById(serviceProvider)){
				//	MsgUtil.addMsg( WorkReminder.Work.server_register.getName(),null,  LoginUserUtils.getLoginUser().getUserId(),LoginUserUtils.getLoginUser().getUserId(), null);
					respon.success( "成功" );
				}else{
					respon.error( "失败" );
				}
			}else{
                if(serprociderService.save( serviceProvider )){
					respon.success( "成功" );
				}else{
					respon.error( "失败" );
				}
            }
		}else{
            respon.loginerror( "未登录" );
        }

		return respon;
	}


	@PostMapping("judge")
	@ApiOperation(value="判断申请服务商状态",notes="判断申请服务商状态")
	public Respon  judge() {
		Respon respon = new Respon();
		SysUser user = LoginUserUtils.getLoginUser();
		if (null != user) {
			PageData pd = new PageData(  );
			pd.put( "userId" ,LoginUserUtils.getLoginUser().getUserId() );
			PageData serviceProvider =	serprociderService.findById( pd);
			if(null!=serviceProvider){
				//getPCJson(serviceProvider);
				respon.success(serviceProvider);
			}
		}else{
			respon.loginerror("登录失败");
		}
		return respon;
	}
	@GetMapping("findByCreateid")
	@ApiOperation(value="判断申请服务商状态",notes="判断申请服务商状态")
	public Respon  findByCreateid(String serviceId) {
		Respon respon = new Respon();
		try {
			QueryWrapper<ServiceProvider> qw=new QueryWrapper<ServiceProvider>();
			qw.eq("createid", serviceId);
			ServiceProvider s=serprociderService.getOne(qw, false);
			respon.success(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error(e);
		}
		return respon;
	}
	/*private String pcJson(PageData pd){
		String pcJson = pd.getString("businesscope");
		Map<String,List<String>> mapJson = new LinkedHashMap<String,List<String>>();
		
		if(StringUtils.isBlank(pcJson)){
			pcJson=StringUtil.replace(pcJson, " ", "");
			String[] arr$=StringUtil.split(pcJson, '@');
			
			for(int i=0;i<arr$.length;i++){
				
			}
			
			
		}
		
	}*/

	@PostMapping("getmyservice")
	@ApiOperation(value="查询我的服务商信息",notes="查询我的服务商信息")
	public Respon getmyservice() {
		Respon respon = new Respon();
		SysUser user = LoginUserUtils.getLoginUser();
		if (null != user) {

			if (user.getUserType().equals( UserType.SERVICE_PROVIDER .code())) {
				PageData pd = new PageData(  );
				pd.put( "userId" ,LoginUserUtils.getLoginUser().getUserId() );
				PageData serviceProvider = serprociderService.findById( pd);
				return  respon.success( serviceProvider );
			}
		}
		return respon.error(  );
	}
	@PostMapping("findallservice")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
			@ApiImplicitParam(name = "size", value = "页数" ,dataType = "String")
	})
	@ApiOperation(value="查询服务商列表",notes="查询服务商列表")
	public Respon findallservice(MyPage page) {
		Respon respon = new Respon();
		PageData pd = this.getPageData();
		page.setPd( pd );
		try {
			List<PageData> list = serprociderService.findByPage(page);
			respon.success(list,page  );
		}catch (Exception e){
			e.printStackTrace();
			respon.error( "系统异常" );
		}
		return respon;
	}
	
	/*private void getPCJson(PageData pd) {
		String bus=pd.getString("businesscope");
		List<String> retvalue = new ArrayList<String>();
		if(!StringUtils.isBlank(bus)) {
			String fmt="{value:%s}";
			String businesscope = StringUtils.replace(pd.getString("businesscope"), " ", "");
			String[] arr = StringUtils.split(businesscope, "@");
			
			for(int i=0;i<arr.length;i++) {
			   String[] value = StringUtils.split(arr[i],",");
			   retvalue.add(String.format(fmt, new ArrayList<String>(Arrays.asList(value))));
			}
		}
		//JSONObject jsonObject = JSONObject.fromObject(retvalue);

		//pd.add("businesscope", jsonObject.toString());
		
	}*/
}
