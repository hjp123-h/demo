package com.hjp123.demo.service;

import com.hjp123.demo.bean.Likes;
import com.hjp123.demo.bean.Notice;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.mapper.LikeMapper;
import com.hjp123.demo.mapper.NoticeMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 点赞逻辑类
 */

@Service
public class LikesService {

    @Autowired
    public LikeMapper likeMapper;
    @Autowired
    public QuesstionMapper quesstionMapper;
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public NoticeService noticeService;

    public void addLike(Long articleId,Long userId){
        //获取文章信息和用户信息
        Question question = quesstionMapper.getById(articleId);
        User user = userMapper.selectById(userId);
        if (question != null && user != null){
            //判断用户是否点赞过
            Likes likeMapperById = likeMapper.getById(articleId, userId);
            if (likeMapperById == null){
                //在likes表中增加用户对应点赞文章数据
                likeMapper.addArticleId(articleId,userId);
                //在文章主表中增加点赞数
                quesstionMapper.likesAdd(articleId);
                //添加点赞通知
                noticeService.addArticleId(user.getId(),question.getCreator(),question.getId(),
                        1,1,"收到了一条新点赞");
            }else {
                //在likes表中删除用户对应点赞文章数据
                likeMapper.deleteArticleId(articleId,userId);
                //在文章主表中删除点赞数
                quesstionMapper.likesDelete(articleId);
            }
        }

    }
}
