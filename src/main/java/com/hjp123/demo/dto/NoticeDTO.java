package com.hjp123.demo.dto;

import com.hjp123.demo.bean.User;
import lombok.Data;

@Data
public class NoticeDTO {

    private Long authorid; //通知者id
    private User authorUser; //通知者id
    private Long replyid; //被通知id
    private Integer articleid; //文章id
    private int type; //1点赞2评论3回复
    private int status; //1未读2已读
    private String content; //内容
    private Long time; //时间
    private String author; //通知者
    private String article; //文章标题
    private User user;

}
