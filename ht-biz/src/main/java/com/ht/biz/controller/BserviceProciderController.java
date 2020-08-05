package com.ht.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.BserprociderService;

import com.ht.biz.service.SerprociderService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.CopyUtils;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.shiro.SysUser;

import com.ht.entity.shiro.usertype.BserviceProvider;
import com.ht.entity.shiro.usertype.ServiceProvider;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
/**
 * 
 * @author jied
 *
 */
@RestController
@RequestMapping(value="/ht-biz/bservice")
@Api(value="IndexController",description = "前台中间表服务商")
public class BserviceProciderController extends BaseController{




	@Autowired
	private BserprociderService bserprociderService;
	@Autowired
	private SerprociderService serprociderService;



	@PostMapping("savaService")
	@ApiOperation(value="提价审批信息",notes="提价审批信息")
	public Respon savaService(@ApiParam(name = "ServiceProvider对象", value = "传入json格式", required = true) BserviceProvider bserviceProvider) throws IllegalAccessException {

		Respon respon = new Respon();
		if(null!=LoginUserUtils.getLoginUser()){
			try {
				QueryWrapper que = new QueryWrapper();
				que.eq( "createid" ,LoginUserUtils.getLoginUser().getUserId());
				BserviceProvider service = bserprociderService.getOne(que  );
				ServiceProvider ser = serprociderService.getOne(que);
				if(null!=ser){ 
					ser.setStats( BserviceProvider.btype.To_be_audited.getStat());
					if(!ser.getIsagency()){
						ser.setAgencyimg("");
					}
					serprociderService.saveOrUpdate(ser);
				}
				if(null!=service){
					CopyUtils.copyProperties(bserviceProvider,service);
					bserprociderService.saveOrUpdate(service  );
					MsgUtil.addMsg(WorkReminder.Work.server_register.getName(),null,  LoginUserUtils.getLoginUser().getUserId(),LoginUserUtils.getLoginUser().getUserId(), null);
					respon.success("提交成功");
				}else{//提交审核
					bserviceProvider.setCreatedate(new Date(  ));
					bserviceProvider.setCreateid(  LoginUserUtils.getLoginUser().getUserId());
					bserviceProvider.setStats( BserviceProvider.btype.To_be_audited.getStat());//设置为待审核状态
					if(!bserviceProvider.getIsagency()){
						bserviceProvider.setAgencyimg("");
					}
					bserprociderService.saveOrUpdate(bserviceProvider  );
					MsgUtil.addMsg(WorkReminder.Work.server_register.getName(),null,  LoginUserUtils.getLoginUser().getUserId(),LoginUserUtils.getLoginUser().getUserId(), null);
					respon.success("提交成功");
				}
				
			}catch (Exception e){
				e.printStackTrace();
				respon.error( e );
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
				return respon.success(BserviceProvider.btype.Already.getStat()); //已是服务商
			}else{
				QueryWrapper que = new QueryWrapper();
				que.eq( "createid" ,LoginUserUtils.getLoginUser().getUserId());
				BserviceProvider service = bserprociderService.getOne(que  );
				if(null!=service){
					if(BserviceProvider.btype.To_be_audited.getStat()==service.getStats() ){ //待审核
						respon.success( BserviceProvider.btype.To_be_audited.getStat() );
					}
					if(BserviceProvider.btype.refuse.getStat()==service.getStats() ){ //拒绝
						respon.success( BserviceProvider.btype.refuse.getStat(),service);
					}
				}
			}
		}else{
			respon.loginerror("登录失败");
		}
		return respon;
	}
	

}
