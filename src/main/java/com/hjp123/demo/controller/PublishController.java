package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.mapper.QuesstionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    private QuesstionMapper quesstionMapper;

    /**
     * 进入注册页面类
     */
    @GetMapping("/publish")
    public String PublishController() {
        return "publish";
    }

    /**
     *注册页面传递数据类
     */

    @PostMapping("/publish")
    public String doPublishController(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Model model) {
        //判断标题是否为空
        if (title != null && title != "") {
            //判断正文是否为空
            if (description != null && description != "") {
                //创建Question对象 传入参数
                Question question = new Question();
                question.setTitle(title);
                question.setDescription(description);
                question.setTag(tag);
                //获取session
                HttpSession session = httpServletRequest.getSession();
                Object object = session.getAttribute("user");
                //判断session是否为user类型 并且不等于空
                if (object instanceof User && object != null) {
                    User user = (User) object;
                    //取出user中数据 放入Qusetion中
                    question.setCreator(user.getId());
                    question.setGmtCreate(System.currentTimeMillis());
                    question.setGmtModified(question.getGmtCreate());

                    //将正文内容插入数据库
                    quesstionMapper.increaseQuestion(question);
                    //调用RedirectAttributes类放入成功信息 用于重定向传递数据 Model无法重定向传递数据
                    redirectAttributes.addFlashAttribute("Publishseccuse", "发布成功");
                    return "redirect:/";
                } else {
                    //调用Model放入错误信息
                    model.addAttribute("error", "用户未登录");
                    return "publish";
                }

            }else {
                model.addAttribute("supplementError", "问题补充不能为空");
                return "publish";
            }
        }else {
            model.addAttribute("titleError", "标题不能为空");
            return "publish";
        }
    }
}


