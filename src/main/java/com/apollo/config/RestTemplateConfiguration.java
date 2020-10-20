package com.apollo.config;

import com.apollo.config.restTemplate.CustomHttpRequestInterceptor;
import com.apollo.config.restTemplate.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration implements RestTemplateCustomizer {

    public RestTemplateConfiguration RestTemplateConfiguration() {
        return new RestTemplateConfiguration();
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new CustomHttpRequestInterceptor());
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

}
