package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import com.hjp123.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String hello(Model model, HttpServletRequest httpServletRequest){

        //传递登陆Github参数
        model.addAttribute("uri",uri);
        model.addAttribute("clientid",Clientid);

        //获取前端cookie
        Cookie[] cookies = httpServletRequest.getCookies();
        //判断cookies是否为空
        if (cookies != null){
            //遍历这个cookie
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    //获取这个cookie中的id
                    String value = cookie.getValue();

                    //进入数据库查询 获得对象
                    User user = userMapper.selectByToken(value);

                    //判断对象不为空的情况下 放入session
                    if(user != null){

                        httpServletRequest.getSession().setAttribute("user",user);
                    }
                    break;
                }

            }
        }

        //获取全部文章
        List<QuestionDTO> questionList = questionService.selectAll();
        model.addAttribute("questionList",questionList);
        return "index";
    }

}
