package com.tabuyos.rpc.common.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.entity</i>
 *     <b>class: </b><i>Request</i>
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
 * @since 0.1.0 - 12/14/20 2:43 PM
 */
public class Request implements Serializable {

  private static final long serialVersionUID = 6481413501932696241L;
  /**
   * 请求唯一标识号
   */
  private String id;
  /**
   * 类名
   */
  private String clazz;
  /**
   * 函数名称
   */
  private String method;
  /**
   * 参数类型
   */
  private Class<?>[] parameterTypes;
  /**
   * 参数列表
   */
  private Object[] parameters;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getClazz() {
    return clazz;
  }

  public void setClazz(String clazz) {
    this.clazz = clazz;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Class<?>[] getParameterTypes() {
    return parameterTypes;
  }

  public void setParameterTypes(Class<?>[] parameterTypes) {
    this.parameterTypes = parameterTypes;
  }

  public Object[] getParameters() {
    return parameters;
  }

  public void setParameters(Object[] parameters) {
    this.parameters = parameters;
  }

  @Override
  public String toString() {
    return "Request{" +
      "id='" + id + '\'' +
      ", className='" + clazz + '\'' +
      ", methodName='" + method + '\'' +
      ", parameterTypes=" + Arrays.toString(parameterTypes) +
      ", parameters=" + Arrays.toString(parameters) +
      '}';
  }
}
