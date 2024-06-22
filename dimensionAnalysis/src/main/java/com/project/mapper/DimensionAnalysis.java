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
}
