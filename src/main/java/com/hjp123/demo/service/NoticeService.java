package com.hjp123.demo.service;

import com.hjp123.demo.bean.Comment;
import com.hjp123.demo.bean.Notice;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.NoticeDTO;
import com.hjp123.demo.mapper.CommentMapper;
import com.hjp123.demo.mapper.NoticeMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private QuesstionMapper quesstionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    public void addArticleId(Long authorid,Long replyid,int articleid,int  type,int status,String content){
        if(!authorid.equals(replyid)){
            Notice notice = new Notice();
            notice.setAuthorid(authorid);
            notice.setReplyid(replyid);
            notice.setArticleid(articleid);
            notice.setType(type);
            notice.setStatus(status);
            notice.setContent(content);
            notice.setTime(System.currentTimeMillis());
            System.out.println(notice);
            noticeMapper.addArticleId(notice);
        }
    }
    public List<NoticeDTO> getById(Long replyid){

        List<Notice> notices = noticeMapper.getById(replyid);
        if (notices != null){
            List<NoticeDTO> noticeDTOS = new ArrayList<>();
            for (Notice n :notices) {
                if (n.getType() != 3){
                    NoticeDTO noticeDTO = new NoticeDTO();
                    BeanUtils.copyProperties(n,noticeDTO);
                    Question byId = quesstionMapper.getById(Long.valueOf(n.getArticleid()));
                    noticeDTO.setArticle(byId.getTitle());
                    User user = userMapper.selectById(n.getAuthorid());
                    noticeDTO.setAuthor(user.getName());
                    noticeDTOS.add(noticeDTO);
                }else {
                    NoticeDTO noticeDTO = new NoticeDTO();
                    BeanUtils.copyProperties(n,noticeDTO);
                    Comment comment = commentMapper.selectById(Long.valueOf(n.getArticleid()));
                    noticeDTO.setArticle(comment.getContent());
                    User user = userMapper.selectById(n.getAuthorid());
                    noticeDTO.setAuthor(user.getName());
                    noticeDTOS.add(noticeDTO);
                }

            }
            return noticeDTOS;
        }

        return null;

    }
}
