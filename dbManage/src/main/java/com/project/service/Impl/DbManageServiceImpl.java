package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.DbManageMapper;
import com.project.service.IService.DbManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class DbManageServiceImpl extends ServiceImpl<DbManageMapper, Object> implements DbManageService {
    public boolean createTable(Map<String, Object> map){
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        String translation = Optional.ofNullable(map.get("tableNameTranslation")).orElse("").toString();
        String attributeDate = tableName + "_id";
        try {
            baseMapper.createTable(tableName);
            baseMapper.createTableMapping(tableName,translation);
            baseMapper.initTable(tableName,translation,attributeDate);
            baseMapper.initTable2(tableName,translation);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            tmp.put("dataBase","Mysql");
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

    @Override
    public List<String> getFieldList(Map<String, Object> map) {
        List<LinkedHashMap<String, Object>> res = baseMapper.getTableStruct(Optional.ofNullable(map.get("tableName")).orElse("").toString());
        List<String> res_ = new ArrayList<>();
        for(LinkedHashMap<String, Object> map_:res){
            res_.add(map_.get("Field").toString());
        }
        return res_;
    }

    @Override
    public boolean deleteTable(Map<String, Object> map) {
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        try{
            baseMapper.deleteTable(tableName);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    @Override
    public boolean deleteRecord(Map<String, Object> map) {
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        String attribute = Optional.ofNullable(map.get("attribute")).orElse("").toString();
        String value = Optional.ofNullable(map.get("value")).orElse("").toString();
        try{
            baseMapper.deleteRecord(tableName,attribute,value);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateStructTable(Map<String, Object> map) {
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();

        try{
            List<LinkedHashMap<String,Object>> tmp = new ArrayList<>();
            List<LinkedHashMap<String,Object>> data = (List<LinkedHashMap<String, Object>>) Optional.ofNullable(map.get("data")).orElse(tmp);
            List<LinkedHashMap<String,Object>> data2 = (List<LinkedHashMap<String, Object>>)getTableStruct(map).get("data");
            int count=0;
            for(LinkedHashMap<String,Object> map_:data){
                System.out.println(map_);
                LinkedHashMap<String,Object> map2 = data2.get(count++);
                String Field = Optional.ofNullable(map_.get("Field")).orElse("").toString();
                String Type = Optional.ofNullable(map_.get("Type")).orElse("").toString();
                String Null = Optional.ofNullable(map_.get("Null")).orElse("").toString();
                String Key = Optional.ofNullable(map_.get("Key")).orElse("").toString();
                String Default = Optional.ofNullable(map_.get("Default")).orElse("").toString();
                String Extra = Optional.ofNullable(map_.get("Extra")).orElse("").toString();
                if(!Extra.isEmpty()&&!Key.equals("PRI"))
                    return false;
                baseMapper.updateStructModify(tableName,Field,Type,Null);
                if(map2.get("Key").equals("PRI")&&!Key.equals("PRI"))
                    baseMapper.updateStructKey(tableName,Field,Key);
                baseMapper.updateStructDefault(tableName,Field,Default);
                baseMapper.truncateTable(tableName+"_attribute");
                baseMapper.updateAttribute(tableName+"_attribute",Field,Type,count);
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean insertRecord(Map<String, Object> map) {
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        Map<String,Object> record = (Map<String, Object>) Optional.ofNullable(map.get("record")).orElse("");

        try{
                baseMapper.insertRecord(tableName, record);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteField(Map<String, Object> map) {
        String tableName = Optional.ofNullable(map.get("tableName")).orElse("").toString();
        String Field = Optional.ofNullable(map.get("Field")).orElse("").toString();
        try{
            baseMapper.deleteField(tableName,Field);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
