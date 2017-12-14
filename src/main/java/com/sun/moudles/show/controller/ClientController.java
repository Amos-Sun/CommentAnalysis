package com.sun.moudles.show.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SunGuiyong on 2017/10/10.
 */
@Controller
@RequestMapping(value = "/test")
public class ClientController {

    @RequestMapping(value = "ok")
    @ResponseBody
    public String ok() {
        return "this connect is ok";
    }
}
