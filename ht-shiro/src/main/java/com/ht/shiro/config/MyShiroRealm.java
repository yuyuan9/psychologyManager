package com.ht.shiro.config;



import com.ht.entity.shiro.SysUser;
import com.ht.shiro.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.entity.shiro.SysPermission;
import com.ht.entity.shiro.SysRole;
import com.ht.shiro.service.SysPermissionService;
import com.ht.shiro.service.SysRoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @jied
 * 自定义权限匹配和账号密码匹配
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysPermissionService sysPermissionService;


    @Resource
    private SysUserService sysUserService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        try {
            List<SysRole> roles = null;//sysRoleService.selectRoleByUser(userInfo);
            for (SysRole role : roles) {
                authorizationInfo.addRole(role.getRole());
            }
            List<SysPermission> sysPermissions = null;//sysPermissionService.selectPermByUser(userInfo);
            for (SysPermission perm : sysPermissions) {
               // authorizationInfo.addStringPermission(perm.getPermission());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.

        String username = (String) token.getPrincipal();
//       System.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
       /* QueryWrapper<UserInfo> ew = new QueryWrapper<UserInfo>();*/
        QueryWrapper<SysUser> ew = new QueryWrapper<SysUser>();
        ew.eq("PHONE",username);
        System.out.println(ew.getSqlSegment());
        SysUser sysUser =  sysUserService.getOne( ew );
     /*   UserInfo userInfo = (UserInfo)userInfoService.getOne(ew);*/
//        System.out.println("----->>userInfo="+userInfo);
        if(sysUser == null){
            return  null;
        }
       if (sysUser.getActive() == false) { //账户冻结
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo  authenticationInfo = new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), getName());
      /*  SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );*/
        return authenticationInfo;
    }
    

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}


}
