package com.tabuyos.rpc.common.netty.server;

import com.tabuyos.rpc.common.entity.Request;
import com.tabuyos.rpc.common.entity.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.netty.server</i>
 *     <b>class: </b><i>NettyServerHandler</i>
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
 * @since 0.1.0 - 12/14/20 3:20 PM
 */
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<Request> implements ApplicationContextAware {

  private ApplicationContext applicationContext;
  private final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Request request) {
    Response response = new Response();
    response.setId(request.getId());
    try {
      log.info(request.toString());
      Object handler = handler(request);
//            log.info("返回结果是: " + handler);
      response.setData(handler);
    } catch (Exception e) {
      response.setMessage(e.toString());
      e.printStackTrace();
    }
    log.info("响应内容: {}", response);
    ctx.writeAndFlush(response);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  private Object handler(Request request) throws ClassNotFoundException, InvocationTargetException {
    Class<?> clazz = Class.forName(request.getClazz());
    log.info("clazz: " + clazz);
    Object bean = applicationContext.getBean(clazz);
    log.info("bean: " + bean);
    Class<?> beanClass = bean.getClass();
    log.info("beanClass: " + beanClass);
    String method = request.getMethod();
    Class<?>[] parameterTypes = request.getParameterTypes();
    Object[] parameters = request.getParameters();
    FastClass fastClass = FastClass.create(beanClass);
    System.out.println(Arrays.toString(parameterTypes));
    log.info("method: " + method);
    FastMethod fastMethod = fastClass.getMethod(method, parameterTypes);
    log.info("开始调用cglib动态代理执行服务端方法......");
    return fastMethod.invoke(bean, parameters);
  }
}
