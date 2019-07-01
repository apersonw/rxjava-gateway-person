package org.rxjava.gateway.person;

import org.rxjava.api.user.inner.InnerUserApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author happy 2019-06-18 14:55
 * 网关测试上下文
 */
@Configuration
public class GatewayTestContext {
    @Bean
    @Qualifier("userClientAdapter")
    public ClientAdapter userClientAdapter(ConversionService conversionService, WebClient.Builder webClientBuilder) {
        return ReactiveHttpClientAdapter.build(conversionService, webClientBuilder, "localhost:8081");
    }

    @Bean
    public InnerUserApi innerUserApi(@Qualifier("userClientAdapter") ClientAdapter clientAdapter) {
        InnerUserApi api = new InnerUserApi();
        api.setclientAdapter(clientAdapter);
        return api;
    }
}
