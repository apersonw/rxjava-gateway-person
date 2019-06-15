package org.rxjava.gateway.person;

import org.rxjava.api.user.inner.InnerUserApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author happy 2019-03-17 22:10
 */
@SpringBootApplication
public class RxGatewayPersonApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxGatewayPersonApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
//                .route(r -> r.path("/user/**").filters(p -> p.stripPrefix(1)).uri("http://service-user"))
                .route(r -> r.path("/user/**").filters(p -> p.stripPrefix(1)).uri("http://localhost:8081"))
                .route(r -> r.path("/goods/**").filters(p -> p.stripPrefix(1)).uri("http://service-goods"))
                .route(r -> r.path("/order/**").filters(p -> p.stripPrefix(1)).uri("http://service-order"))
                .route(r -> r.path("/pay/**").filters(p -> p.stripPrefix(1)).uri("http://service-pay"))
                .build();
    }

    @Bean
    @Qualifier("userClientAdapter")
    public ClientAdapter userClientAdapter(
            @Qualifier("webFluxConversionService")
                    ConversionService conversionService,
            WebClient.Builder webClientBuilder
    ) {
        return ReactiveHttpClientAdapter.build(
                conversionService, webClientBuilder, "localhost:8081"
        );
    }

    @Bean
    public InnerUserApi serveUserApi(@Qualifier("userClientAdapter") ClientAdapter clientAdapter) {
        InnerUserApi innerUserApi = new InnerUserApi();
        innerUserApi.setclientAdapter(clientAdapter);
        return innerUserApi;
    }

    @Bean
    public TokenFilter tokenFilter(InnerUserApi innerUserApi) {
        return new TokenFilter(innerUserApi);
    }
}