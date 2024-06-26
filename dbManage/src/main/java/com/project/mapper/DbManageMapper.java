package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;

public interface DbManageMapper extends BaseMapper<Object> {

    @Select("CREATE TABLE IF NOT EXISTS ${tableName} (${tableName}_id INT PRIMARY KEY AUTO_INCREMENT)")
    void createTable(@Param("tableName")String tableName);

    @Select("CREATE TABLE IF NOT EXISTS ${tableName}_attribute (`attribute` varchar(45) PRIMARY KEY , `translation` varchar(45) ,`class_name` varchar(45) ,`privilege` INT)")
    void createTableMapping(String tableName, String translation);

    @Select("show tables")
    List<LinkedHashMap<String, Object>> getTables();

    List<LinkedHashMap<String, Object>> getTableData(@Param("tableName")String tableName,@Param("attribute")String attribute,@Param("value")String value);

    @Select("SELECT COLUMN_NAME AS attribute FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'project' AND TABLE_NAME = '${tableName}'")
    List<LinkedHashMap<String, Object>> getTableMapping(String tableName);

    @Select("SHOW COLUMNS FROM ${tableName}")
    List<LinkedHashMap<String, Object>> getTableStruct(String tableName);


    @Select("SELECT create_time FROM information_schema.tables WHERE table_schema = 'project' AND table_name = #{tableName}")
    List<LinkedHashMap<String, Object>> getTableCreateTime(@Param("tableName")String tableName);

    @Select("DROP TABLE  IF EXISTS ${tableName}")
    void deleteTable(String tableName);

    @Insert("INSERT INTO ${tableName}_attribute VALUES (#{attributeId}, #{translation}, 'INT', 1)")
    void initTable(String tableName,String translation,String attributeId);

    @Insert("INSERT INTO table_info VALUES (#{tableName}, #{translation})")
    void initTable2(String tableName, String translation);

    @Delete("DELETE FROM ${tableName} WHERE ${attribute} = #{value}")
    void deleteRecord(String tableName, String attribute, String value);
}
