package com.github.akwei.demo1;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {

    private final String baseUrl = "";

//    public SwaggerUiWebMvcConfigurer(String baseUrl) {
//        this.baseUrl = baseUrl;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
        registry.
                addResourceHandler(baseUrl + "/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/documentation/v2/api-docs",
                "/v2/api-docs?group=restful-api");
        registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/documentation/swagger-resources/configuration/security",
                "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/documentation/swagger-resources",
                "/swagger-resources");

        registry.addViewController(baseUrl + "/swagger-ui/")
                .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
    }
}
