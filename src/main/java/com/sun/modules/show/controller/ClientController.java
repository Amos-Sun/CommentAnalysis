package com.sun.modules.show.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by SunGuiyong on 2017/10/10.
 */
@Controller
public class ClientController {

    @RequestMapping(value = "/index")
    public String login() {
        return "index";
    }

    @RequestMapping(value = "/chart")
    public String pageJump() {
        return "chart";
    }
}
