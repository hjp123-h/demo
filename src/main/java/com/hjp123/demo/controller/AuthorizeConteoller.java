package com.hjp123.demo.controller;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.AccessTokenDTO;
import com.hjp123.demo.dto.GithubUser;
import com.hjp123.demo.mapper.UserMapper;
import com.hjp123.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeConteoller {

    @Autowired
    private GithubProvider githubProvider;

    /**
     * 用来向接收Github发送过来的用户密钥
     */

    @Value("${Github.setClient.id}")
    private String Clientid;
    @Value("${Github.setClient.secret}")
    private String Secret;
    @Value("${Github.setRedirect.uri}")
    private String Uri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Clientid);
        accessTokenDTO.setClient_secret(Secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(Uri);
        accessTokenDTO.setState(state);
        System.out.println(accessTokenDTO);
        //提交密钥
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //解析用户数据
        System.out.println(accessToken+" accessToken ");
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user+" GithubUser");
        System.out.println(user.getName()+"====="+user.getId());
        if (user != null){
            Long userid = user.getId();
            User selectById = userMapper.selectById(userid);
            if (selectById != null){
                request.getSession().setAttribute("user",selectById);
                System.out.println("登陆成功");
                return "redirect:/";
            }
            User GithubUser = new User(userid, "Github用户" + userid, "Github" + userid, "GithubPassword" + userid);
            //登陆成功
            userMapper.increaseGithubUser(GithubUser);
            request.getSession().setAttribute("user",GithubUser);
            System.out.println("注册成功");
            return "redirect:/";
        }else{
            //登陆失败
            System.out.println("失败");
            return "redirect:/";
        }
    }
}
