package com.hjp123.demo.service;

import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.LikeMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    /**
     * Question逻辑类
     */

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuesstionMapper quesstionMapper;
    @Autowired
    private UserService userService;


    //主页分页
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

    //我的问题分页
    public PaginationDTO selectAll(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = quesstionMapper.countById(userId);
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

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
    //增加阅读数
    public void incView(Long id) {
       quesstionMapper.viewId(id);
    }

    //获取相同标签
    public List<Question> selectRelated(QuestionDTO questionDTO){
        //判断标签是否为空
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
       String replace = StringUtils.replace(questionDTO.getTag(), ",", "|");
        List<Question> questions = quesstionMapper.selectRelated(Long.valueOf(questionDTO.getId()), replace);
        return questions;
    }

    //标签格式化类
    public String formatTag (String tag){

        //设置标签暂存集合
        ArrayList endArray = new ArrayList<String>();
        //不为空情况下进入
        if (StringUtils.isNotBlank(tag)) {
            //将用户输入的中文逗号转换为英文逗号
            String replace = StringUtils.replace(tag, "，", ",");
            //去除首尾空格
            String deleteWhitespace = StringUtils.deleteWhitespace(replace);
            //删除特殊符号
            String replaceAll = deleteWhitespace.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5,，]+", "");

            for (int i = 0;i<replaceAll.length();i++){
                endArray.add(replaceAll.charAt(i));
            }
            //获取集合迭代器
            ListIterator<String> iterator = endArray.listIterator();

            //设置前后元素
            Object next1 = null;
            Object next2 = null;
            while (iterator.hasNext()){
                //获取后元素
                next2 = iterator.next();
                //判断前元素是否为空
                if (next1 != null){
                    //判断元素是否相等
                    if (next1.equals(next2)){
                        //判断元素是否等于指定符号
                        if(next2.toString().contains(",")) {
                            //一样的话删除元素
                            iterator.remove();
                        }
                    }else {
                        //前后元素不相等是 更新前元素
                        next1 = next2;
                    }
                }else {
                    //前元素为空时 将后元素赋值
                    next1 = next2;
                }
            }
            //转换为string
            String listString = (String) endArray.stream().map(Object::toString)
                    .collect(Collectors.joining(""));
            //移除开头标点
            String start = StringUtils.removeStart(listString, ",");
            //移除结尾标点
            String end = StringUtils.removeEnd(start, ",");
            return end;
        }else {
            return StringUtils.defaultString(tag);
        }
    }
}
