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

    //我的通知分页
    public PaginationDTO selectAll(Long userId, Integer page, Integer size) {
        //创建分页DTO
        PaginationDTO paginationDTO = new PaginationDTO();
        //获取用户总条数
        Integer totalCount = noticeMapper.countById(userId);

        //调用分页工具类！！！！！！！！！！！！！！
        paginationDTO.setPagination(totalCount, page, size);

        //页数不能小于1
        if (page < 1) {
            page = 1;
        }
        //页数不能大于总页数
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        //分页公式 行数-1
        Integer offset = 0;
        if(page > 0){
            offset = size * (page - 1);
        }
        //获取分页后的集合
        List<Notice> notices = noticeMapper.selectAllByid(userId,offset, size);
        //调用方法放入通知DTO
        List<NoticeDTO> noticeDTOList = null;
        if (notices.size() > 0) {
            noticeDTOList = getBy(notices);
        }
        //将通知DTO放入分页DTO
        paginationDTO.setNoticeDTO(noticeDTOList);
        return paginationDTO;
    }

    //获取指定用户通知
    public List<NoticeDTO> getBy(List<Notice> notices){

        //参数不是空的情况下
        if (notices != null){
            List<NoticeDTO> noticeDTOS = new ArrayList<>();
            //遍历这个参数
            for (Notice n :notices) {
                //判断type不是点赞情况下
                if (n.getType() != 3){
                    //创建一个新的通知DTO
                    NoticeDTO noticeDTO = new NoticeDTO();
                    //将数据拷贝过去
                    BeanUtils.copyProperties(n,noticeDTO);
                    //获取文章信息
                    Question byId = quesstionMapper.getById(Long.valueOf(n.getArticleid()));
                    //向DTO传入文章标题
                    noticeDTO.setArticle(byId.getTitle());
                    //获取通知者信息
                    User user = userMapper.selectById(n.getAuthorid());
                    //获取通知者姓名
                    noticeDTO.setAuthor(user.getName());
                    //获取通知者信息
                    noticeDTO.setAuthorUser(user);
                    //放入通知DTO集合
                    noticeDTOS.add(noticeDTO);
                }else {
                    //创建一个新的通知DTO
                    NoticeDTO noticeDTO = new NoticeDTO();
                    //将数据拷贝过去
                    BeanUtils.copyProperties(n,noticeDTO);
                    //获取点赞信息
                    Comment comment = commentMapper.selectById(Long.valueOf(n.getArticleid()));
                    //向DTO传入点赞信息
                    noticeDTO.setArticle(comment.getContent());
                    //获取通知者信息
                    User user = userMapper.selectById(n.getAuthorid());
                    //获取通知者姓名
                    noticeDTO.setAuthor(user.getName());
                    //获取通知者信息
                    noticeDTO.setAuthorUser(user);
                    //放入通知DTO集合
                    noticeDTOS.add(noticeDTO);
                }

            }
            return noticeDTOS;
        }
        return null;
    }
}
