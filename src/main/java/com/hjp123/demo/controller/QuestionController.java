package com.hjp123.demo.controller;

import com.hjp123.demo.dto.QuestionDTO;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 进入指定问题页面类
 */

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        //根据问题id获取问题详情
        QuestionDTO questionDTO = questionService.getId(id);
        //将问题详情传入model
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
