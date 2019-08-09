package com.atguigu.springboot.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    /*将其配置加入到配置文件spring.datasource中*/
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    /*
    *
    * 本来配置filter和Servlet需要用到web.xml但是springboot就直接生成
    * ServletRegistrationBean   FilterRegistrationBean 即可，将其放到容器中
    *
    *
    * */


    // 配置Druid的监控
    // 1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();

        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");// 默认就是允许所有访问
        initParams.put("deny", "192.168.1.127");//拒绝访问 这个地方虽然和localhost一样的ip地址，但是不能访问

        bean.setInitParameters(initParams);
        return bean;
    }

    // 2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");//排除这些对静态文件的拦截

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));//拦截的路径

        return bean;
    }

}

