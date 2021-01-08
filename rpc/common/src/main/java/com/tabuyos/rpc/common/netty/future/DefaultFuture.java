package com.tabuyos.rpc.common.netty.future;


import com.tabuyos.rpc.common.entity.Response;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.common.netty.future</i>
 *     <b>class: </b><i>DefaultFuture</i>
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
public class DefaultFuture {
  private Response response;
  private volatile boolean isSucceed = false;
  private final Object object = new Object();

  public Response getResponse(int timeout) {
    synchronized (object) {
      while (!isSucceed) {
        try {
          object.wait(timeout);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      return response;
    }
  }

  public void setResponse(Response response) {
    if (isSucceed) {
      return;
    }
    synchronized (object) {
      this.response = response;
      this.isSucceed = true;
      object.notify();
    }
  }
}
