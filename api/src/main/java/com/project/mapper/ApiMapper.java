package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ApiMapper extends BaseMapper<Object> {
    public void apiCreate(@Param("name") String name, @Param("info") String info, @Param("privacy") String privacy, @Param("command") String command, @Param("flowControl") String flowControl,@Param("url") String url);

    @Select("select api_url from api where api_privacy = #{privacy} order by api_id desc limit 1")
    public Map<String,Object> getUrl(@Param("privacy")String privacy);

    @Select("${sql}")
    public List<Map<String,Object>> apiCheck(@Param("sql") String sql);
}
