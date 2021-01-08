package com.tabuyos.rpc.consumer.service.impl;

import com.tabuyos.rpc.consumer.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>Description: </p>
 * <pre>
 *     <b>project: </b><i>IntelliJ IDEA</i>
 *     <b>package: </b><i>com.tabuyos.rpc.provider.service.impl</i>
 *     <b>class: </b><i>UserServiceImpl</i>
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
 * @since 0.1.0 - 12/14/20 3:11 PM
 */
@Service
public class UserServiceImpl implements UserService {
  @Override
  public String findUser(String id) {
    return "findUser-client";
  }

  @Override
  public String findAll() {
    return "findAll-client";
  }
}
