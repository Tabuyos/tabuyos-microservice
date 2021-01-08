package com.tabuyos.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Tabuyos
 * @Time 3/16/20 1:34 AM
 * @Site www.tabuyos.com
 * @Email tabuyos@outlook.com
 * @Description
 */
@RestController
public class PowerController {
  @GetMapping("/getPower")
  public Object getPower() {
    Map<String, Object> map = new HashMap<>(20);
    map.put("key", "power");
    return map;
  }
}
