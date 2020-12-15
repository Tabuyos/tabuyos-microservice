package com.tabuyos.rpc.test.application;

import com.tabuyos.rpc.common.service.DiscoverService;
import com.tabuyos.rpc.common.service.RegistryService;
import com.tabuyos.rpc.common.service.RemoveService;
import com.tabuyos.rpc.common.service.impl.DiscoverServiceImpl;
import com.tabuyos.rpc.common.service.impl.RegistryServiceImpl;
import com.tabuyos.rpc.common.service.impl.RemoveServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.test.application</i>
 *     <b>class: </b><i>TestApplication</i>
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
 * @since 0.1.0 - 12/15/20 1:21 PM
 */
//@SpringBootApplication
public class TestApplication {

    static DiscoverService discoverService;
    static RegistryService registryService;
    static RemoveService removeService;

    public static void main(String[] args) throws Exception {
        registryService = new RegistryServiceImpl("127.0.0.1:2181");
        registryService.registry("rpc", "tabuyos4");
        registryService.registry("rpc", "tabuyos0");
        registryService.registry("rpc2", "tabuyos2");
        TimeUnit.SECONDS.sleep(10);

        discoverService = new DiscoverServiceImpl("127.0.0.1:2181");
        String discover = discoverService.discover("rpc");

        removeService = new RemoveServiceImpl("127.0.0.1:2181");
        removeService.remove("rpc");

        discoverService = new DiscoverServiceImpl("127.0.0.1:2181");
        String discover1 = discoverService.discover("rpc");
        String discover2 = discoverService.discover("rpc2");
        System.out.println("==================================");
        System.out.println(discover);
        System.out.println("---------");
        System.out.println(discover1);
        System.out.println("+++++++++");
        System.out.println(discover2);
        System.out.println("==================================");
    }
}
