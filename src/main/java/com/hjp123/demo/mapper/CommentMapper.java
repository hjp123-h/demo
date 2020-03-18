package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {


    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(Comment comment);

    Comment selectById(Long id);

    List<Comment> selectByParentId(Long id, Integer type);

    void incCommentCount(Long commentId, Integer commentCount);

    void incLikeCount(Long commentId, Integer likeCount);

    void deleteFromId(Long id);

    void deleteFromParentId(Long id);
}
