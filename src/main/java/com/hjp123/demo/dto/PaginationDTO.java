package com.hjp123.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页功能后台实现
 */
@Data
public class PaginationDTO {
    private List<NoticeDTO> noticeDTO;//通知DTO
    private List<QuestionDTO> questionDTOS;//文章DTO
    private boolean showPrevious;//前一页按钮
    private boolean showFirstPage;//首页按钮
    private boolean showNext;//下一页按钮
    private boolean showEndPage;//尾页按钮
    private Integer page;//条数
    private List<Integer> pages = new ArrayList<>();//条数数组
    private Integer totalPage;//总分页数
    private Integer totalPages;//总条数

    //分页工具类
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        totalPages = totalCount;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "noticeDTO=" + noticeDTO +
                ", questionDTOS=" + questionDTOS +
                ", showPrevious=" + showPrevious +
                ", showFirstPage=" + showFirstPage +
                ", showNext=" + showNext +
                ", showEndPage=" + showEndPage +
                ", page=" + page +
                ", pages=" + pages +
                ", totalPage=" + totalPage +
                '}';
    }
}
