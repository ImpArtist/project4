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

    @Select("select * from ${tableName}")
    List<LinkedHashMap<String, Object>> getTableData(String tableName);

    @Select("SELECT COLUMN_NAME AS attribute FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'project' AND TABLE_NAME = '${tableName}'")
    List<LinkedHashMap<String, Object>> getTableMapping(String tableName);

    @Select("SHOW COLUMNS FROM ${tableName}")
    List<LinkedHashMap<String, Object>> getTableStruct(String tableName);


    @Select("SELECT create_time FROM information_schema.tables WHERE table_schema = 'project' AND table_name = #{tableName}")
    List<LinkedHashMap<String, Object>> getTableCreateTime(@Param("tableName")String tableName);
}
