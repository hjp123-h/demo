package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Likes;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.CommentDTO;
import com.hjp123.demo.dto.NoticeDTO;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.LikeMapper;
import com.hjp123.demo.mapper.NoticeMapper;
import com.hjp123.demo.service.CommentService;
import com.hjp123.demo.service.NoticeService;
import com.hjp123.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通知类
 */

@Controller
public class NoticeController {

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

    //所有通知
    @GetMapping("/profile/reolies")
    public String reolies(Model model,
                          HttpServletRequest httpServletRequest,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "10") Integer size){
        User user = (User) httpServletRequest.getSession().getAttribute("user");

        if(user == null){
            return "redirect:/";
        }

        //获取未读消息
        Integer unread = noticeMapper.getUnread(user.getId());
        model.addAttribute("unread",unread);

        //通知分页
        PaginationDTO paginationDTO = noticeService.selectAll(user.getId(), page, size);
        if (paginationDTO != null){
            model.addAttribute("pagination",paginationDTO);
        }

        model.addAttribute("section", "reolies");
        model.addAttribute("sectionName", "我的通知");

        return  "reolies";
    }

    //已读选中通知
    @GetMapping("/unread/{id}")
    public String unread(Model model,
                         HttpServletRequest request,
                         @PathVariable Long id){
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
            //更新阅读状态
            noticeMapper.updateHave(userId,id);

            //获取未读消息
            Integer unread = noticeMapper.getUnread(user.getId());
            model.addAttribute("unread",unread);

            //查询用户是否点赞过
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
        return "redirect:/question/"+id;
    }

    //已读全部通知
    @GetMapping("/unread/unreadAll")
    public String unreadAll(HttpServletRequest httpServletRequest,
                            Model model){
        User user = (User) httpServletRequest.getSession().getAttribute("user");

        if(user == null){
            return "redirect:/";
        }

        Integer unread = noticeMapper.getUnread(user.getId());
        model.addAttribute("unread",unread);

        noticeMapper.updateAll(user.getId());
        return "redirect:/profile/reolies/";
    }
}
