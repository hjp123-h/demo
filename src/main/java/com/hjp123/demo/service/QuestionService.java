package com.hjp123.demo.service;

import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
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

    public PaginationDTO selectAll(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = quesstionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = quesstionMapper.selectAllByid(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        questions.forEach(i -> {
            User user = userService.findUserById(i.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(i, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        paginationDTO.setQuestionDTOS(questionDTOList);
        return paginationDTO;
    }

    //根据问题id返回问题详情
    public QuestionDTO getId(Long id) {
        //找到问题详情
        Question question = quesstionMapper.getById(id);
        //根据问题创建者id找到用户id
        User user = userService.findUserById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        //用户信息写入DTO中
        questionDTO.setUser(user);
        //问题信息拷贝到DTO中
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;

    }

    //根据id判断文章是否存在
    public void increaseQuestion(Question question) {
        if (question.getId() == null){
            //记录创建时间
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            //插入数据
            quesstionMapper.increaseQuestion(question);
        }else {
            //记录更新时间
            question.setGmtModified(System.currentTimeMillis());
            //更新数据
            quesstionMapper.update(question);
        }
    }
}
