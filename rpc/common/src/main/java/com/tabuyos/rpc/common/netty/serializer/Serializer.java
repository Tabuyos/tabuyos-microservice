package com.tabuyos.rpc.common.netty.serializer;

import java.io.IOException;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.netty.codec</i>
 *     <b>class: </b><i>NettyDecoder</i>
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
 * @since 0.1.0 - 12/14/20 3:21 PM
 */
public interface Serializer {

    /**
     * 将对象进行序列化
     * @param object 待序列化的对象
     * @return 二进制流
     * @throws IOException 抛出IO异常
     */
    byte[] serializer(Object object) throws IOException;

    /**
     * 反序列化从而得到对象
     * @param clazz 反序列化后的对象类型
     * @param bytes 待反序列化的二进制流
     * @return 反序列化后的对象
     * @throws IOException 抛出IO异常
     */
    <T> T deserializer(Class<T> clazz, byte[] bytes) throws IOException;
}
