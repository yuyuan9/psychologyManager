package com.ht.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jied
 */
@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
//        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        filterChainDefinitionMap.put("/ht-shiro/loginuser", "logout");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/statics/**", "anon");
        filterChainDefinitionMap.put("/mindmap/**", "anon");//思维导图
        filterChainDefinitionMap.put("/help/**", "anon");//帮助中心
        filterChainDefinitionMap.put("/error/**", "anon");//错误跳转页面
        filterChainDefinitionMap.put("/webjars/**", "anon");//swagger 静态资源开发
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        //filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put( "/swagger-resources/**","anon" );
        filterChainDefinitionMap.put( "/v2/api-docs","anon" );
        filterChainDefinitionMap.put( "/ht-biz/sysregionset/findall**" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/banner/**" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/govindex/**" ,"anon");//政管通页面

        //跳转产品中心
        filterChainDefinitionMap.put( "/currency/**" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/findfirsttype" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/findchild" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/toproseroder" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/treelist" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/getlistprobyshop" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/topageserpro" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/findtypepro" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/topagespecial" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/product/getprotybyid" ,"anon");

        //跳转页面
        filterChainDefinitionMap.put( "/app/**" ,"anon");
        filterChainDefinitionMap.put( "/ht-biz/app/login/**" ,"anon");
       
        //filterChainDefinitionMap.put("/ht-biz/app/index/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/index/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/highcompany/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/policydig/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/policylib/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/projectlib/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/policymatch/list**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/policymatch/detail**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/policymatch/index**", "anon");
        filterChainDefinitionMap.put("/ht-biz/app/policymatch/match**", "anon");
       //地区选择
        filterChainDefinitionMap.put("/ht-biz/sysregionset/firstregion**", "anon");
        filterChainDefinitionMap.put("/ht-biz/sysregionset/chileregion**", "anon");
        //filterChainDefinitionMap.put("/citytree/**", "anon");
        //filterChainDefinitionMap.put("/ajaxLogin", "anon");
        //filterChainDefinitionMap.put("/login/**", "anon");
       
        //filterChainDefinitionMap.put("/ht-biz/**", "anon"); 
        //filterChainDefinitionMap.put("/sys/ht-biz/**", "anon"); 
        filterChainDefinitionMap.put("/ht-biz/phone/**", "anon");//手机端接口放开
        filterChainDefinitionMap.put("/ht-biz/xcx/**","anon");  //微信小程序
        filterChainDefinitionMap.put("/ht-biz/policydig/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/resouce/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/library/upload", "authc");//上传文件页面跳转
        filterChainDefinitionMap.put("/ht-biz/service/index/service_input" ,"authc");//入驻服务商
        filterChainDefinitionMap.put("/ht-biz/library/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/evaluate/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/service/index/**", "anon"); 
        filterChainDefinitionMap.put("/ht-biz/login/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/sendcode/**", "anon");//验证码
        filterChainDefinitionMap.put("/ht-biz/search/**", "anon");//全站搜索
        filterChainDefinitionMap.put("/ht-shiro/sendcode/**", "anon");//手机验证码
        filterChainDefinitionMap.put("/order/weixinNotifyUrl/**", "anon");
        filterChainDefinitionMap.put("/ht-shiro/loginuser/**", "anon");
        filterChainDefinitionMap.put("/ht-biz/policylib/winxinlist**", "anon");//政策库微信小程序
        filterChainDefinitionMap.put("/ht-biz/projectlib/winxinlist**", "anon");//立项库微信小程序
        filterChainDefinitionMap.put("/ht-biz/inquire/index", "anon");//企政查搜索主页面
        filterChainDefinitionMap.put("/ht-biz/inquire/physical/**", "anon");//企业体检
        filterChainDefinitionMap.put("/ht-biz/unit/**", "anon");//主管单位获取
        filterChainDefinitionMap.put("/ht-biz/catalog/**", "anon");//技术领域等其他领域获取
        filterChainDefinitionMap.put("/ht-shiro/sysuser/retrievepassword**", "anon");//获取用户信息
        filterChainDefinitionMap.put("/ht-biz/hisself/hisself_index**", "anon");//免费评估介绍页
        filterChainDefinitionMap.put("/ht-shiro/sysuser/getUser**", "anon");//获取用户信息
        filterChainDefinitionMap.put("/ht-biz/foreign/**", "anon");//对外接口放开
        filterChainDefinitionMap.put("/ht-biz/policymatch/**", "anon");//政策匹配
        filterChainDefinitionMap.put("/ht-shiro/loginuser/maunauth**", "anon");//后台拦截跳转
        filterChainDefinitionMap.put("/ht-biz/honeymanager/getHoneyByCode**", "anon");//获取某一部分honey值
        //后台权限放开
        filterChainDefinitionMap.put("/sys/ht-biz/syscitytree/**", "anon");

        filterChainDefinitionMap.put("/", "anon");

        //心理咨询放开
        filterChainDefinitionMap.put("/psychology/**", "anon");


        filterChainDefinitionMap.put("/**", "authc");
        
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/ht-shiro/loginuser/unauth");
        
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */ 
  /*  @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }
*/
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
      /*  myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());*/
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        //超时时间 12小时
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new MySessionListener());
        mySessionManager.setSessionListeners(listeners);
        mySessionManager.setGlobalSessionTimeout(43200*1000);
        mySessionManager.setSessionDAO(redisSessionDAO());

        mySessionManager.setSessionIdCookieEnabled( true );
        mySessionManager.setSessionIdCookie(sessionIdCookie());
        return mySessionManager;
    }
    
    @Bean
    public SimpleCookie sessionIdCookie() {
    	SimpleCookie simplecookie= new SimpleCookie();
    	simplecookie.setName("jeesite.session.id");
    	simplecookie.setPath("/");
    	return simplecookie;
    }

    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     * @return
     */
    @ConfigurationProperties(prefix = "spring.redis")
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setExpire(43200);//12小时
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
//      Custom your redis key prefix for session management, if you doesn't define this parameter,
//      shiro-redis will use 'shiro_redis_session:' as default prefix
//      redisSessionDAO.setKeyPrefix("");
        return redisSessionDAO;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注册全局异常处理
     * @return
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new MyExceptionHandler();
    }
}
