package com.hjp123.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 搜索功能
 */
@Controller
public class SearchController {

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public void search(HttpServletRequest httpServletRequest){

    }
}
