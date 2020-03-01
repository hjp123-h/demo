package com.hjp123.demo.controller;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Value("${Github.setClient.id}")
    private String Clientid;
    @Value("${Github.setRedirect.uri}")
    private String uri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
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
                    System.out.println("cookie value======= "+ value);
                    //进入数据库查询 获得对象
                    User user = userMapper.selectByToken(value);
                    System.out.println("cookie ======= "+ user);
                    //判断对象不为空的情况下 放入session
                    if(user != null){
                        System.out.println("user ========="+user.getName());
                        httpServletRequest.getSession().setAttribute("user",user);
                    }
                    break;
                }

            }
        }

        return "index";
    }
}
