package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuesstionMapper {

    //插入数据
    void increaseQuestion(Question question);

    //查询所有数据
    List<Question> selectAll(Integer offset, Integer size);

    //搜索数据分页
    List<Question> selectsearch(Integer offset, Integer size, String search);

    //查询总条数
    Integer count();

    //模糊查询条数
    Integer countLike(String search);

    //查询总条数
    Integer countById(Long userId);

    //查询指定用户发表的文章进行分页
    List<Question> selectAllByid(Long userId, Integer offset, Integer size);

    //查询指定文章
    Question getById(Long userId);

    //查询热门文章
    List<Question> seleteHot();

    //更新文章
    void update(Question question);

    //增加浏览数
    void viewId(Long id);

    //增加点赞数
    void likesAdd(Long id);

    //删除文章
    void deleteId(Long id);

    //减少点赞数
    void likesDelete(Long articleId);

    //增加回复数
    void commentAdd(Long id);

    //标签推荐
    List<Question> selectRelated (Long id, String tag);

}
