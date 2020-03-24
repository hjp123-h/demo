package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Likes;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.CommentDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.LikeMapper;
import com.hjp123.demo.mapper.NoticeMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.service.CommentService;
import com.hjp123.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 进入指定问题页面类
 */

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           HttpServletRequest request,
                           Model model){
        //根据问题id获取问题详情
        QuestionDTO questionDTO = questionService.getId(id);
        //更新浏览数
        questionService.incView(id);
        //获取用户session
        User user = (User) request.getSession().getAttribute("user");
        //获取当前问题id
        Long questionDTOId = Long.valueOf(questionDTO.getId());
        //问题id和用户session不为空情况下进入
        if (questionDTOId != null && user != null){
            Long userId = user.getId();
            //查询用户是否点赞过
            //获取未读消息
            Integer unread = noticeMapper.getUnread(user.getId());
            model.addAttribute("unread",unread);
            Likes likeMapperById = likeMapper.getById(questionDTOId,userId);
            //查询出来不等于0的话 给likes赋值have，否则NoLike

            if (likeMapperById != null){
                questionDTO.setLikes("Have");
            }else {
                questionDTO.setLikes("NoLike");
            }
        }

        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id,1);
        List<Question> relatedQuestionDTO = questionService.selectRelated(questionDTO);
        //将问题详情传入model
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOS);
        model.addAttribute("relatedQuestionDTO",relatedQuestionDTO);
        return "question";
    }
}
