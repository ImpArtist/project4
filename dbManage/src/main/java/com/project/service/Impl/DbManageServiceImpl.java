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
    public List<LinkedHashMap<String, Object>> getTables(Map<String, Object> map_) {
        List<LinkedHashMap<String, Object>> names = baseMapper.getTables();
        List<String> valuesList = new ArrayList<>();
        List<LinkedHashMap<String, Object>> res = new ArrayList<>();
        for (LinkedHashMap<String, Object> map : names) {
            valuesList.add(map.get("Tables_in_project").toString());
        }
        for(String s:valuesList){
            LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
            tmp.put("tableName",s);
            tmp.put("dataBase","数据库类型:Mysql");
            tmp.put("createTime",baseMapper.getTableCreateTime(s).get(0).get("CREATE_TIME").toString());
            res.add(tmp);
        }

        return res;
    }

    @Override
    public LinkedHashMap<String, Object> getTableData(Map<String, Object> map) {
        LinkedHashMap<String, Object> res = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> mapping;
        List<LinkedHashMap<String, Object>> data;
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        String attribute = Optional.ofNullable(map.get("attribute")).orElse("").toString();
        String value = Optional.ofNullable(map.get("value")).orElse("").toString();
        data = baseMapper.getTableData(tableName, attribute, value);
        mapping = baseMapper.getTableStruct(tableName);
        for(LinkedHashMap<String, Object> map_:mapping){
            map_.remove("Type");
            map_.remove("Null");
            map_.remove("Key");
            map_.remove("Default");
            map_.remove("Extra");
            map_.put("attribute",map_.get("Field"));
            map_.remove("Field");
        }
        for (LinkedHashMap<String, Object> map1 : mapping) {
            map1.put("translation",map1.get("attribute"));
        }
        res.put("mapping", mapping);
        res.put("data", data);
        return res;
    }

    @Override
    public LinkedHashMap<String, Object> getTableStruct(Map<String, Object> map) {
        LinkedHashMap<String, Object> res = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> mapping = new ArrayList<>();
        List<LinkedHashMap<String, Object>> data;
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        data = baseMapper.getTableStruct(tableName);
        LinkedHashMap<String, Object> tmp1 = new LinkedHashMap<>();
        LinkedHashMap<String, Object> tmp2 = new LinkedHashMap<>();
        LinkedHashMap<String, Object> tmp3 = new LinkedHashMap<>();
        LinkedHashMap<String, Object> tmp4 = new LinkedHashMap<>();
        LinkedHashMap<String, Object> tmp5 = new LinkedHashMap<>();
        LinkedHashMap<String, Object> tmp6 = new LinkedHashMap<>();
        tmp1.put("attribute","Field");
        tmp1.put("translation","列名");
        mapping.add(tmp1);
        tmp2.put("attribute","Type");
        tmp2.put("translation","数据类型");
        mapping.add(tmp2);
        tmp3.put("attribute","Null");
        tmp3.put("translation","允许空值");
        mapping.add(tmp3);
        tmp4.put("attribute","Key");
        tmp4.put("translation","键属性");
        mapping.add(tmp4);
        tmp5.put("attribute","Default");
        tmp5.put("translation","默认值");
        mapping.add(tmp5);
        tmp6.put("attribute","Extra");
        tmp6.put("translation","其他设置");
        mapping.add(tmp6);
        res.put("data", data);
        res.put("mapping",mapping);
        return res;
    }
}
