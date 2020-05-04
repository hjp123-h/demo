package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.Attention;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttentionMapper {

    void increaseAttention(Attention attention);
}
