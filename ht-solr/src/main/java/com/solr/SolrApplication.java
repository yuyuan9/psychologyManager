package com.solr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import com.solr.rpc.server.RpcServer;



@SpringBootApplication(exclude=SolrAutoConfiguration.class,
						scanBasePackages= {"com.solr","com.ht.commons"})
@MapperScan(basePackages  = "com.solr.mapper")
public class SolrApplication extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SolrApplication.class);
    }
	
	@Bean
	public ServletRegistrationBean<RpcServer> servletRegistrationBean() {
	    return new ServletRegistrationBean<RpcServer>(new RpcServer(), "/jsonrpc");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication app = new SpringApplication(SolrApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}

}
