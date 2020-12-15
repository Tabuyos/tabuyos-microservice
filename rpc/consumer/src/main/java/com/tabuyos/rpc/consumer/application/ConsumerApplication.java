package com.tabuyos.rpc.consumer.application;

import com.tabuyos.rpc.common.netty.proxy.ProxyFactory;
import com.tabuyos.rpc.consumer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.consumer.application</i>
 *     <b>class: </b><i>ConsumerApplication</i>
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
@SpringBootApplication(scanBasePackages = {"com.tabuyos.rpc.consumer"})
public class ConsumerApplication {

    private final static Logger log = LoggerFactory.getLogger(ConsumerApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConsumerApplication.class, args);
        UserService helloService = ProxyFactory.create(UserService.class);
        log.info("响应结果: {}", helloService.findUser("tabuyos-for-id"));
    }
}
