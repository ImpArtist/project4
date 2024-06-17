package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ApiMapper extends BaseMapper<Object> {
    public void apiCreate(@Param("name") String name, @Param("info") String info, @Param("privacy") String privacy, @Param("command") String command, @Param("flowControl") String flowControl,@Param("business") String business,@Param("url") String url);

    @Select("select api_url from api where api_privacy = #{business} order by api_id desc limit 1")
    public Map<String,Object> getUrl(@Param("business")String business);

    @Select("${sql}")
    public List<Map<String,Object>> apiCheck(@Param("sql") String sql);

    @Select("select api_command from api where api_url = #{url}")
    List<LinkedHashMap<String, Object>> Visit(@Param("url") String url);

    @Select("${sql}")
    List<LinkedHashMap<String, Object>> GetInfo(@Param("sql") String sql);

    @Select("select api_id from api where api_name = #{name}")
    List<LinkedHashMap<String, Object>> apiCheckName(String name);

    @Select("select api_id,api_name,api_info,api_privacy,api_command,api_flow_control,api_business,api_url from api where api_url = #{url}")
    List<LinkedHashMap<String, Object>> GetAPIInfo();
}
