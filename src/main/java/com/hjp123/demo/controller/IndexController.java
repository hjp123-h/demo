package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.LikeMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import com.hjp123.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 主页登陆类
 */

@Controller
public class IndexController {

    @Value("${Github.setClient.id}")
    private String Clientid;
    @Value("${Github.setRedirect.uri}")
    private String uri;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size){

        //传递登陆Github参数
        model.addAttribute("uri",uri);
        model.addAttribute("clientid",Clientid);

        //获取全部文章
        PaginationDTO pagination = questionService.selectAll(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
