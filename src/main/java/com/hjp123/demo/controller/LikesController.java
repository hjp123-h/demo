package com.hjp123.demo.controller;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 点赞请求类
 */

@Controller
public class LikesController {

    @Autowired
    private LikesService likesService;

    @RequestMapping("/likes/addlikes")
    @ResponseBody
    Map likesAdd(HttpServletRequest request,
              HttpServletResponse httpServletResponse) {
        Map result = new HashMap();
        //获取ajax传入的文章id
        String articleId = request.getParameter("articleId");
        //获取session中的用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断是否为空
        if (articleId != null && user != null){
            //获取用户id
            Long userId = user.getId();
            //将用户id放入点赞方法中
            likesService.addLike(Long.valueOf(articleId),userId);
            return result;
        }
        return result;
    }
}
