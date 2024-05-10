package com.green.greengram.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**") //웹 접근 경로
                .addResourceLocations("file:" + uploadPath); // 서버 내 실제 경로

        //pic도안걸리고 컨트롤러에서도 안걸린 모든 요청
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestResource = location.createRelative(resourcePath);

                        if (requestResource.exists() && requestResource.isReadable()) {
                            return requestResource;
                        }
                        return new ClassPathResource("/static/index.html");
                    }
                });
        //이 작업들을 거치면 http://localhost:8080/feed 여기서 새로고침을 해도
        //http://localhost:8080 로 된거처럼 됨
    }
}