package com.hjp123.demo.service;

import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    /**
     * Question逻辑类
     */

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuesstionMapper quesstionMapper;

    public PaginationDTO selectAll(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = quesstionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page-1);
        //获取所有question对象
        List<Question> questionList = quesstionMapper.selectAll(offset,size);

        List<QuestionDTO> questionDTOS = new ArrayList<>();

        //循环遍历取出
        for (Question question : questionList) {
            //根据question的id查出对应发表该文章的用户
           User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //Spring内置工具类 快速将一个类属性拷贝到另一个类
            BeanUtils.copyProperties(question,questionDTO);
            //添加user进questionDTO类中
            questionDTO.setUser(user);

            //存入并返回集合
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }
}
