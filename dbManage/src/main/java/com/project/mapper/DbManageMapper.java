package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface DbManageMapper extends BaseMapper<Object> {

    void createTable(@Param("tableName")String tableName, @Param("translation")String translation);
}
