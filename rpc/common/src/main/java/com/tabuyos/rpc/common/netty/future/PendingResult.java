package com.tabuyos.rpc.common.netty.future;


import com.tabuyos.rpc.common.entity.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.netty.future</i>
 *     <b>class: </b><i>PendingResult</i>
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
public class PendingResult {
    private final Map<String, ResultFuture> map = new ConcurrentHashMap<>();

    public void add(String id, ResultFuture future) {
        this.map.put(id, future);
    }

    public void set(String id, Response response) {
        ResultFuture resultFuture = this.map.get(id);
        if (resultFuture != null) {
            resultFuture.setSuccess(response);
            this.map.remove(id);
        }
    }
}
