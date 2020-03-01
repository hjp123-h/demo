package com.hjp123.demo.controller;

import com.hjp123.demo.dto.AccessTokenDTO;
import com.hjp123.demo.dto.GithubUser;
import com.hjp123.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeConteoller {

    @Autowired
    private GithubProvider githubProvider;

    /**
     * 用来向接收Github发送过来的用户密钥
     */

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,@RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("73ea771286d3bb60c202");
        accessTokenDTO.setClient_secret("4246ea5f2d94357467ffc070898740d8309d2273");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8088/callback");
        accessTokenDTO.setState(state);
        System.out.println(accessTokenDTO);
        //提交密钥
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //解析用户数据
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName()+"---"+user.getId());
        return "index";
    }
}
