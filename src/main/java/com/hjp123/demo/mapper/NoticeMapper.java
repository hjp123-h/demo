package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.Notice;
import com.hjp123.demo.bean.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    //创建通知
    void addArticleId(Notice notice);

    //查询当前id所有的通知
    List<Notice> getById(Long replyid);

    //查询指定用户通知条数分页
    List<Notice> selectAllByid(Long userId, Integer offset, Integer size);

    //查询指定用户通知条数
    Integer countById(Long replyid);

    //查询未读
    Integer getUnread(Long replyid);

    //更新已读状态
    void updateHave(Long replyid,Long articleid);

    //全部已读
    void updateAll(Long replyid);

}
