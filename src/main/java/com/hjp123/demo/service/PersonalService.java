package com.hjp123.demo.service;

import com.hjp123.demo.bean.*;
import com.hjp123.demo.dto.DynamicDTO;
import com.hjp123.demo.dto.NoticeDTO;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.mapper.CommentMapper;
import com.hjp123.demo.mapper.DynamicMapper;
import com.hjp123.demo.mapper.QuesstionMapper;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalService {

    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuesstionMapper quesstionMapper;
    @Autowired
    private UserMapper userMapper;

    //我的动态
    public PaginationDTO selectAll(Long userId, Integer page, Integer size) {
        //创建分页DTO
        PaginationDTO paginationDTO = new PaginationDTO();
        //获取用户总条数
        Integer totalCount = dynamicMapper.countById(userId);

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
        List<Dynamic> dynamics = dynamicMapper.selectDynamicById(userId, offset, size);
        //将数据放入DTO集合
        List<DynamicDTO> dynamicDTOList = new ArrayList<>();
        //获取整个用户动态信息
        User user = userMapper.selectById(userId);
        //分页后大于o
        if (dynamics.size() > 0) {
            for (Dynamic d : dynamics){
                DynamicDTO dynamicDTO = new DynamicDTO();
                //将数据拷贝进DTO
                BeanUtils.copyProperties(d,dynamicDTO);
                //获取回复信息
                Comment comment = commentMapper.selectById(dynamicDTO.getCid());
                //获取文章作者信息 防止出现空指针
                User commentuser = null;
                Question question = null;
                if(comment != null){
                    question = quesstionMapper.getById(comment.getParentId());
                    commentuser = userMapper.selectById(question.getCreator());
                }

                if (user != null){
                    dynamicDTO.setCcomment(comment);
                    dynamicDTO.setUser(user);
                    dynamicDTO.setCommentuser(commentuser);
                    dynamicDTO.setCommentquestion(question);
                    dynamicDTOList.add(dynamicDTO);
                }
            }
        }else {
           DynamicDTO dynamicDTO = new DynamicDTO();
           dynamicDTO.setUser(user);
           dynamicDTOList.add(dynamicDTO);
        }
        //将动态DTO放入分页DTO
        paginationDTO.setDynamicDTOS(dynamicDTOList);
        return paginationDTO;
    }
}
