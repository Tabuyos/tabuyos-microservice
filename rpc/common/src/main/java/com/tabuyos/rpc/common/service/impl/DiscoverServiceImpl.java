package com.tabuyos.rpc.common.service.impl;

import com.tabuyos.rpc.common.config.Constant;
import com.tabuyos.rpc.common.service.DiscoverService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.service.impl</i>
 *     <b>class: </b><i>DiscoverServiceImpl</i>
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
 * @since 0.1.0 - 12/14/20 4:39 PM
 */
public class DiscoverServiceImpl implements DiscoverService {

  private final CuratorFramework curatorFramework;
  private final Logger log = LoggerFactory.getLogger(DiscoverServiceImpl.class);
  private volatile List<String> dataList = new ArrayList<>();

  public DiscoverServiceImpl(String address) throws Exception {
    this.curatorFramework = CuratorFrameworkFactory
      .builder()
      .connectString(address)
      .connectionTimeoutMs(Constant.ZK_CONNECTION_TIMEOUT)
      .sessionTimeoutMs(Constant.ZK_SESSION_TIMEOUT)
      .retryPolicy(new ExponentialBackoffRetry(1000, 3))
      .build();
    watchChildNode(curatorFramework);
  }

  public DiscoverServiceImpl(CuratorFramework curatorFramework) {
    this.curatorFramework = curatorFramework;
  }

  private void watchChildNode(final CuratorFramework client) throws Exception {
    CuratorFrameworkState state = client.getState();
    if (!state.equals(CuratorFrameworkState.STARTED)) {
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
    List<String> dataList = new ArrayList<>();
    log.info("{}", nodeList);
    for (String node : nodeList) {
      byte[] data = client.getData().forPath(Constant.ZK_REGISTRY_PATH + "/" + node);
      dataList.add(new String(data));
    }
    this.dataList = dataList;
  }

  @Override
  public String discover(String key) {
    log.info("{}", dataList);
    String data = null;
    List<String> address = getAddress(dataList, key);
    int size = address.size();
    log.info("发现与 {} 相关的节点: {}", key, address);
    if (size > 0) {
      if (size == 1) {
        data = address.get(0);
      } else {
        data = address.get(ThreadLocalRandom.current().nextInt(size));
      }
    }
    return data;
  }

  private List<String> getAddress(List<String> list, String prefix) {
    List<String> rpcList = new ArrayList<>();
    for (String el : list) {
      String[] split = el.split("=");
      if (split.length == 2 && prefix.equals(split[0])) {
        rpcList.add(split[1]);
      }
    }
    return rpcList;
  }
}
