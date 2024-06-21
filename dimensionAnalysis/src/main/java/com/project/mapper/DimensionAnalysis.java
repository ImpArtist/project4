package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;

public interface DimensionAnalysis extends BaseMapper<Object> {

    @Select("select attribute,translation from stu_info_attribute where privilege between 21 and 34 order by privilege")
    List<LinkedHashMap<String, Object>> getList();
}
