package com.hjp123.demo.controller;

import com.hjp123.demo.dto.AccessTokenDTO;
import com.hjp123.demo.dto.GithubUser;
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
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName()+"====="+user.getId());
        if (user != null){
            //登陆成功
            request.getSession().setAttribute("user",user);
            System.out.println("成功");
            return "redirect:/";
        }else{
            //登陆失败
            System.out.println("失败");
            return "redirect:/";
        }
    }
}
