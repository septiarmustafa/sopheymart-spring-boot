package com.enigma.sopimart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello(){
        return "<h1> Hello World </h1>";
    }
    @GetMapping(value = "/hello/v1")
    public String[] getHoobies(){
        return new String[]{"makan", "tidur"};
    }
    @GetMapping("/search{key}")
    public String getRequestParam(@RequestParam String key){
        return  key;
    }
    @GetMapping("/data/{id}")
    public String getDataById(@PathVariable String id){
        return "Data " + id;
    }
    @GetMapping("/users/{id}/profile/{imageId}")
    public String getDataById(@PathVariable String id, @PathVariable String imageId){
        return  "<img src=\" "+ imageId +".jpg\"/>";
    }
}
