package com.hjp123.demo.bean;

import lombok.Data;

@Data
public class Notice {

    private Long authorid; //主人id
    private Long replyid; //通知人id
    private Integer articleid; //文章id
    private int type; //1点赞2评论3回复
    private int status; //1未读2已读
    private String content; //内容
    private Long time; //时间
}
