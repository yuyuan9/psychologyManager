package com.ht.shiro.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author jied
 * 
 */
@Configuration
@MapperScan(basePackages  = "com.ht.shiro.mapper", sqlSessionTemplateRef  = "shiroSqlSessionTemplate")
public class ShiroDataSourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "shiroDataSource")
    public DataSource shiroDataSource() throws SQLException {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sessionFactoryShiro")
    public SqlSessionFactory sessionFactoryShiro(@Qualifier(value = "shiroDataSource") DataSource dataSource,
                                                 PaginationInterceptor paginationInterceptor,
                                                 @Qualifier(value="globalConfigurationShiro")GlobalConfig globalConfig) throws Exception{
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:shiroMapper/*/*.xml"));
        Interceptor[] interceptors = new Interceptor[]{paginationInterceptor};
        bean.setPlugins(interceptors);
        bean.setGlobalConfig(globalConfig);
        return bean.getObject();
    }

    @ConfigurationProperties(prefix = "mybatis-plus.global-config")
    @Bean(name="globalConfigurationShiro")
    public GlobalConfig globalConfigurationShiro() {
        return new GlobalConfig();
    }

    @Bean(name = "transactionManagerShiro")
    public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("shiroDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "shiroSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sessionFactoryShiro") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
