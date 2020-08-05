package com.ht.main.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${swagger.enable}")
    private boolean swagger_is_enable;
    @Bean
    public Docket createRestApi(){
        return new Docket( DocumentationType.SWAGGER_2 )
                .enable( swagger_is_enable )
                .apiInfo( apiInfo() )
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.ht" ) )
                .paths( PathSelectors.any( ) )
                .build();
    } 

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("高企云")
                //描述
                .description("接口文档")
                //创建人
                //.contact(new Contact("LinXiuNan", "", ""))
                //版本号
                .version("1.0")
                .build();
    }


}
