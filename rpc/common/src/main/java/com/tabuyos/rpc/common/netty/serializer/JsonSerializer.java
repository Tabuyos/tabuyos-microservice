package com.tabuyos.rpc.common.netty.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class JsonSerializer implements Serializer {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final Logger log = LoggerFactory.getLogger(JsonSerializer.class);

  @Override
  public byte[] serializer(Object object) throws IOException {
    log.info("开始序列化！");
    return objectMapper.writeValueAsBytes(object);
  }

  @Override
  public <T> T deserializer(Class<T> clazz, byte[] bytes) throws IOException {
    log.info("开始反序列化！");
    return objectMapper.readValue(bytes, clazz);
  }
}
