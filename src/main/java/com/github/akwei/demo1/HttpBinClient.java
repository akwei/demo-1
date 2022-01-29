package com.github.akwei.demo1;

import com.github.akwei.demo1.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "HttpBin", url = "https://httpbin.org",configuration = {})
public interface HttpBinClient {

    @GetMapping("/get")
    Map<String, Object> get(@RequestParam(name = "name") String name);

    @PostMapping("/post")
    Map<String, Object> post(@RequestBody User user);
}
