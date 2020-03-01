package com.hjp123.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Value("${Github.setClient.id}")
    private String Clientid;
    @Value("${Github.setRedirect.uri}")
    private String uri;

    @GetMapping("/")
    public String hello(Model model){

        model.addAttribute("uri",uri);
        model.addAttribute("clientid",Clientid);
        return "index";
    }
}
