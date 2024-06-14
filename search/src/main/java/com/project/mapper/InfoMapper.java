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

    List<LinkedHashMap<String, Object>> queryListByAttribute(@Param("table") String table, @Param("attribute") String attribute, @Param("value") String value, @Param("attributes") String attributes, @Param("order") String order, @Param("desc") String desc, @Param("start") Integer start, @Param("count") Integer count,@Param("type") String type);


    List<LinkedHashMap<String, Object>> queryRangeList(@Param("table") String table,@Param("attribute") String attribute, @Param("begin") String begin, @Param("end") String end,@Param("attributes") String attributes,@Param("order") String order, @Param("desc") String desc, @Param("start") Integer start, @Param("count") Integer count);

    List<LinkedHashMap<String, Object>> queryConnectList(@Param("table1") String table1,@Param("table2") String table2,@Param("attribute1") String attribute1,@Param("attribute2") String attribute2,@Param("compareType") String compareType,@Param("attributes") String attributes,@Param("order") String order, @Param("desc") String desc, @Param("start") Integer start, @Param("count") Integer count);
    @Select("Select * FROM ${table}_attribute order by privilege")
    List<LinkedHashMap<String, Object>> queryAllAttribute(@Param("table") Object table);

    @Select("Select attribute,translation,class_name FROM ${table}_attribute where attribute = #{attribute}")
    List<LinkedHashMap<String, Object>> queryAttribute(@Param("table") Object table,@Param("attribute") String attribute);

    @Select("Select * FROM ${table}")
    List<Map<String, Object>>  queryAll(String table);

    @Select("Select * FROM table_info")
    List<Map<String, Object>> queryTableName();

    List<LinkedHashMap<String, Object>> queryMODE(@Param("table") String table,@Param("group") String group,@Param("aggregate")  String aggregate);

    List<LinkedHashMap<String, Object>> queryGroupList(@Param("table") String table, @Param("attribute") String attribute, @Param("value") String value,  @Param("order") String order, @Param("desc") String desc, @Param("start") Integer start, @Param("count") Integer count,@Param("type") String type, @Param("group") String group, @Param("aggregateType") String aggregateType,@Param("aggregate")  String aggregate);

    List<LinkedHashMap<String, Object>> queryMEDIAN(@Param("table") String table,@Param("group") String group,@Param("aggregate")  String aggregate);
}
