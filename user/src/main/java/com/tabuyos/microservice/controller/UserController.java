package com.tabuyos.microservice.controller;

import com.tabuyos.microservice.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Tabuyos
 * @Time 3/16/20 1:00 AM
 * @Site www.tabuyos.com
 * @Email tabuyos@outlook.com
 * @Description
 */
@RestController
public class UserController {

  final RestTemplate restTemplate;

  public UserController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/getUser")
  public R getUser() {
    Map<String, Object> map = new HashMap<>(20);
    map.put("key", "user");
    return R.success("Successful", map);
  }

  @GetMapping("/getPower")
  public R getPower() {
    return R.success("Successful", restTemplate.getForObject("http://localhost:6000/getPower", HashMap.class));
  }

  @GetMapping("/getPower1")
  public R getPower1() {
    // hipnote 使用Nginx做负载均衡
    return R.success("Successful", restTemplate.getForObject("http://localhost:80/getPower", HashMap.class));
  }
}
