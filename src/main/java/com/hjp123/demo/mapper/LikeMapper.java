package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.Likes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {

    //新增点赞记录
    void addArticleId(Long articleId,Long userId);

    //删除点赞记录
    void deleteArticleId(Long articleId,Long userId);

    //查询点赞记录
    Likes getById(Long articleId, Long userId);
}
