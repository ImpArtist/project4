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

    @Select("select api_id,api_name,api_url,api_info,api_command,api_business from api")
    List<LinkedHashMap<String, Object>> GetAPIInfo();

    @Select("select attribute,translation from api_attribute order by privilege limit 6")
    List<LinkedHashMap<String, Object>> GetTransInfo();

    List<LinkedHashMap<String, Object>> getAPISelectedInfo(@Param("attribute") String attribute,@Param("value") String value,@Param("type") String type);

    @Select("DELETE FROM api WHERE api_name = #{name}")
    void deleteAPI(@Param("name") String name);

    @Select("select api_name from api where api_name like CONCAT('%', #{value}, '%') order by api_id")
    List<LinkedHashMap<String, Object>> getNameList(String value);

    @Select("SELECT api_name from api where api_url = #{url}")
    List<LinkedHashMap<String, Object>> getAPIName(String url);

    @Select("UPDATE api SET api_view_count = api_view_count + 1 WHERE api_url = #{url}")
    void updateAPI(@Param("url") String url);

    @Select("INSERT INTO api_record (api_record_name,api_record_ip,api_record_time) VALUES (#{name},#{ip},#{time}) ")
    void updateAPIRecord(@Param("name") String name,@Param("ip") String ip,@Param("time") String time);
}
