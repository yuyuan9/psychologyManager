package com.ht.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.biz.service.BserprociderService;
import com.ht.biz.service.SerprociderService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.CopyUtils;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.entity.shiro.usertype.BserviceProvider;
import com.ht.entity.shiro.usertype.BserviceProvider.btype;
import com.ht.entity.shiro.usertype.ServiceProvider;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author jied
 *
 */
@RestController
@RequestMapping(value="/sys/ht-biz/bservice")
@Api(value="IndexController",description = "后台中间表服务商")
public class SysbserviceProciderController extends BaseController{




	@Autowired
	private BserprociderService bserprociderService;
	@Autowired
	private SerprociderService serprociderService;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@PostMapping("sysfindpagelist")
	@ApiOperation(value="查询待审核列表",notes="查询待审核列表")
	public Respon findpagelist(@RequestBody PageData pd)  {
		MyPage page = this.getMyPage( pd );
		Respon respon = new Respon();
		try {
			List<PageData> list = bserprociderService.sysfindByPage(page);
			respon.success( list,page);
		}catch (Exception e){
			respon.error( e );
		}
		return respon;
	}

	@GetMapping("findbyId")
	@ApiOperation(value="通过id查询详细信息",notes="通过id查询详细信息")
	public Respon findbyId(@ApiParam(name = "id", value = "id") String bid) {
		Respon respon = new Respon();
		try {
		
			BserviceProvider provider = bserprociderService.getById(bid);
			respon.success( provider );
		}catch (Exception e){
			respon.error(e);
		}
		return respon;
	}

	@PostMapping("revieService")
	@ApiOperation(value="审核服务商",notes="审核服务商")
	public Respon revieService(@ApiParam(name = "ServiceProvider对象", value = "传入json格式", required = true) @RequestBody BserviceProvider bserviceProvider) throws IllegalAccessException {
			Respon respon = new Respon();
			try {
				if(BserviceProvider.btype.Already.getStat()==bserviceProvider.getStats()){ //通过
					redisTemplate.opsForValue().set( Const.redisusertype+LoginUserUtils.getLoginUser().getUserId() ,"true");
							QueryWrapper que = new QueryWrapper(  );
							que.eq( "createid" ,bserviceProvider.getCreateid());
							ServiceProvider service = new ServiceProvider();
							if(null!=serprociderService.getOne( que )){
								service = serprociderService.getOne( que );
								CopyUtils.copyProperties(bserviceProvider,service);
							}else{
								CopyUtils.copyProperties(bserviceProvider, service );
							}
							serprociderService.saveOrUpdate(  service);
							bserprociderService.removeById(  bserviceProvider.getBid());
							SysUser user = (SysUser) ((IService)SpringContextUtil.getBean("sysUserService")).getById(  bserviceProvider.getCreateid());
							user.setUserType( UserType.SERVICE_PROVIDER.name());
							((IService)SpringContextUtil.getBean("sysUserService")).saveOrUpdate(user);
							//加入redis
							redisTemplate.opsForValue().set( Const.redisusertype+LoginUserUtils.getLoginUser().getUserId() ,"true");
							MsgUtil.addMsg( WorkReminder.Work.server_check_success.getName(),"/ht-biz/service/index/shop_edit",
							bserviceProvider.getCreateid(), LoginUserUtils.getLoginUser().getUserId(), LoginUserUtils.getLoginUser().getCompanyid());
				}
				if(BserviceProvider.btype.refuse.getStat()==bserviceProvider.getStats()){ //拒绝
					QueryWrapper que = new QueryWrapper(  );
					que.eq( "createid" ,bserviceProvider.getCreateid());
					ServiceProvider service = serprociderService.getOne( que );
					if(null!=service){
						service.setStats( btype.refuse.getStat());
						serprociderService.saveOrUpdate(service);
					}
					bserprociderService.saveOrUpdate(bserviceProvider  );
					MsgUtil.addMsg( WorkReminder.Work.server_check_fail.getName(),"", bserviceProvider.getCreateid(),
							LoginUserUtils.getLoginUser().getUserId(), bserviceProvider.getReason());
				}
				respon.success("提交成功");
			}catch (Exception e){
				e.printStackTrace();
				respon.error( e );
			}

		return respon;
	}



}
