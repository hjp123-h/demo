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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
                           HttpServletRequest request, HttpServletResponse httpServletResponse){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Clientid);
        accessTokenDTO.setClient_secret(Secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(Uri);
        accessTokenDTO.setState(state);
        //提交密钥
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //解析用户数据
        //将token传入 获得用户数据
        GithubUser user = githubProvider.getUser(accessToken);
        //判断是否登陆Github成功
        if (user != null){
            //查询这个token是否注册过
            User selectByToken = userMapper.selectById(user.getId());
            if (selectByToken != null){
                //更新数据库数据
                Long userid = user.getId();
                User GithubUser = new User(userid, "Github用户" + userid, "Github" + userid, "GithubPassword" + userid, UUID.randomUUID().toString());
                userMapper.updateUser(GithubUser);
                //返回token
                Cookie cookie = new Cookie("token", GithubUser.getToken());
                cookie.setMaxAge(3600);
                httpServletResponse.addCookie(cookie);

                return "redirect:/";
            }
            //获取id 创建user对象
            Long userid = user.getId();
            User GithubUser = new User(userid, "Github用户" + userid, "Github" + userid, "GithubPassword" + userid, UUID.randomUUID().toString());

            //注册成功 放到数据库
            userMapper.increaseGithubUser(GithubUser);
            //返回cookie
            Cookie cookie = new Cookie("token", GithubUser.getToken());
            cookie.setMaxAge(3600);
            httpServletResponse.addCookie(cookie);
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }
    }
}
