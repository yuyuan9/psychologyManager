package com.solr.config;

import java.sql.SQLException;

import javax.sql.DataSource;

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

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

@Configuration
@MapperScan(basePackages  = "com.solr.mapper", sqlSessionTemplateRef  = "solrSqlSessionTemplate")
public class SolrDataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "solrDataSource")
    public DataSource solrDataSource() throws SQLException {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sessionFactorysolr")
    public SqlSessionFactory sessionFactorysolr(@Qualifier(value = "solrDataSource") DataSource dataSource,
                                                 PaginationInterceptor paginationInterceptor,
                                                 @Qualifier(value="globalConfigurationsolr")GlobalConfig globalConfig) throws Exception{
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:solrMapper/*/*.xml"));
        Interceptor[] interceptors = new Interceptor[]{paginationInterceptor};
        bean.setPlugins(interceptors);
        bean.setGlobalConfig(globalConfig);
        return bean.getObject();
    }

    @ConfigurationProperties(prefix = "mybatis-plus.global-config")
    @Bean(name="globalConfigurationsolr")
    public GlobalConfig globalConfigurationsolr() {
        return new GlobalConfig();
    }

    @Bean(name = "transactionManagersolr")
    public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("solrDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "solrSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sessionFactorysolr") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
