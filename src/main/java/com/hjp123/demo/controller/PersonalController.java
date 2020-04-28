package com.hjp123.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonalController {

    @RequestMapping("/Personal")
    public String PersonalIndex(){
        return "Personal";
    }

    @RequestMapping("/personalmodify")
    public String PersonalModifyIndex(){
        return "personalmodify";
    }
}
