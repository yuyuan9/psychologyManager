package com.ht.shiro.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SimpleHashUtil;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.biz.sys.Share;
import com.ht.entity.shiro.SysPermission;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.entity.shiro.usertype.CompanyUser;
import com.ht.shiro.service.*;
import com.ht.shiro.service.usertype.CompanyUserService;
import com.ht.utils.MsgUtil;
import com.ht.utils.RewardUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.ht.utils.LoginUserUtils;
import com.ht.vo.shiro.UserVO;

import io.swagger.annotations.Api;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value = "/ht-shiro/loginuser")
@Api(value = "ShiroController", description = "用户登录注册退出")
public class ShiroController extends BaseController {
	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private CompanyUserService companyUserService;
	@Autowired
	private SysLogService setLoginUser;
	/**
	 * 注册
	 *
	 * @param
	 * @return
	 */
	@ApiOperation(value="用户注册", notes="根据id查询资源详细")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "sysuserobj", value = "用户对象，根据属性传入", required = true, dataType = "Object"),
			@ApiImplicitParam(paramType="key", name = "key", value = "推荐人id", required = true, dataType = "String"),
    })
	@PostMapping (value = "/register")
	public Respon register( SysUser sysuserobj,String code,String key) {
		Respon respon = this.getRespon();
		try {
			if(StringUtils.isBlank( sysuserobj.getUsername())||StringUtils.isBlank(code)){
				return respon.error("手机号，验证码不能为空");
			}
			boolean check = smsService.findByCode(code,sysuserobj.getUsername());
			if(check){
				sysuserobj.setPhone( sysuserobj.getUsername() );
				SysUser sysUser = sysUserService.findByPhone(sysuserobj.getUsername());
				if (sysUser != null) {
					respon.error("该手机号已存在");
				} else {
					sysUser = sysUserService.registerDefaultUser(sysuserobj);
					sysUserService.save(sysUser);
					//注册奖励
					RewardUtil.disHoney(String.valueOf(RewardRule.Code.reg_user), null, sysUser.getUserId(), sysUser.getUserId(),sysUser.getCompanyid());
					respon.success(sysUser);
					if(StringUtils.isNotBlank( key )){
						//奖励推荐者
						RewardUtil.disHoney(String.valueOf(RewardRule.Code.recommend_reg), null, key, sysUser.getUserId(),sysUser.getCompanyid());
						savaShare(sysUser,key);
					}
				}
			}else{
				respon.error("验证错误");
			}

		} catch (Exception e) {
			respon.error(e);
		}
		return respon;
	}

   public void savaShare(SysUser sysUser,String key){
	   QueryWrapper<RewardRule> queryWrapper = new QueryWrapper<RewardRule>();
	   queryWrapper.eq( "code" ,"recommend_reg");
	   RewardRule rewardRule = (RewardRule) ((IService)SpringContextUtil.getBean("rewardRuleService")).getOne( queryWrapper );
	   Share  share  = new Share();
	   share.setCreatedate( new Date(  ) );
	   share.setCreateid(sysUser.getUserId());
	   share.setFromuserid(key);
	   share.setTouserid( sysUser.getUserId() );
	   share.setPhone(  sysUser.getPhone().substring(0, 3) + "****" + sysUser.getPhone().substring(7, sysUser.getPhone().length()) );
	   share.setHoney(rewardRule.getReturnValue());
	   ((IService)SpringContextUtil.getBean("shareService")).save(share);
   }
	@ApiOperation(value="企业用户注册", notes="企业用户")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "sysuserobj", value = "用户对象，根据属性传入", required = true, dataType = "Object"),
	})
	@PostMapping(value = "/qregister")
	public Respon qregister( SysUser sysuserobj,String code,CompanyUser companyUser,String key) {
		Respon respon = this.getRespon();
		try {
			if(StringUtils.isBlank( sysuserobj.getUsername())||StringUtils.isBlank(code)){
				return respon.error("手机号，验证码不能为空");
			}
			boolean check = smsService.findByCode(code,sysuserobj.getUsername());

			if(check){
				sysuserobj.setPhone( sysuserobj.getUsername() );
				SysUser sysUser = sysUserService.findByPhone(sysuserobj.getUsername());
				if (sysUser != null) {
					respon.error("该手机号已存在");
				} else {
					CompanyUser company =companyUserService.findBycompregcode(companyUser.getCompregcode());
					if(null!=company){
						respon.error("该企业统一信用代码已存在");
					}else{
						sysUser = sysUserService.registerCompanyUser(sysuserobj);
						if(sysUserService.save(sysUser)){
							companyUser.setCreatedate( new Date(  ) );
							companyUser.setCreateid( sysUser.getUserId() );
							companyUserService.save( companyUser );
							respon.success(sysUser);
							//注册奖励
							RewardUtil.disHoney(String.valueOf(RewardRule.Code.reg_company_user), null, sysUser.getUserId(), sysUser.getUserId(),sysUser.getCompanyid());
							MsgUtil.addMsg( WorkReminder.Work.company_register.getName(),"/ht-biz/usercenter/user/info", sysUser.getUserId(),sysUser.getUserId(),null );
							if(StringUtils.isNotBlank( key )){
								//奖励推荐者
								RewardUtil.disHoney(String.valueOf(RewardRule.Code.recommend_reg), null, key, sysUser.getUserId(),sysUser.getCompanyid());
								savaShare(sysUser,key);
							}
						}else{
							respon.error( "注册失败" );
						}

					}

				}
			}else{
				respon.error("验证错误");
			}

		} catch (Exception e) {
			respon.error(e);
		}
		return respon;
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	@ApiOperation(value="根据用户名检测用户", notes="检查用户是否存在")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "username", value = "登录名称", required = true, dataType = "String"),
    })
	@GetMapping(value = "/checkUsername")
	@ResponseBody
	public Respon checkUsername(String username) {
		Respon respon = this.getRespon();

		try {
			SysUser sysUser = sysUserService.findByUserName(username);
			if (sysUser != null) {
				respon.error("该账号已存在");
			} else {
				respon.success(null);
			}
		} catch (Exception e) {
			respon.error(e);
		}

		return respon;
	}

	/**
	 * 退出
	 * 
	 * @return
	 */
	@ApiOperation(value="用户退出", notes="退出系统")
	@GetMapping(value = "/loginOut")
	@ResponseBody
	public Respon loginOut() {
		Respon respon = this.getRespon();
		try {
			SecurityUtils.getSubject().logout();
			respon.success(null);
		}catch(Exception e) {
			respon.error(e);
		}
		return respon;
	}

	/**
	 * 忘记密码
	 * 
	 * @return
	 */
	@PostMapping(value = "forgetPwd")
	@ResponseBody
	public Respon forgetPwd(UserVO uservo) {
		Respon respon = this.getRespon();
		String code="";//查询短信验证
		if (!StringUtils.equals(uservo.getVerificacode(),code)) {
			respon.error("验证码不正确");
			return respon;
		}else {
			try {
				SysUser sysuser = sysUserService.findByPhone(uservo.getPhone());
				if (sysuser != null) {
						//修改密码
					sysuser.setPassword(SimpleHashUtil.convertEncryptionPwd(uservo.getUsername(), uservo.getPassword()));
					sysUserService.saveOrUpdate(sysuser);
					respon.success("修改成功");
				}else {
					respon.error("密码不正确");
				}
			} catch (Exception e) {
				respon.error(e);
			}
		}

		return respon;
	}

	/**
	 * 后台登录
	 *
	 * @return
	 */
	@GetMapping(value = "admin_main_toLogin")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "验证码",  dataType = "String"),
			@ApiImplicitParam(name = "username", value = "用户名" ,dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
	})
	public Respon adminMainToLogin( SysUser sysUser) {
		Respon respon = this.getRespon();
		Subject subject = SecurityUtils.getSubject();
		PageData pd = this.getPageData();
		Session session = subject.getSession();
		String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
		if(null == pd.getString( "code" ) || "".equals(pd.getString( "code" ))){
			respon.setCode("10010");
			respon.setMsg("验证码错误");
			return respon;
		}else {
			if(StringUtils.isNoneBlank( sessionCode ) && sessionCode.equalsIgnoreCase(pd.getString( "code" ))) {
				if (Strings.isNotBlank( sysUser.getPassword() ) && Strings.isNotBlank( sysUser.getUsername() )) {
					String passwd = new SimpleHash( "SHA-1", sysUser.getUsername(), sysUser.getPassword() ).toString();
					UsernamePasswordToken token = new UsernamePasswordToken( sysUser.getUsername(), passwd );
					try {
						subject.login( token );
						return getMenus(LoginUserUtils.getLoginUser(),subject);

					} catch (IncorrectCredentialsException e) {
						return	respon.error( "用户或密码错误" );
					} catch (LockedAccountException e) {
						return	respon.error( "登录失败，该用户已被冻结" );
					} catch (AuthenticationException e) {
						return	respon.error( "登录失败，该用户不存在" );
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return respon.error( "登录失败，该用户不存在" );
				}
			}else{
				return respon.error("验证码错误");
			}
		}
		return respon;
	}
	public Respon getMenus(SysUser user,Subject subject)throws Exception{
		HashMap map = new HashMap(  );
		Respon respon = this.getRespon();
		try {
				if(UserType.ADMIN.name().equals( user.getUserType() )||UserType.REGION_COMPANY.name().equals( user.getUserType() )){
					String  roleid =  user.getRoleid() ;
					if(StringUtils.isNotBlank( roleid )){
						List<SysPermission> list= sysPermissionService.findPermsByRoleId(Integer.parseInt( roleid));
						if(list.size()>0){
							return respon.success( LoginUserUtils.getUserVO( subject.getSession().getId() ) );
						}else{
							SecurityUtils.getSubject().logout();
							return respon.error("没有权限登录");
						}
					}else{
						SecurityUtils.getSubject().logout();
						return respon.error("没有权限登录");
					}
				}else{
					SecurityUtils.getSubject().logout();
					return respon.error("没有权限登录");
				}
		}catch(Exception e) {
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	/**
	 * 登录方法
	 * 
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/ajaxLogin", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "验证码",  dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名" ,dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
    })
	@ResponseBody
	public Respon ajaxLogin(SysUser sysUser) {
		// JSONObject jsonObject = new JSONObject();

		Respon respon = this.getRespon();
		Subject subject = SecurityUtils.getSubject();
		PageData pd = this.getPageData();
		Session session = subject.getSession();
		System.out.println(  session.getId());
		String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
		if(null == pd.getString( "code" ) || "".equals(pd.getString( "code" ))){
			  respon.setCode("10010");
			  respon.setMsg("验证码错误");
			  return respon;
		}else {
			if(StringUtils.isNoneBlank( sessionCode ) && sessionCode.equalsIgnoreCase(pd.getString( "code" ))) {
				if (Strings.isNotBlank( sysUser.getPassword() ) && Strings.isNotBlank( sysUser.getUsername() )) {
					// String passwd = SimpleHashUtil.convertEncryptionPwd(sysUser.getUsername(),
					// sysUser.getPassword());
					String passwd = new SimpleHash( "SHA-1", sysUser.getUsername(), sysUser.getPassword() ).toString();
					/*String passwd="000e4a4d171dcf5c54a15d70244c0a7d5d083919";*/
					UsernamePasswordToken token = new UsernamePasswordToken( sysUser.getUsername(), passwd );
					try {
						subject.login( token );
						if(!RewardUtil.getHoney(LoginUserUtils.getLoginUser().getUserId())){
							RewardUtil.disHoney(String.valueOf(RewardRule.Code.login_day), null, LoginUserUtils.getLoginUser().getUserId(), LoginUserUtils.getLoginUser().getUserId(),null);
						}
						setLoginUser.setLoginUser(LoginUserUtils.getLoginUser().getUserId());
					/*	SecurityUtils.getSubject().getSession().setTimeout(43200000); //12小时*/
						return respon.success( LoginUserUtils.getUserVO( subject.getSession().getId() ) );

						// jsonObject.put("token", subject.getSession().getId());
						// jsonObject.put("msg", "登录成功");
					} catch (IncorrectCredentialsException e) {
						// jsonObject.put("msg", "密码错误");
						return	respon.error( "用户或密码错误" );
					} catch (LockedAccountException e) {
						// jsonObject.put("msg", "登录失败，该用户已被冻结");
						return	respon.error( "登录失败，该用户已被冻结" );
					} catch (AuthenticationException e) {
						return	respon.error( "登录失败，该用户不存在" );
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return respon.error( "登录失败，该用户不存在" );
				}
			}else{
				return respon.error("验证码错误");
			}
		}
		return respon;
	}


	@ApiOperation(value="小程序登录",notes="")
	@RequestMapping(value = "/weixinLogin", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名" ,dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
	})
	@ResponseBody
	public Respon weixinLogin(SysUser sysUser) {
		Respon respon = this.getRespon();
		Subject subject = SecurityUtils.getSubject();
		if (Strings.isNotBlank( sysUser.getPassword() ) && Strings.isNotBlank( sysUser.getUsername() )) {
			String passwd = new SimpleHash( "SHA-1", sysUser.getUsername(), sysUser.getPassword() ).toString();
			UsernamePasswordToken token = new UsernamePasswordToken( sysUser.getUsername(), passwd );
			try {
				subject.login( token );
				if(!RewardUtil.getHoney(LoginUserUtils.getLoginUser().getUserId())){
					RewardUtil.disHoney(String.valueOf(RewardRule.Code.login_day), null, LoginUserUtils.getLoginUser().getUserId(), LoginUserUtils.getLoginUser().getUserId(),null);
				}
				setLoginUser.setLoginUser(LoginUserUtils.getLoginUser().getUserId());
				return respon.success( LoginUserUtils.getUserVO( subject.getSession().getId() ) );
			} catch (IncorrectCredentialsException e) {
				return	respon.error( "用户或密码错误" );
			} catch (LockedAccountException e) {
				return	respon.error( "登录失败，该用户已被冻结" );
			} catch (AuthenticationException e) {
				return	respon.error( "登录失败，该用户不存在" );
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return respon.error( "登录失败，该用户不存在" );
		}

		return respon;
	}
	/**
	 * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
	 * 
	 * @return
	 */
	@GetMapping(value = "/unauth")
	public ModelAndView unauth(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView(  );
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("code", "1000000");
		//map.put("msg", "未登录");
		Respon respon = this.getRespon();
		try {
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String 	url = savedRequest.getRequestUrl();
			if(url.contains( "sys" )){
				modelAndView.addObject( "url", url);
				modelAndView.setViewName( "redirect:/ht-shiro/loginuser/maunauth" );
				return modelAndView;
			}
			else if(url.contains( "app" )){
				modelAndView.addObject( "url", url);
				modelAndView.setViewName( "app/login/login" );
				return modelAndView;
			}
			else{
				modelAndView.addObject( "url", url);
				modelAndView.setViewName( "login/login" );
				return modelAndView;
			}
		}catch(Exception e) {
			respon.error(e);
		}
		return null;
	}

	@GetMapping(value = "maunauth")
	@ResponseBody
	public Respon unauth1(){
		Respon respon = this.getRespon();
		return respon.loginerror(  "未登录");
	}

}
