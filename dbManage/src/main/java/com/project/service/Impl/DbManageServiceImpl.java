package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.DbManageMapper;
import com.project.service.IService.DbManageService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
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

    @Override
    public Object[] getTables(Map<String, Object> map_) {
        List<LinkedHashMap<String, Object>> names = baseMapper.getTables();
        List<String> valuesList = new ArrayList<>();
        for (LinkedHashMap<String, Object> map : names) {
            valuesList.add(map.get("Tables_in_project").toString());
        }

        return valuesList.toArray();
    }
}
