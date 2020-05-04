package com.hjp123.demo.controller;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class SignUpController {

    @Autowired
    private UserMapper userMapper;

    @Value("${default.avatar}")
    private String avaterURL;

    /**
     *使用Ajax登陆
     * @ResponseBody能将返回字符串转为json格式
     */
    @RequestMapping("/loginAjax")
    @ResponseBody
    Map login(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        //创建返回值Map
        Map<String, String> result = new HashMap();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (userName != null && password != null) {
            //查询账号密码是否正确
            User user = userMapper.selectNamePassword(userName, password);
            if (user == null) {
                result.put("flag", "false");
            } else {
                //账号密码正确 获取id
                Long userid = user.getId();
                //存入user缓存类
                User GithubUser = new User(userid, user.getName(), user.getAccount(), user.getPassword(),UUID.randomUUID().toString());
                //更新数据
                userMapper.updateUser(GithubUser);
                //返回token 存入cookie
                Cookie cookie = new Cookie("token", GithubUser.getToken());
                cookie.setMaxAge(3600);
                httpServletResponse.addCookie(cookie);
                result.put("flag", "true");
            }
        }
        return result;
    }

    /**
     * 进入注册页面方法
     */
    @RequestMapping("/registjump")
    public String registJump(){
        return "regist";
    }

    /**
     * 注册方法
     */

    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    String regist(String username,
                  String password,
                  HttpServletResponse response,
                  RedirectAttributes redirectAttributes,
                  Model model) {
        //用post传递 获取账号密码
        String userName = username;
        String passWord = password;
        if (userName != null && passWord != null) {
            //查询用户名是否存在
            User user = userMapper.selectName(userName);
            if (user == null) {
                //创建user缓存类
                User GithubUser = new User("用户" + userName, userName, passWord, UUID.randomUUID().toString());
                //将user插入数据库
                GithubUser.setAvatar(avaterURL);
                userMapper.addUser(GithubUser);
                //将token放入cookie返回
                Cookie cookie = new Cookie("token", GithubUser.getToken());
                cookie.setMaxAge(3600);
                response.addCookie(cookie);
                //重定向返回信息
                redirectAttributes.addFlashAttribute("registSecces","注册成功");
                return "redirect:/";
            } else {
                model.addAttribute("registErroe","注册失败");
                System.out.println("失败");
                return "regist";
            }
        }

        return "regist";
    }

    /**
     * 注销方法
     */

    @RequestMapping("/destroyCookie")
    public String destroyCookie(@CookieValue("token") Cookie userCookie,
                                HttpServletResponse response,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        //消除cookie
        userCookie.setMaxAge(0);
        userCookie.setPath("/");
        response.addCookie(userCookie);
        //消除session
        HttpSession session = request.getSession();
        session.invalidate();
        redirectAttributes.addFlashAttribute("destroyCookie","退出登陆成功");
        return "redirect:/";
    }

}
