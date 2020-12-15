package com.tabuyos.rpc.common.netty.future;

import com.tabuyos.rpc.common.entity.Response;
import io.netty.util.concurrent.DefaultPromise;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.netty.future</i>
 *     <b>class: </b><i>ResultFuture</i>
 *     Future有几种模式：
 *     1. JDK自带的Future: {@link java.util.concurrent.Future}
 *     2. 自定义Future: 比如{@link DefaultFuture}
 *     3. 利用Netty的DefaultPromise : {@link DefaultPromise}
 *     4. Java 8 的 {@link java.util.concurrent.CompletableFuture}
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
public class ResultFuture extends DefaultPromise<Response> {
}
