package com.ht.main;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Administrator on 2018/1/4.
 */
@EnableTransactionManagement
@EnableSwagger2
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,SolrAutoConfiguration.class},
                       scanBasePackages= {"com.ht.main","com.ht.biz","com.ht.shiro","com.ht.commons"})
public class Application extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        
        
    }

}
