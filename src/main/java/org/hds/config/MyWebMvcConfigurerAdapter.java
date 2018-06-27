package org.hds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//WebMvcConfigurerAdapter
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{
    /**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/my/**").addResourceLocations("file:E:/test/");
        super.addResourceHandlers(registry);
    }
}