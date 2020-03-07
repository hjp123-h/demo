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

    //查询总条数
    Integer count();

    //查询指定用户发表的文章进行分页
    List<Question> selectAllByid(Long userId, Integer offset, Integer size);

    //查询指定用户文章 不进行分页
    Question countById(Long userId);

    //查询指定文章
    Question getById(Long userId);

    //更新文章
    void update(Question question);
}
