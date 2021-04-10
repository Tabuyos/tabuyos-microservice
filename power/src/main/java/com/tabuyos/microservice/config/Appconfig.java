package com.tabuyos.microservice.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Tabuyos
 * @Time 3/16/20 1:34 AM
 * @Site www.tabuyos.com
 * @Email tabuyos@outlook.com
 * @Description
 */
@Configuration
public class Appconfig {

  @Bean
  public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
    TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
    tomcatServletWebServerFactory.setPort(6000);
    return tomcatServletWebServerFactory;
  }
}
