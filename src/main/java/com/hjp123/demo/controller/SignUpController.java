package com.hjp123.demo.controller;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class SignUpController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String LoginUser(String name , String password, HttpServletResponse httpServletResponse){
        System.out.println(name+" "+password);
        if (name != null && password != null){
            User user = userMapper.selectNamePassword(name, password);
            if(user != null){
                Long userid = user.getId();
                System.out.println(user);
                User GithubUser = new User(userid, user.getName(), user.getAccount(), user.getPassword(),UUID.randomUUID().toString());
                userMapper.updateUser(GithubUser);
                //返回token
                Cookie cookie = new Cookie("token", GithubUser.getToken());
                cookie.setMaxAge(3600);
                httpServletResponse.addCookie(cookie);
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @RequestMapping("/loginAjax")
    @ResponseBody
    Map login(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        Map<String, String> result = new HashMap();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (userName != null && password != null) {
            User user = userMapper.selectNamePassword(userName, password);
            if (user == null) {
                result.put("flag", "false");
            } else {
                Long userid = user.getId();
                System.out.println(user);
                User GithubUser = new User(userid, user.getName(), user.getAccount(), user.getPassword(),UUID.randomUUID().toString());
                userMapper.updateUser(GithubUser);
                //返回token
                Cookie cookie = new Cookie("token", GithubUser.getToken());
                cookie.setMaxAge(3600);
                httpServletResponse.addCookie(cookie);
                result.put("flag", "true");
            }
        }
        return result;
    }

}
