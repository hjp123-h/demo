package com.hjp123.demo.interceptor;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.mapper.NoticeMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置登陆拦截器
 */

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取前端cookie
        Cookie[] cookies = request.getCookies();
        //判断cookies是否为空
        if (cookies != null){
            //遍历这个cookie
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    //获取这个cookie中的id
                    String value = cookie.getValue();

                    //进入数据库查询 获得对象
                    User user = userMapper.selectByToken(value);

                    //判断对象不为空的情况下 放入session
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                        Integer unread = noticeMapper.getUnread(user.getId());
                        request.getSession().setAttribute("notice",unread);
                    }
                    break;
                }

            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
