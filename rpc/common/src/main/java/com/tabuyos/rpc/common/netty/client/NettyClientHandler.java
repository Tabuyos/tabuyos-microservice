package com.tabuyos.rpc.common.netty.client;

import com.tabuyos.rpc.common.entity.Request;
import com.tabuyos.rpc.common.entity.Response;
import com.tabuyos.rpc.common.netty.future.DefaultFuture;
import com.tabuyos.rpc.common.netty.server.NettyServerHandler;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.netty.client</i>
 *     <b>class: </b><i>NettyClientHandler</i>
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
public class NettyClientHandler extends ChannelDuplexHandler {

  /**
   * 使用Map维护请求对象ID与响应结果Future的映射关系
   */
  private final Map<String, DefaultFuture> futureMap = new ConcurrentHashMap<>();
  private final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
    if (message instanceof Response) {
//            获取响应对象
      Response response = (Response) message;
      DefaultFuture defaultFuture = futureMap.get(response.getId());
      defaultFuture.setResponse(response);
    }
    super.channelRead(ctx, message);
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object message, ChannelPromise promise) throws Exception {
    if (message instanceof Request) {
      Request request = (Request) message;
//            发送请求对象之前，先把请求ID保存下来，并构建一个与响应Future的映射关系
      futureMap.putIfAbsent(request.getId(), new DefaultFuture());
    }
    super.write(ctx, message, promise);
  }

  /**
   * 获取响应结果
   *
   * @param requestId 请求唯一标识
   * @return response
   */
  public Response getResponse(String requestId) {
    try {
      log.info(requestId);
      log.info(futureMap.toString());
      DefaultFuture future = futureMap.get(requestId);
      return future.getResponse(10);
    } finally {
//            获取成功以后，从map中移除
      futureMap.remove(requestId);
    }
  }
}
