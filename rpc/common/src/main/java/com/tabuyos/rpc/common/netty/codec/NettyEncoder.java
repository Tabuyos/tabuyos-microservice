package com.tabuyos.rpc.common.netty.codec;

import com.tabuyos.rpc.common.netty.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.netty.codec</i>
 *     <b>class: </b><i>NettyEncoder</i>
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
public class NettyEncoder extends MessageToByteEncoder<Object> {

    private final Class<?> clazz;
    private final Serializer serializer;

    public NettyEncoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (clazz != null && clazz.isInstance(in)) {
            byte[] bytes = this.serializer.serializer(in);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
