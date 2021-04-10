package com.tabuyos.microservice.gateway.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: </p>
 * <pre>
 *   <b>project: </b><i>tabuyos-microservice</i>
 *   <b>package: </b><i>com.tabuyos.microservice.gateway.application</i>
 *   <b>class: </b><i>GatewayApplication</i>
 *   comment here.
 * </pre>
 *
 * @author <pre><b>username: </b><i><a href="http://www.tabuyos.com">Tabuyos</a></i></pre>
 * <pre><b>site: </b><i><a href="http://www.tabuyos.com">http://www.tabuyos.com</a></i></pre>
 * <pre><b>email: </b><i>tabuyos@outlook.com</i></pre>
 * <pre><b>description: </b><i>
 *   <pre>
 *     Talk is cheap, show me the code.
 *   </pre>
 * </i></pre>
 * @version 0.1.0
 * @since 0.1.0 - 1/11/21 5:32 PM
 */
@SpringBootApplication(scanBasePackages = {"com.tabuyos.microservice.gateway"})
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
      .route(p -> p
        .path("/get")
        .filters(f -> f.addRequestHeader("Hello", "World"))
        .uri("http://httpbin.org"))
      .build();
  }

//  @Bean
//  public RouteLocator routes(RouteLocatorBuilder builder) {
//    return builder.routes()
//      .route("circuitbreaker_route", r -> r
//        .path("/consumingServiceEndpoint")
//        .filters(f -> f
//          .circuitBreaker(c -> c
//            .setName("myCircuitBreaker")
//            .setFallbackUri("forward:/inCaseOfFailureUseThis")
//            .addStatusCode("INTERNAL_SERVER_ERROR"))
//          .rewritePath("/consumingServiceEndpoint", "/backingServiceEndpoint"))
//        .uri("lb://backing-service:8088"))
//      .build();
//  }
}
