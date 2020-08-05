package com.solr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

/**
 * Created by Administrator on 2018/1/15.
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
    	PaginationInterceptor p=new PaginationInterceptor();
    	//p.setDialectType("MySQL");
        return p;
    }
}
