package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.DbManageMapper;
import com.project.service.IService.DbManageService;

import java.util.*;


public class DbManageServiceImpl extends ServiceImpl<DbManageMapper, Object> implements DbManageService {
    public boolean createTable(Map<String, Object> map){
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        String translation = Optional.ofNullable(map.get("tableNameTranslation")).orElse("").toString();
        try {
            baseMapper.createTable(tableName,translation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
