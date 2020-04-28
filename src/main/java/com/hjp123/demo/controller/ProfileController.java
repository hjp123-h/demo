package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Likes;
import com.hjp123.demo.bean.Notice;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.CommentDTO;
import com.hjp123.demo.dto.NoticeDTO;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.LikeMapper;
import com.hjp123.demo.mapper.NoticeMapper;
import com.hjp123.demo.mapper.UserMapper;
import com.hjp123.demo.service.CommentService;
import com.hjp123.demo.service.NoticeService;
import com.hjp123.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 我的问题类
 */

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CommentService commentService;

    //我的问题
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest httpServletRequest,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "10") Integer size) {

        User user = (User) httpServletRequest.getSession().getAttribute("user");

        if(user == null){
            return "redirect:/";
        }

        //获取未读消息
        Integer unread = noticeMapper.getUnread(user.getId());
        model.addAttribute("unread",unread);

        if (("questions".equals(action))) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        }

        PaginationDTO paginationDTO = questionService.selectAll(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);

        return "profile";
    }


}
