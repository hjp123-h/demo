package com.hjp123.demo.dto;

import lombok.Data;

/**
 * ajax传输数据使用的模型
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
