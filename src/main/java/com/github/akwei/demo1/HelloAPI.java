package com.github.akwei.demo1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class HelloAPI {

    @Resource
    private HttpBinClient httpBinClient;

    @RequestMapping("/home")
    public String home() {
        return "Hello  World";
    }

    @RequestMapping("/feign-get")
    public ResponseEntity<Map<String, Object>> feignGet() {
        return ResponseEntity.ok(this.httpBinClient.get("akwei"));
    }


    @RequestMapping("/pub/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin/users")
    public String users() {
        return "users";
    }

    @RequestMapping("/admin/users/login")
    public String login(String user, String pwd) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        UsernamePasswordAuthenticationToken userAuth=new UsernamePasswordAuthenticationToken()
//        securityContext.setAuthentication();
        return "users";
    }


}
