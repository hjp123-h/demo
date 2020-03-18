package com.hjp123.demo.service;

import com.hjp123.demo.bean.Comment;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.CommentDTO;
import com.hjp123.demo.enums.CommentTypeEnum;
import com.hjp123.demo.exception.CustomizeErrorCode;
import com.hjp123.demo.exception.CustomizeException;
import com.hjp123.demo.mapper.CommentMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuesstionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOTFOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOTFOUND);
            }
            commentMapper.insert(comment);
        }else {
            Question question = questionMapper.getById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOTFOUND);
            }
            commentMapper.insert(comment);

            questionMapper.commentAdd(comment.getParentId());
        }

    }

    public List<CommentDTO> listByQuestionId(Long id,Integer type) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        List<Comment> comments = commentMapper.selectByParentId(id, type);
        if (comments == null) {
            return new ArrayList<>();
        }
        comments.forEach(i -> {
            CommentDTO commentDTO = new CommentDTO();
            User user = userMapper.selectById(i.getCommentator());
            commentDTO.setUser(user);
            BeanUtils.copyProperties(i, commentDTO);
            commentDTOS.add(commentDTO);
        });
        return commentDTOS;
    }
}
