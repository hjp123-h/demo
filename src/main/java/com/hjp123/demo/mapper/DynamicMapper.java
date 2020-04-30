package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.Dynamic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DynamicMapper {

    List<Dynamic> selectDynamicById (long id, Integer offset, Integer size);

    Integer countById(Long id);
}
