package com.tabuyos.rpc.common.service.impl;

import com.tabuyos.rpc.common.config.Constant;
import com.tabuyos.rpc.common.service.RegistryService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.service.impl</i>
 *     <b>class: </b><i>RegistryServiceImpl</i>
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
 * @since 0.1.0 - 12/14/20 3:13 PM
 */
public class RegistryServiceImpl implements RegistryService {

    private final CuratorFramework curatorFramework;
    private final Logger log = LoggerFactory.getLogger(RegistryServiceImpl.class);
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public RegistryServiceImpl(String address) {
        this.curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(Constant.ZK_SESSION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
    }

    @Override
    public void registry(String key, String value) throws Exception {
        CuratorFrameworkState state = curatorFramework.getState();
        if (!state.equals(CuratorFrameworkState.STARTED)) {
            log.info("zookeeper 客户端启动");
            curatorFramework.start();
        }
        String data = key + "=" + value;
        String path = Constant.ZK_CHILDREN_PATH;
        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(path, data.getBytes());
    }
}
