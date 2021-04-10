package com.tabuyos.rpc.provider.application;

import com.tabuyos.rpc.common.netty.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.application</i>
 *     <b>class: </b><i>ProviderApplication</i>
 *     comment here.
 * </pre>
 *
 * @author <pre><b>username: </b><i>Tabuyos</i></pre>
 * <pre><b>site: </b><i>http://www.tabuyos.com</i></pre>
 * <pre><b>email: </b><i>tabuyos@outlook.com</i></pre>
 * <pre><b>description: </b><i>
 *     <pre>
 *         Talk is cheap, show me the code.
 *     </pre>
 * </i></pre>
 * @version 0.1.0
 * @since 0.1.0 - 12/14/20 2:42 PM
 */
@SpringBootApplication(scanBasePackages = {"com.tabuyos.rpc.provider", "com.tabuyos.rpc.common"})
public class ProviderApplication {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(ProviderApplication.class, args);
    NettyServer bean = context.getBean(NettyServer.class);
    System.out.println(bean.hashCode());
  }
}
