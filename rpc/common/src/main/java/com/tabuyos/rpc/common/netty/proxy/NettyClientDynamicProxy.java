package com.tabuyos.rpc.common.netty.proxy;

import com.tabuyos.rpc.common.entity.Request;
import com.tabuyos.rpc.common.entity.Response;
import com.tabuyos.rpc.common.netty.client.NettyClient;
import com.tabuyos.rpc.common.service.DiscoverService;
import com.tabuyos.rpc.common.service.impl.DiscoverServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.netty.proxy</i>
 *     <b>class: </b><i>ClientDynamicProxy</i>
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
public class NettyClientDynamicProxy<T> implements InvocationHandler {
    private final Logger log = LoggerFactory.getLogger(NettyClientDynamicProxy.class);
    private final Class<T> clazz;

    private final DiscoverService discover = new DiscoverServiceImpl("127.0.0.1:2181");

    public NettyClientDynamicProxy(Class<T> clazz) throws Exception {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        String requestId = UUID.randomUUID().toString();

        String className = method.getDeclaringClass().getName();
        log.info(className);
        String methodName = method.getName();

        Class<?>[] parameterTypes = method.getParameterTypes();

        request.setId(requestId);
        request.setClazz(className.replaceAll("consumer", "provider"));
        request.setMethod(methodName);
        request.setParameterTypes(parameterTypes);
        request.setParameters(args);
        log.info("请求内容: {}", request);

        String address = discover.discover("rpc");
        String[] arrays = address.split(":");
        String host = arrays[0];
        int port = Integer.parseInt(arrays[1]);
        NettyClient nettyClient = new NettyClient(host, port);
        log.info("开始连接服务端：{}", new Date());
        nettyClient.connect();
        Response send = nettyClient.send(request);
        log.info("请求调用返回结果：{}", send.getData());
        return send.getData();
    }
}
