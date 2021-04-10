package com.tabuyos.rpc.common.netty.server;

import com.tabuyos.rpc.common.entity.Request;
import com.tabuyos.rpc.common.entity.Response;
import com.tabuyos.rpc.common.netty.codec.NettyDecoder;
import com.tabuyos.rpc.common.netty.codec.NettyEncoder;
import com.tabuyos.rpc.common.netty.serializer.JsonSerializer;
import com.tabuyos.rpc.common.service.RegistryService;
import com.tabuyos.rpc.common.service.impl.RegistryServiceImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.netty.server</i>
 *     <b>class: </b><i>NettyServer</i>
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
 * @since 0.1.0 - 12/14/20 3:19 PM
 */
@Component
public class NettyServer implements InitializingBean {

  @Value("${rpc.server.address}")
  private String serverAddress;
  private EventLoopGroup boss = null;
  private EventLoopGroup worker = null;
  private final NettyServerHandler nettyServerHandler;
  private final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

  public NettyServer(NettyServerHandler nettyServerHandler) {
    this.nettyServerHandler = nettyServerHandler;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    RegistryService registry = new RegistryServiceImpl("127.0.0.1:2181");
    log.info("开始调用 NettyServer#afterPropertiesSet();");
    start(registry);
  }

  public void start(RegistryService registry) throws Exception {
    boss = new NioEventLoopGroup();
    worker = new NioEventLoopGroup();
    registry.registry("rpc", serverAddress);
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(boss, worker)
      .channel(NioServerSocketChannel.class)
      .option(ChannelOption.SO_BACKLOG, 1024)
      .childHandler(childHandler());
    bind(serverBootstrap, 8888);
  }

  private ChannelInitializer<SocketChannel> childHandler() {
    return new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new NettyEncoder(Response.class, new JsonSerializer()));
        pipeline.addLast(new NettyDecoder(Request.class, new JsonSerializer()));
        pipeline.addLast(nettyServerHandler);
      }
    };
  }

  public void bind(final ServerBootstrap bootstrap, int port) {
    bootstrap.bind(port).addListener(future -> {
      if (future.isSuccess()) {
        log.info("端口绑定成功: " + port);
      } else {
        log.info("端口绑定失败: " + port);
        bind(bootstrap, port + 1);
      }
    });
  }

  @PreDestroy
  public void destroy() throws InterruptedException {
    boss.shutdownGracefully().sync();
    worker.shutdownGracefully().sync();
    log.info("关闭Netty");
  }
}
