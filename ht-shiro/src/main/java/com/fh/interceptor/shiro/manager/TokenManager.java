package com.fh.interceptor.shiro.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import com.alibaba.fastjson.JSON;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.commons.utils.StringUtil;
import com.ht.entity.shiro.SysUser;
import com.ht.shiro.config.MyShiroRealm;

/**
 * 
 * <p>
 * 
 * <p>
 * 
 * 区分 责任人 日期 说明<br/>
 * 创建 周柏成 2014年3月3日 <br/>
 * <p>
 * 
 * @author zhou-baicheng
 * 
 * @version 1.0,2014年3月3日
 * 
 *          Shiro管理下的Token工具类
 */
public class TokenManager {
	// 用户登录管理
	public static final MyShiroRealm realm = SpringContextUtil.getBean(MyShiroRealm.class);

	// 用户session管理DefaultWebSecurityManager
	/**
	 * 获取当前登录的用户User对象
	 * 
	 * @return
	 */
	public static SysUser getToken() {
		Subject subject = SecurityUtils.getSubject();
		// SysUser user=(SysUser)subject.getPrincipal();
		Object obj = subject.getPrincipal();
		SysUser sysUser = new SysUser();
		if (obj instanceof SysUser) {
			sysUser = (SysUser) obj;
		} else {
			sysUser = JSON.parseObject(JSON.toJSON(obj).toString(), SysUser.class);
		}
		return sysUser;
	}

	/**
	 * 获取当前用户的Session
	 * 
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 获取当前用户NAME
	 * 
	 * @return
	 */
	public static String getTrueName() {
		SysUser user = null;
		user = getToken();
		if (user != null) {
			return getToken().getTrueName();
		} else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 获取当前用户ID
	 * 
	 * @return
	 */
	public static String getUserId() {
		return getToken() == null ? null : getToken().getUserId();
	}

	/**
	 * 把值放入到当前登录用户的Session里
	 * 
	 * @param key
	 * @param value
	 */
	public static void setVal2Session(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 从当前登录用户的Session里取值
	 * 
	 * @param key
	 * @return
	 */
	public static Object getVal2Session(Object key) {
		return getSession().getAttribute(key);
	}
	// /**
	// * 获取验证码，获取一次后删除
	// * @return
	// */
	// public static String getYZM(){
	// String code = (String) getSession().getAttribute("CODE");
	// getSession().removeAttribute("CODE");
	// return code ;
	// }

	/**
	 * 登录
	 * 
	 * @param user
	 * @param rememberMe
	 * @return
	 */
	public static SysUser login(SysUser sysUser, String code, Boolean rememberMe) {
		//passwordRetryCacheRemove(user.getLoginName());
		// 新添jied 2018.10.24 加代码，清除登录账号
		//clearUserAuthByUserId(sysUser.getUserId());
		realm.clearAllCache();
		String passwd = new SimpleHash( "SHA-1", sysUser.getUsername(), sysUser.getPassword() ).toString();
		UsernamePasswordToken token = new UsernamePasswordToken( sysUser.getUsername(), passwd );
		//UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getPassword(), code);
		//token.setRememberMe(rememberMe);
		SecurityUtils.getSubject().login(token);
		return getToken();
	}

	

	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 退出登录
	 */
	public static void logout() {
		//SecurityUtils.getSubject().getSession().removeAttribute(Const.SESSION_USER);
		SecurityUtils.getSubject().logout();
	}



	/**
	 * 清空当前用户权限信息。 目的：为了在判断权限的时候，再次会再次 <code>doGetAuthorizationInfo(...)  </code>方法。
	 * ps： 当然你可以手动调用 <code> doGetAuthorizationInfo(...)  </code>方法。
	 * 这里只是说明下这个逻辑，当你清空了权限，<code> doGetAuthorizationInfo(...)  </code>就会被再次调用。
	 */
	// public static void clearNowUserAuth(){
	// /**
	// * 这里需要获取到shrio.xml 配置文件中，对Realm的实例化对象。才能调用到 Realm 父类的方法。
	// */
	// /**
	// * 获取当前系统的Realm的实例化对象，方法一（通过 @link
	// org.apache.shiro.web.mgt.DefaultWebSecurityManager
	// 或者它的实现子类的{Collection<Realm> getRealms()}方法获取）。
	// * 获取到的时候是一个集合。Collection<Realm>
	// RealmSecurityManager securityManager =
	// (RealmSecurityManager) SecurityUtils.getSecurityManager();
	// SampleRealm realm =
	// (SampleRealm)securityManager.getRealms().iterator().next();
	// */
	// /**
	// * 方法二、通过ApplicationContext 从Spring容器里获取实列化对象。
	// */
	// realm.clearCachedAuthorizationInfo();
	// /**
	// * 当然还有很多直接或者间接的方法，此处不纠结。
	// */
	// }
	//
	//

	/**
	 * 根据UserIds 清空权限信息。
	 * 
	 * @param id
	 *            用户ID
	 */
	public static void clearUserAuthByUserId(String... userIds) {

		if (null == userIds || userIds.length == 0)
			return;

		List<SimplePrincipalCollection> result = getSimplePrincipalCollectionByUserId(userIds);

		for (SimplePrincipalCollection simplePrincipalCollection : result) {
			realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
		}
	}

	// /**
	// * 方法重载
	// * @param userIds
	// */
	// public static void clearUserAuthByUserId(List<Long> userIds) {
	// if(null == userIds || userIds.size() == 0){
	// return ;
	// }
	// clearUserAuthByUserId(userIds.toArray(new Long[0]));
	// }

	/**
	 * 根据ID查询 SimplePrincipalCollection
	 * 
	 * @param userIds
	 *            用户ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(String... userIds) {
		// 把userIds 转成Set，好判断
		Set<String> idset = (Set<String>) StringUtil.array2Set(userIds);
		// 获取所有session

		Collection<Session> sessions = SpringContextUtil.getBean(org.crazycake.shiro.RedisSessionDAO.class).getActiveSessions();
		// 定义返回
		List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
		for (Session session : sessions) {
			// 获取SimplePrincipalCollection
			Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if (null != obj && obj instanceof SimplePrincipalCollection) {
				// 强转
				SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
				// 判断用户，匹配用户ID。
				obj = spc.getPrimaryPrincipal();
				if (null != obj && obj instanceof SysUser) {
					SysUser user = (SysUser) obj;
					// 比较用户ID，符合即加入集合
					if (null != user && idset.contains(user.getUserId())) {
						list.add(spc);
					}
				}
			}
		}
		return list;
	}

}
