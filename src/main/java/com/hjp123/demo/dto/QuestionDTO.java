package com.hjp123.demo.dto;

import com.hjp123.demo.bean.User;
import lombok.Data;

/**
 * user数据传输类
 * 相比user多了一个参数
 * private User user
 */

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
