package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuesstionMapper {

    //插入数据
    void increaseQuestion(Question question);

    //查询所有数据
    List<Question> selectAll();
}
