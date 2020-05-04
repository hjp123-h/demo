package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.LikeMapper;
import com.hjp123.demo.mapper.NoticeMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import com.hjp123.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    private QuesstionMapper quesstionMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    @RequestMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        HttpServletRequest httpServletRequest){

        //传递登陆Github参数
        model.addAttribute("uri",uri);
        model.addAttribute("clientid",Clientid);

        User user = (User)httpServletRequest.getSession().getAttribute("user");

        //获取全部文章
        PaginationDTO pagination = questionService.selectAll(page,size,null);
        model.addAttribute("pagination",pagination);
        //获取热门文章
        List<Question> questions = quesstionMapper.seleteHot();
        model.addAttribute("questions",questions);
        return "index";
    }


    /**
     * 搜索文章
     */
    @GetMapping("/SearchFor")
    public String SearchFor(String search,
                            Model model,
                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "size", defaultValue = "10") Integer size){

        //搜索文章
        PaginationDTO pagination = questionService.selectAll(page,size,search);
        model.addAttribute("pagination",pagination);
        if (!search.equals("") && search != null){
            model.addAttribute("size",pagination.getTotalPages());
            model.addAttribute("search",search);
        }
        //获取热门文章
        List<Question> questions = quesstionMapper.seleteHot();
        model.addAttribute("questions",questions);
        return "index";
    }
}
