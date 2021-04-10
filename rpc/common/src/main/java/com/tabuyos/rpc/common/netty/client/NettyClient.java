package com.tabuyos.rpc.common.netty.client;

import com.tabuyos.rpc.common.entity.Request;
import com.tabuyos.rpc.common.entity.Response;
import com.tabuyos.rpc.common.netty.codec.NettyDecoder;
import com.tabuyos.rpc.common.netty.codec.NettyEncoder;
import com.tabuyos.rpc.common.netty.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.netty.client</i>
 *     <b>class: </b><i>NettyClient</i>
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
 * @since 0.1.0 - 12/14/20 4:49 PM
 */
public class NettyClient implements InitializingBean {

  private final Logger log = LoggerFactory.getLogger(NettyClient.class);

  private EventLoopGroup eventLoopGroup;
  private Channel channel;
  private NettyClientHandler nettyClientHandler;
  private final String host;
  private final Integer port;
  private static final int MAX_RETRY = 5;

  public NettyClient(String host, Integer port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("开始调用 NettyClient#afterPropertiesSet();");
  }

  public void connect() {
    nettyClientHandler = new NettyClientHandler();
    eventLoopGroup = new NioEventLoopGroup();
//        启动类
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(eventLoopGroup)
//                指定传输使用的Channel
      .channel(NioSocketChannel.class)
      .option(ChannelOption.SO_KEEPALIVE, true)
      .option(ChannelOption.TCP_NODELAY, true)
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
      .handler(handler());
    connect(bootstrap, host, port, MAX_RETRY);
//        ChannelFuture future = bootstrap.connect(host, port).sync();
//        channel = future.channel();
  }

  private ChannelInitializer<SocketChannel> handler() {
    return new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4));
        pipeline.addLast(new NettyEncoder(Request.class, new JsonSerializer()));
        pipeline.addLast(new NettyDecoder(Response.class, new JsonSerializer()));
        pipeline.addLast(nettyClientHandler);
      }
    };
  }

  /**
   * 失败重连机制，参考Netty入门实战掘金小册
   *
   * @param bootstrap bootstrap
   * @param host      host
   * @param port      port
   * @param retry     retry
   */
  private void connect(Bootstrap bootstrap, String host, int port, int retry) {
    ChannelFuture channelFuture = bootstrap.connect(host, port).addListener(future -> {
      if (future.isSuccess()) {
        log.info("连接服务端成功");
      } else if (retry == 0) {
        log.error("重试次数已用完，放弃连接");
      } else {
//                第几次重连：
        int order = (MAX_RETRY - retry) + 1;
//                本次重连的间隔
        int delay = 1 << order;
        log.error("{} : 连接失败，第 {} 重连....", new Date(), order);
        bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
      }
    });
    channel = channelFuture.channel();
  }

  /**
   * 发送消息
   *
   * @param request request
   * @return response
   */
  public Response send(final Request request) {
    try {
      Thread.sleep(5_000);
      channel.writeAndFlush(request).await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return nettyClientHandler.getResponse(request.getId());
  }

  @PreDestroy
  public void close() {
    eventLoopGroup.shutdownGracefully();
    channel.closeFuture().syncUninterruptibly();
  }
}
