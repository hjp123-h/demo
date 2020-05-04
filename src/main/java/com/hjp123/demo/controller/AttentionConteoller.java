package com.hjp123.demo.controller;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AttentionConteoller {

    @RequestMapping(value = "/attention/{id}")
    public String attention(@PathVariable Long id, HttpServletResponse response, RedirectAttributes attributes){
        try {
            response.sendRedirect("/Personal/"+id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
