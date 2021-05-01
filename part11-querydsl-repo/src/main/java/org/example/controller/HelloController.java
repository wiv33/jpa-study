package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pilseong.ko
 */
@RestController
public class HelloController {


    @GetMapping("/")
    public String hello() {
        return "Hello world!";
    }
}
