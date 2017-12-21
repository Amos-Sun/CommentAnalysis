package com.sun.modules.show.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by SunGuiyong on 2017/10/10.
 */
@Controller
@RequestMapping(value = "/test")
public class ClientController {

    @RequestMapping(value = "/login")
//    @ResponseBody
    public ModelAndView ok() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login2")
//    @ResponseBody
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/ok2")
    @ResponseBody
    public String oktest() {
        return "ttt";
    }
}
