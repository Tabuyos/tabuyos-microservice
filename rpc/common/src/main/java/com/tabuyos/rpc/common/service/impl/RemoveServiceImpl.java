package com.tabuyos.rpc.common.service.impl;

import com.tabuyos.rpc.common.config.Constant;
import com.tabuyos.rpc.common.service.RemoveService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.service.impl</i>
 *     <b>class: </b><i>RemoveServiceImpl</i>
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
 * @since 0.1.0 - 12/15/20 3:06 PM
 */
public class RemoveServiceImpl implements RemoveService {

    private final CuratorFramework curatorFramework;
    private final Logger log = LoggerFactory.getLogger(RegistryServiceImpl.class);
    private volatile Map<String, String> concurrentHashMap = new ConcurrentHashMap<>(100);

    public RemoveServiceImpl(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public RemoveServiceImpl(String address) throws Exception {
        this.curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(Constant.ZK_SESSION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        watchChildNode(curatorFramework);
    }

    private void watchChildNode(final CuratorFramework client) throws Exception {
        if (!client.getState().equals(CuratorFrameworkState.STARTED)) {
            log.info("zookeeper 客户端启动");
            client.start();
        }
        List<String> nodeList = client.getChildren().usingWatcher((Watcher) watchedEvent -> {
            if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                try {
                    watchChildNode(client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).forPath(Constant.ZK_REGISTRY_PATH);
        log.info("nodeLists: {}", nodeList);
        for (String node : nodeList) {
            String path = Constant.ZK_REGISTRY_PATH + "/" + node;
            byte[] data = client.getData().forPath(path);
            concurrentHashMap.put(new String(data), path);
        }
    }

    @Override
    public void remove(String key) throws Exception {
        System.out.println(this.concurrentHashMap);
        if (!curatorFramework.getState().equals(CuratorFrameworkState.STARTED)) {
            log.info("zookeeper 客户端启动");
            curatorFramework.start();
        }
        Stat stat = new Stat();
        for (String mapKey : concurrentHashMap.keySet()) {
            log.info(mapKey);
            String mapValue = concurrentHashMap.get(mapKey);
            String[] split = mapKey.split("=");
            if (split.length == 2 && key.equals(split[0])) {
                log.info("移除 {} 节点, path: {}", split[1], mapValue);
                curatorFramework
                        .getData()
                        .storingStatIn(stat)
                        .forPath(mapValue);
                curatorFramework
                        .delete()
                        .deletingChildrenIfNeeded()
                        .withVersion(stat.getVersion())
                        .forPath(mapValue);
            }
        }
    }
}
