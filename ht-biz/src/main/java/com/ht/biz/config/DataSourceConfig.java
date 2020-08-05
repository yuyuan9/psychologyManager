package com.ht.biz.config;

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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author jied
 */
@Configuration
@MapperScan(basePackages = {"com.ht.biz.mapper","com.ht.biz.mapper.govpolicyapp"},sqlSessionTemplateRef = "SqlSessionTemplate")
public class DataSourceConfig {

	  @ConfigurationProperties(prefix = "spring.datasource")
	    @Bean(name = "datasource")
	    @Primary
	    public DataSource datasource() throws SQLException {
	        return DruidDataSourceBuilder.create().build();
	    }

	    @Bean(name = "sessionFactory")
	    @Primary
	    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "datasource") DataSource dataSource,
	                                                PaginationInterceptor paginationInterceptor,
	                                                @Qualifier(value = "globalConfiguration") GlobalConfig globalConfiguration) throws Exception {
	        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
	        bean.setDataSource(dataSource);
	        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:bizMapper/**/*.xml"));
	        Interceptor[] interceptors = new Interceptor[]{paginationInterceptor};
	        bean.setPlugins(interceptors);
	        bean.setGlobalConfig(globalConfiguration);
	        return bean.getObject();
	    }

	    @ConfigurationProperties(prefix = "mybatis-plus.global-config")
	    @Bean(name = "globalConfiguration")
	    public GlobalConfig globalConfiguration() {
	        return new GlobalConfig();
	    }


	    @Bean(name = "transactionManager")
	    @Primary
	    public DataSourceTransactionManager dataSourceTransactionManager1(@Qualifier("datasource") DataSource dataSource) {
	        return new DataSourceTransactionManager(dataSource);
	    }

	    @Bean(name = "SqlSessionTemplate")
	    @Primary
	    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
	        return new SqlSessionTemplate(sqlSessionFactory);
	    }

}
