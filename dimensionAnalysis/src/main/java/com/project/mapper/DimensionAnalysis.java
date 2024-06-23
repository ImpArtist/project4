package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;

public interface DimensionAnalysis extends BaseMapper<Object> {

    @Select("select attribute,translation from stu_info_attribute where privilege between 21 and 34 order by privilege")
    List<LinkedHashMap<String, Object>> getList();

    @Select("SELECT stu_edu_level FROM project.stu_info group by stu_edu_level")
    List<LinkedHashMap<String, Object>> getEduList();

    @Select("SELECT stu_info_grade FROM project.stu_info where stu_edu_level = #{eduName} group by stu_info_grade order by stu_info_grade")
    List<LinkedHashMap<String, Object>> getGradeList(@Param("eduName") String eduName);

    @Select("SELECT stu_class_name FROM project.stu_info where stu_edu_level = #{eduName} and stu_info_grade = #{gradeName} group by stu_class_name order by stu_class_name")
    List<LinkedHashMap<String, Object>> getClassList(@Param("eduName") String eduName, @Param("gradeName") String gradeName);

    @Select("SELECT ${attributes} FROM project.stu_info where stu_edu_level LIKE CONCAT('%',#{value},'%')")
    List<LinkedHashMap<String, Object>> getEduChart(@Param("attributes") String attributes, @Param("value") String value);

    @Select("SELECT ${attributes} FROM project.stu_info where stu_edu_level LIKE CONCAT('%',#{value1},'%') and stu_info_grade LIKE CONCAT('%',#{value2},'%')")
    List<LinkedHashMap<String, Object>> getGradeChart(@Param("attributes") String attributes, @Param("value1") String value1, @Param("value2") String value2);

    @Select("SELECT ${attributes} FROM project.stu_info where stu_edu_level LIKE CONCAT('%',#{value1},'%') and stu_info_grade LIKE CONCAT('%',#{value2},'%') and stu_class_name LIKE CONCAT('%',#{value3},'%')")
    List<LinkedHashMap<String, Object>> getClassChart(@Param("attributes") String attributes, @Param("value1") String value1, @Param("value2") String value2, @Param("value3") String value3);

    @Select("SELECT translation FROM project.stu_info_attribute where attribute = #{trim}")
    List<LinkedHashMap<String, Object>> getTrans(@Param("trim") String trim);

    @Select("SELECT MAX(${trim}) AS max FROM project.stu_info")
    List<LinkedHashMap<String, Object>> getMax(String trim);

    @Select("SELECT ${attributes} FROM project.stu_info where stu_number = #{stuNumber}")
    List<LinkedHashMap<String, Object>> getStuChart(@Param("attributes") String attributes, @Param("stuNumber") String stuNumber);

    @Select("SELECT stu_number FROM project.stu_info")
    List<LinkedHashMap<String, Object>> getStuNumList();

    @Select("SELECT stu_name,stu_edu_level,stu_info_grade,stu_class_name FROM project.stu_info where stu_number = #{num}")
    List<LinkedHashMap<String, Object>> getInfo(String num);
}
