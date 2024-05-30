package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.pojo.AttributeTranslation;
import com.project.domain.pojo.StuInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface InfoMapper extends BaseMapper<Object> {

    @Select("SELECT attribute,translation FROM ${table}_attribute order by privilege")
    List<AttributeTranslation> selectAttributeTranslations(@Param("table") String table);

    List<LinkedHashMap<String, Object>> queryListByAttributeConcrete(@Param("table") String table, @Param("attribute") String attribute, @Param("value") String value, @Param("attributes") String attributes, @Param("order") String order, @Param("desc") String desc, @Param("start") Integer start, @Param("count") Integer count);

    List<LinkedHashMap<String, Object>> queryListByAttributeAbstract(@Param("table") String table,@Param("attribute") String attribute, @Param("value") String value,@Param("attributes") String attributes,@Param("order") String order, @Param("desc") String desc, @Param("start") Integer start, @Param("count") Integer count);

    @Select("SELECT ${attributes} FROM ${table} WHERE ${attribute} >= #{start} AND ${attribute} <= #{end}")
    List<Map<String, Object>> queryRangeListByAttribute(@Param("table") String table,@Param("attribute") String attribute, @Param("start") String start, @Param("end") String end,@Param("attributes") String attributes);

    @Select("Select * FROM ${table}_attribute")
    List<Map<String, Object>> queryAllAttribute(@Param("table") Object table);

    @Select("Select * FROM ${table}")
    List<Map<String, Object>>  queryAll(String table);

    @Select("Select * FROM table_info")
    List<Map<String, Object>> queryTableName();

    @Select("Select ${attributes} FROM ${table1},${table2} WHERE ${table1}.${attribute1} ${compareType} ${table2}.${attribute2}")
    List<Map<String, Object>> queryConnectSearchedList(@Param("table1") String table1,@Param("table2") String table2,@Param("attribute1") String attribute1,@Param("attribute2") String attribute2,@Param("compareType") String compareType,@Param("attributes") String attributes);

    @Select("Select SUM(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupSumList(String table, String aggregateAttri, String groupAttri);

    @Select("Select AVG(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupAvgList(String table, String aggregateAttri, String groupAttri);

    @Select("Select MIN(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupMinList(String table, String aggregateAttri, String groupAttri);

    @Select("Select MAX(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupMaxList(String table, String aggregateAttri, String groupAttri);

    @Select("Select COUNT(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupCountList(String table, String aggregateAttri, String groupAttri);

    @Select("Select STD(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupStdList(String table, String aggregateAttri, String groupAttri);

    @Select("Select VAR(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupSqrtList(String table, String aggregateAttri, String groupAttri);

    @Select("Select MEDIAN(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupMedianList(String table, String aggregateAttri, String groupAttri);

    @Select("Select MODE(${aggregateAttri}) AS ${aggregateAttri} FROM ${table} GROUP BY ${groupAttri}")
    List<Map<String, Object>> queryGroupModeList(String table, String aggregateAttri, String groupAttri);
}
