package com.ht.shiro.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.*;
import com.ht.entity.shiro.SysPermission;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.SysUserRole;
import com.ht.entity.shiro.constants.UserType;
import com.ht.entity.shiro.usertype.CompanyUser;
import com.ht.shiro.service.SysPermissionService;
import com.ht.shiro.service.SysRoleService;
import com.ht.shiro.service.SysUserRoleService;
import com.ht.shiro.service.SysUserService;
import com.ht.shiro.service.usertype.CompanyUserService;
import com.ht.utils.LoginUserUtils;
import com.ht.vo.shiro.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author jied
 */
@RestController
@RequestMapping("/ht-shiro/sysuser")
@Api(value="SysUserController",description = "后台用户管理")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private CompanyUserService companyUserService;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

    /**
     * 查询用户列表
     * @return
     */
    @ApiOperation(value="查询所有用户列表", notes="根据账号名称查询，可以不填")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "truename", value = "账号名称", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "username", value = "账号", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "usertype", value = "用户类型:{ADMIN=系统管理员,REGION_COMPANY=區域公司,EXPERT=专家,SERVICE_PROVIDER=服务商,	COMPANY_USER=企业用戶,DEFAULT_USER=普通用户}", required = false, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "companyid", value = "区域公司id", required = false, dataType = "String"),
    })
    @GetMapping("/list")
    public Respon list()throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		PageData pd= getPageData();
    		MyPage mypage=getMyPage(pd);
    		Page<UserVO> list=(Page<UserVO>)sysUserService.findList(mypage);
    	/*	for(UserVO vo : list.getRecords()) {
    			System.out.println(vo.getActive());
    		}*/
    		respon.success(list);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }

    @ApiOperation(value="查询用户明细", notes="根据用户id查询用户详情")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "String"),
    })
    @GetMapping("/detail/{id}")
    public Respon detail(@PathVariable String id)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		SysUser sysUser=sysUserService.getById(id);
    		respon.success(sysUser);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }

    /**
     * 禁用用户
     * @param userid
     * @return
     * @throws Exception
     */
    @ApiOperation(value="禁用用户", notes="根据用户id禁用用户")
    @ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "String")
    @GetMapping("/cancelUser/{userid}")
    public Respon cancelUser(@PathVariable String userid)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		System.out.println(userid);
    		UpdateWrapper wrapper = new UpdateWrapper();
    		wrapper.set("active", false);
    		wrapper.eq("USER_ID", userid);
    		sysUserService.update(wrapper);
    		//sysUserService.cancelUser(userid);
    		respon.success(null);
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }

    @ApiOperation(value="启用用户", notes="根据用户id启用用户")
    @ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "String")
    @GetMapping("/okUser/{userid}")
    public Respon okUser(@PathVariable String userid)throws Exception {
    	Respon respon = this.getRespon();
    	try {
    		UpdateWrapper wrapper = new UpdateWrapper();
    		wrapper.set("active", true);
    		wrapper.eq("USER_ID", userid);
    		sysUserService.update(wrapper);
    		respon.success(null);
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }

    @ApiOperation(value="删除用户", notes="根据用户id删除用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "String"),
    })
    @GetMapping("/deleteUser/{userid}")
    public Respon deleteUser(@PathVariable String userid)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		int check=sysUserService.deleteUser(userid);
    		respon.success(check);
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }

    @ApiOperation(value="批量删除用户", notes="根据用户数组批量删除用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "userids", value = "用户id(根据,获取;分开id)", required = true, dataType = "String[]"),
    })
    @GetMapping("/deleteUsers/{userids}")
    @ResponseBody
    public Respon deleteUsers(@PathVariable String userids) {
    	Respon respon = this.getRespon();
    	try {
    		String[] arrids = StringUtil.getStringArray(userids);
    		for(String uid : arrids) {
    			SysUser user = sysUserService.getById( uid );
    			HashMap<String ,Object> map= new HashMap<String, Object>();
				map.put( "createid",uid );
    			if(UserType.SERVICE_PROVIDER.name().equals(  user.getUserType())){
					((IService)SpringContextUtil.getBean("serviceProvider")).removeByMap( map );
					((IService)SpringContextUtil.getBean("bserviceProvider")).removeByMap( map );
				}
				if(UserType.COMPANY_USER.name().equals( user.getUserType() )){
					companyUserService.removeByMap(map);
				}
    			sysUserService.deleteUser(uid);
    		}
    		respon.success(null);
    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }

    @ApiOperation(value="修改普通用户信息", notes="修改普通用户信息，密码账号除外不可修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "sysuser对象", value = "用户id(根据,获取;分开id)", required = false, dataType = "String[]"),
    })
    @PostMapping(value="save")
    public Respon save(@RequestBody SysUser sysuser)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		SysUser sysusertemp=sysUserService.getById(sysuser.getUserId());
    		if(sysusertemp==null) {
    			respon.error("只修改，不新增");
    		}else {
    			boolean check=sysUserService.update(sysuser);
    			respon.success(check);
    		}
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;

    }

    @ApiOperation(value="修改用户", notes="修改用户")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "uservo对象", value = "uservo对象", required = false, dataType = "object"),
    })
    @PostMapping(value="editUser")
    public Respon editUser(@RequestBody SysUser uservo)throws Exception{
    	Respon respon = this.getRespon();
			SysUser obj = sysUserService.findByPhone(uservo.getPhone());
			if(null!=obj){
				if( uservo.getPhone().equals( sysUserService.getById( uservo.getUserId() ).getPhone())){//手机号是同一个
					sysUserService.saveOrUpdate(uservo);
					respon.success("修改成功");
				}else{
					respon.error("该账号已存在");
				}
			}else{
				if(sysUserService.saveOrUpdate(uservo)){//手机号没注册
					respon.success("修改成功");
				}else{
					respon.error("该账号已存在");
				}
			}
			LoginUserUtils.serUser();
    	return respon;
    }

    @ApiOperation(value="添加用户", notes="添加用户")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "uservo对象", value = "uservo对象", required = false, dataType = "object"),
    })
    @PostMapping(value="createUser")
    public Respon createUser(@RequestBody SysUser sysUser)throws Exception{
		Respon respon = this.getRespon();
		if(StringUtils.isBlank(  sysUser.getPassword())){
			sysUser.setPassword( "666666" );
		}
		sysUser.setUsername( sysUser.getPhone() );
		sysUser.setPassword(SimpleHashUtil.convertEncryptionPwd(sysUser.getPhone(), sysUser.getPassword()));
		sysUser.setActive(true);
		sysUser.setCreatedate(new Date());
    	try {
    	    Object obj = sysUserService.findByPhone(sysUser.getPhone());
    		if(obj==null) {
	    		sysUserService.save(sysUser);
	    		SysUserRole sur= new SysUserRole();
	    		sur.setUserid(sysUser.getUserId());
	    		sur.setRoleid(Integer.valueOf(sysUser.getRoleid()));
	    		sysUserRoleService.save(sur);
	    		respon.success(null);
    		}else {
    			respon.error("该用户已经存在");
    		}
    	}catch(Exception e){
    		respon.error(e);
    	}

    	return respon;
    }



    /**
     * 用户角色关联
     * @param roleid
     * @return
     * @throws Exception
     */
    @ApiOperation(value="给用户关联角色", notes="根据用户关联多个角色")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "roleid", value = "角色id可以传多个id用,或;分隔", required = true, dataType = "String[]"),
    })
    @GetMapping("/saveUserRole/{userid}/{roleid}")
    @ResponseBody
    public Respon saveUserRole(@PathVariable String userid,@PathVariable String roleid)throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		//String userid=LoginUserUtils.getUserId();
    		List<SysUserRole> list =sysUserRoleService.findByUid(userid);
    		List<String> arrRoleid=new ArrayList<String>();
    		String[] roleids = StringUtil.getStringArray(roleid);

    		for(SysUserRole sysUserRole : list) {
    			for(String rid:roleids) {
    				if(!StringUtils.equals(rid, String.valueOf(sysUserRole.getRoleid()))) {
    					arrRoleid.add(rid);
    				}
    			}
    		}

    		SysUserRole userRole=null;
    		List<SysUserRole> surlist=new ArrayList<SysUserRole>();
    		for(String rid : arrRoleid) {
    			userRole=new SysUserRole();
    			userRole.setRoleid(Integer.valueOf(rid));
    			userRole.setUserid(userid);
    			surlist.add(userRole);
    		}

    		if(!surlist.isEmpty() && surlist.size()>0){
    			sysUserRoleService.saveOrUpdateBatch(surlist);
    		}

    	}catch(Exception e) {
    		respon.error(e);
    	}

    	return respon;
    }

    @ApiOperation(value="获取用户信息", notes="获取用户信息")
    @GetMapping("/getUser")
    @ResponseBody
    public Respon getUser()throws Exception{
    	Respon respon = this.getRespon();
    	try {
			if(null!=LoginUserUtils.getLoginUser()){
				String value = redisTemplate.opsForValue().get( Const.redisusertype+LoginUserUtils.getLoginUser().getUserId() );
				if(StringUtils.isNotBlank( value )){
					LoginUserUtils.serUser();
				}
				respon.success(LoginUserUtils.getLoginUser());
			}else{
				respon.error( "未登录" );
			}

    	}catch(Exception e) {
			e.printStackTrace();
    		respon.error(e);
    	}
    	return respon;
    }

    /**
     * 根据个人获取菜单权限
     * @return
     * @throws Exception
     */
    @ApiOperation(value="获取用户菜单", notes="更加登录用户获取用户菜单")
    @GetMapping("/getMenus")
    @ResponseBody
    public Respon getMenus()throws Exception{
		HashMap map = new HashMap(  );
    	Respon respon = this.getRespon();
    	try {
			SysUser user = LoginUserUtils.getLoginUser();
    		if(null!= user) {
    			if(UserType.ADMIN.name().equals( user.getUserType() )||UserType.REGION_COMPANY.name().equals( user.getUserType() )){
					String  roleid =  user.getRoleid() ;
					if(StringUtils.isNotBlank( roleid )){
						List<SysPermission> list= sysPermissionService.findPermsByRoleId(Integer.parseInt( roleid));
						List<SysPermission> buttonper = sysPermissionService.findbuttonPermsByRoleId( Integer.parseInt( roleid) );
						ArrayList<String> strArray = new ArrayList<String> ();
						if(buttonper.size()>0){
							for(SysPermission but:buttonper){
								strArray.add(  but.getPath());
							}
						}
						if(list.size()>0){
							List<TreeEntity> treelist = new ArrayList<TreeEntity>(sysPermissionService.convertTreeMenu(list));
							TreeBuilder treeBuilder =new TreeBuilder(treelist);
							map.put( "menu", treeBuilder.buildTree());
							map.put( "button", strArray);
							respon.success(map);
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
    		/*	List<SysPermission> list=sysPermissionService.findPermsMenuByUserId(userid);
        		List<TreeEntity> treelist = new ArrayList<TreeEntity>(sysPermissionService.convertTreeMenu(list));
        		TreeBuilder treeBuilder =new TreeBuilder(treelist);
        		respon.success(treeBuilder.buildTree());*/
    		}else {
				SecurityUtils.getSubject().logout();
    			return respon.loginerror("未登录");
    		}

    	}catch(Exception e) {
    		e.printStackTrace();
    		respon.error(e);
    	}

    	return respon;
    }

    /**
     * 获取用户所有资源
     * 以书的形式展示
     * @return
     * @throws Exception
     */
    @ApiOperation(value="获取用户所有资源", notes="根据登录用户获取用户所有资源")
    @GetMapping("/getResources")
    @ResponseBody
    public Respon getResources()throws Exception{
    	Respon respon = this.getRespon();
    	
    	try {
    		
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	
    	return respon;
    }
    
    /**
     * 获取用户权限资源
     * 以列表形式展示
     * @return
     * @throws Exception
     */
    @ApiOperation(value="获取用户权限资源", notes="根据登录用户获取用户权限资源")
    @GetMapping("/getJuriResources")
    @ResponseBody
    public Respon getJuriResources()throws Exception{
    	Respon respon = this.getRespon();
    	
    	try {
    		
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	
    	return respon;
    }
    //修改密码
	@ApiOperation(value="修改密码", notes="修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "password", value = "原始密码",  dataType = "String"),
			@ApiImplicitParam(name = "newpassword", value = "新密码" ,dataType = "String"),
			@ApiImplicitParam(name = "rnewpassword", value = "再次输入密码", dataType = "String"),
	})
	@PostMapping("/updatepassword")
	@ResponseBody
	public Respon updatepassword(@RequestBody PageData pd)throws Exception{
		Respon respon = new Respon();
		try {
			SysUser sysuser = LoginUserUtils.getLoginUser();
			if(null!=sysuser){
				String passwd = SimpleHashUtil.convertEncryptionPwd( sysuser.getPhone(), pd.getString( "password" ) );
				if(!sysuser.getPassword().equals( passwd )){
					return respon.error( "用户的原始密码不对" );
				}
				if(StringUtils.isNoneBlank( pd.getString( "newpassword" ) )&&StringUtils.isNoneBlank( pd.getString( "rnewpassword" ) )){
					if(pd.getString(  "newpassword").equals(  pd.getString( "rnewpassword" ))){
						sysuser.setPassword( SimpleHashUtil.convertEncryptionPwd( sysuser.getPhone(), pd.getString( "newpassword" ) ));
						sysUserService.saveOrUpdate(sysuser);
						respon.success("修改成功");
					}else{
						respon.error("两次密码不一致");
					}
				}else{
					respon.error( "请输入密码" );
				}
			}else{
				respon.loginerror("用户已退出");
			}

		}catch(Exception e) {
			e.printStackTrace();
			respon.error("系统异常");
		}

		return respon;
	}

    /*
     * 普通用户
     */
    @ApiOperation(value="获取普通用户列表", notes="查询普通用户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pd", value = "Map集合", required = false, dataType = "PageData"),
    })
    @GetMapping("/getDefaultUserList")
    @ResponseBody
    public Respon getDefaultUserList()throws Exception{
    	Respon respon = this.getRespon();
    	try {
    		PageData pd = this.getPageData();
    		pd.add("userType$eq", UserType.DEFAULT_USER.name());
    		MyPage page = this.getMyPage(pd);
    		Page<SysUser> list=(Page<SysUser>)sysUserService.findListpage(page);
    		respon.success(list);
    	}catch(Exception e) {
    		respon.error(e);
    	}
    	return respon;
    }

	@ApiOperation(value="重置密码", notes="重置密码")
	@GetMapping("/Resetpassword")
	@ResponseBody
	public Respon Resetpassword(@ApiParam(name = "phone", value = "电话号码")String phone,@ApiParam(name = "password", value = "重置密码")String password)throws Exception{
		Respon respon = this.getRespon();
		try {
			SysUser user = sysUserService.findByPhone( phone);
			if(null!=user){
				user.setPassword(SimpleHashUtil.convertEncryptionPwd( user.getPhone(),password ) );
				if(sysUserService.saveOrUpdate(user)) {
					return  respon.success( "重置密码成功" );
				}else {
					respon.error("修改失败");
				}
		
			}else {
				respon.error("该用户不存在");
			}
		}catch(Exception e) {
			respon.error(e);
		}
		return respon.error( "失败" );
	}

    @RequestMapping(value = "myinfo", method = RequestMethod.POST)
    @ApiOperation(value="我的基本信息",notes="我的基本信息")
    public Respon delectcolletion(){
        Respon  respon = new Respon(  );
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
            if(UserType.COMPANY_USER.name().equals( user.getUserType() )){
                PageData pd = new PageData(  );
                pd.put( "userId",user.getUserId() );
                PageData    userinfo =  companyUserService.findById(pd);
                respon.success( userinfo );
            }else if(!UserType.SERVICE_PROVIDER.name().equals( user.getUserType() )||!UserType.COMPANY_USER.name().equals( user.getUserType())){
                 SysUser   userinfo =  sysUserService.getById( user.getUserId() );
                respon.success( userinfo );
            }else{
                respon.error( "参数有误" );
            }
        }else{
            respon.loginerror( "请登录" );
        }
        return respon;
    }
	@RequestMapping(value = "updatauser", method = RequestMethod.POST)
	@ApiOperation(value="修改用户信",notes="修改用户信息")
	public Respon upodatauser(SysUser syuser) throws Exception{
		Respon respon = this.getRespon();
		SysUser obj = sysUserService.findByPhone(syuser.getPhone());
		if(null!=obj){
			if( syuser.getPhone().equals( sysUserService.getById( syuser.getUserId() ).getPhone())){//手机号是同一个
				sysUserService.saveOrUpdate(syuser);
				respon.success(null);
			}else{
				respon.error("该账号已存在");
			}
		}else{
		/*	uservo.setPassword(SimpleHashUtil.convertEncryptionPwd(uservo.getPhone(), uservo.getPassword()));
			uservo.setUsername(  uservo.syuser());*/
			sysUserService.saveOrUpdate(syuser);
		}
		return respon;
	}
	//找回密码
	@RequestMapping(value = "retrievepassword", method = RequestMethod.POST)
	@ApiOperation(value="找回密码",notes="找回密码")
	public Respon retrievepassword(@ApiParam(name = "phone", value = "电话号码")String  phone,
								   @ApiParam(name = "code", value = "验证码")String code,@ApiParam(name = "password", value ="密码")String password) throws Exception{
		Respon respon = this.getRespon();
		if(StringUtils.isBlank(phone)){
			return respon.error("手机号不能为空");
		}
		if(StringUtils.isBlank( password )){
			return respon.error("密码不能为空");
		}
		if(StringUtils.isBlank(code)){
			return respon.error("验证码不能为空");
		}
		try {
			SysUser obj = sysUserService.findByPhone(phone);
			if(null!=obj){
				obj.setPassword( SimpleHashUtil.convertEncryptionPwd(phone,password ));
				sysUserService.saveOrUpdate( obj );
				respon.success("修改成功"  );
			}else{
				respon.error( "改用户暂未注册" );
			}
		}catch (Exception e){
			respon.error( e );
		}

		return respon;
	}
}
