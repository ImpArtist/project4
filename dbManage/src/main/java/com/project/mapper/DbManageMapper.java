package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;

public interface DbManageMapper extends BaseMapper<Object> {

    void createTable(@Param("tableName")String tableName, @Param("translation")String translation);

    @Select("show tables")
    List<LinkedHashMap<String, Object>> getTables();
}
