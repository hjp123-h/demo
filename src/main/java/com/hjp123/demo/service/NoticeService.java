package com.hjp123.demo.service;

import com.hjp123.demo.bean.Comment;
import com.hjp123.demo.bean.Notice;
import com.hjp123.demo.bean.Question;
import com.hjp123.demo.bean.User;
import com.hjp123.demo.dto.NoticeDTO;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.dto.QuestionDTO;
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
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    //创建一个新通知
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

    //获取指定用户通知
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

    //我的通知分页
    public PaginationDTO selectAll(Long userId, Integer page, Integer size) {
        //创建分页DTO
        PaginationDTO paginationDTO = new PaginationDTO();
        //获取用户总条数
        Integer totalCount = noticeMapper.countById(userId);
        //调用分页工具类
        paginationDTO.setPagination(totalCount, page, size);

        //条数不能小于1
        if (page < 1) {
            page = 1;
        }
        //条数不能大于总页数
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        //分页公式 行数-1
        Integer offset = size * (page - 1);
        //获取分页后的集合
        List<Notice> notices = noticeMapper.selectAllByid(userId,offset, size);
        //调用方法放入通知DTO
        List<NoticeDTO> noticeDTOList = getBy(notices);

        //将通知DTO放入分页DTO
        paginationDTO.setNoticeDTO(noticeDTOList);
        return paginationDTO;
    }

    //获取指定用户通知
    public List<NoticeDTO> getBy(List<Notice> notices){

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
