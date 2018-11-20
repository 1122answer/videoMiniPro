package com.lcf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping("")
    public String Hello() {
        return "天猫精灵,hello 我在~";
    }

}
