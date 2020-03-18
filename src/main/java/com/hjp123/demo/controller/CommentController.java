package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Comment;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.CommentDTO;
import com.hjp123.demo.dto.ResultDTO;
import com.hjp123.demo.exception.CustomizeErrorCode;
import com.hjp123.demo.mapper.CommentMapper;
import com.hjp123.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object  post(@RequestBody CommentDTO commentDTO,
                        HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDTO==null||StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_NULL);
        }
        Comment comment = new Comment();
        //前端ajax传来的数据自动封装进commentDTO中;
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());

        comment.setLikeCount(0L);
        comment.setCommentCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);

        return ResultDTO.okOf();
    }
}
