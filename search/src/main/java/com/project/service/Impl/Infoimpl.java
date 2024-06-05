package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.pojo.AttributeTranslation;
import com.project.mapper.InfoMapper;
import com.project.service.IService.IService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class Infoimpl extends ServiceImpl<InfoMapper, Object> implements IService {

    public List<AttributeTranslation> selectAttributeTranslations(String table){
        return baseMapper.selectAttributeTranslations(table);
    }

    public  String configerSQL(String table,String[] attributes){
        List<AttributeTranslation> attributeTranslations = selectAttributeTranslations(table);
        StringBuilder selectClause = new StringBuilder();
        Map<String, String> attributeMap = new HashMap<>();
        for (AttributeTranslation translation : attributeTranslations) {
            attributeMap.put(translation.getAttribute(), translation.getTranslation());
        }
        if(attributes.length != 0) {
            for (String attribute : attributes) {
                selectClause.append(attribute)
//                        .append(" AS ").append(attributeMap.get(attribute))
                        .append(", ");
                //System.out.println(attributeMap.get(attribute));
            }
        }
        else{
            for (AttributeTranslation translation : attributeTranslations){
                selectClause.append(translation.getAttribute())
//                        .append(" AS ").append(translation.getTranslation())
                        .append(", ");
                //System.out.println(translation.getAttribute());
            }
        }

        selectClause.setLength(selectClause.length() - 2);
        return selectClause.toString();
    }

    public  List<LinkedHashMap<String, String>> queryListMapping(String table,String[] attributes){
        List<AttributeTranslation> attributeTranslations = selectAttributeTranslations(table);
        List<LinkedHashMap<String, String>> res=new ArrayList<>();
        Map<String, String> attributeMap = new HashMap<>();
        for (AttributeTranslation translation : attributeTranslations) {
            attributeMap.put(translation.getAttribute(), translation.getTranslation());
        }
        if(attributes.length != 0) {
            for (String attribute : attributes) {
                LinkedHashMap<String, String> tem = new LinkedHashMap<>();
                tem.put("attribute", attribute);
                tem.put("translation", attributeMap.get(attribute));
                res.add(tem);
            }
        }
        else{
            for (AttributeTranslation translation : attributeTranslations){
                LinkedHashMap<String, String> tem = new LinkedHashMap<>();
                tem.put("attribute", translation.getAttribute());
                tem.put("translation", translation.getTranslation());
                res.add(tem);
            }
        }

        return res;
    }

    public String ArraysToString(Object arr){
        return arr.toString().replaceAll("\\[|]|\\s", "");
    }
    public String[]  JsonToArrays(String str){
        JSONArray jsonArray = new JSONArray(str);
        String[] stringArray = new String[jsonArray.length()];

        // 遍历JSONArray并将每个元素转换为String
        for (int i = 0; i < jsonArray.length(); i++) {
            stringArray[i] = jsonArray.getString(i);
        }
        return stringArray;
    }


    @Override
    public List<LinkedHashMap<String, Object>> queryList(Map<String, Object> map){
        String table = map.get("table").toString();
        String attribute = map.get("attribute").toString();
        String value = Optional.ofNullable(map.get("value")).orElse("").toString();
        String order = Optional.ofNullable(map.get("order")).orElse("").toString();
        String desc = Optional.ofNullable(map.get("desc")).orElse("").toString();
        String startValue = Optional.ofNullable(map.get("start")).orElse("").toString();
        int start;
        String countValue = Optional.ofNullable(map.get("count")).orElse("").toString();
        int count;
        String type = Optional.ofNullable(map.get("type")).orElse("").toString();
        String attris = Optional.ofNullable(map.get("attributes")).orElse("").toString();
        String[] attributes = JsonToArrays(attris);
        String select = configerSQL(table, attributes);
        try {
            if (countValue != null && !countValue.isEmpty()&&Integer.parseInt(countValue)!=0)
                count = Integer.parseInt(countValue);
            else
                count = 1000;
            if (startValue != null && !startValue.isEmpty()) {
                start = Integer.parseInt(startValue);
            } else {
                start = 0;
            }
            return baseMapper.queryListByAttribute(table, attribute, value, select, order, desc, start, count,type);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<LinkedHashMap<String, String>> queryListMapping(Map<String, Object> map) {
        String table = map.get("table").toString();
        String attris = Optional.ofNullable(map.get("attributes")).orElse("").toString();
        String[] attributes = JsonToArrays(attris);
        try {
            return queryListMapping(table, attributes);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryRangeList(Map<String, Object> map){

        String table = map.get("table").toString();
        String attribute = map.get("attribute").toString();
        String begin = Optional.ofNullable(map.get("begin")).orElse("").toString();
        String end = Optional.ofNullable(map.get("end")).orElse("").toString();
        String order = Optional.ofNullable(map.get("order")).orElse("").toString();
        String desc = Optional.ofNullable(map.get("desc")).orElse("").toString();
        String startValue = Optional.ofNullable(map.get("start")).orElse("").toString();
        int start;
        String countValue = Optional.ofNullable(map.get("count")).orElse("").toString();
        int count;
        String attris = Optional.ofNullable(map.get("attributes")).orElse("").toString();
        String[] attributes = JsonToArrays(attris);
        String select = configerSQL(table, attributes);
        try {
            if (countValue != null && !countValue.isEmpty())
                count = Integer.parseInt(countValue);
            else
                count = 100;
            if (startValue != null && !startValue.isEmpty()) {
                start = Integer.parseInt(startValue);
            } else {
                start = 0;
            }
            return baseMapper.queryRangeList(table, attribute, begin, end, select, order, desc, start, count);
        } catch (NumberFormatException e) {
            return null;
        }
    }



    @Override
    public List<Map<String, Object>>  queryAll(String table){
        return baseMapper.queryAll(table);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getTableAttribute(Map<String, Object> map) {
        if(map.get("table") != null) {
            return baseMapper.queryAllAttribute(map.get("table"));
        }
        return null;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryConnectListMapping(Map<String, Object> map) {
        String table1 = Optional.ofNullable(map.get("table1")).orElse("").toString();
        String table2 = Optional.ofNullable(map.get("table2")).orElse("").toString();
        List<LinkedHashMap<String, Object>>  t1 = baseMapper.queryAllAttribute(table1);
        List<LinkedHashMap<String, Object>> t2 = baseMapper.queryAllAttribute(table2);
        for (LinkedHashMap<String, Object> item : t1) {
            if (item.containsKey("attribute")) {
                item.put("attribute", table1 + "." + item.get("attribute"));
            }
            if (item.containsKey("translation")) {
                item.put("translation", "表1." + item.get("translation"));
            }
        }
        for (LinkedHashMap<String, Object> item : t2) {
            if (item.containsKey("attribute")) {
                item.put("attribute", table2 + "." + item.get("attribute"));
            }
            if (item.containsKey("translation")) {
                item.put("translation", "表2." + item.get("translation"));
            }
        }
        return  Stream.concat(t1.stream(), t2.stream())
                .collect(Collectors.toList());
    }


    @Override
    public List<LinkedHashMap<String, Object>> queryConnectList(Map<String, Object> map_){
        String table1 = Optional.ofNullable(map_.get("table1")).orElse("").toString();
        String table2 = Optional.ofNullable(map_.get("table2")).orElse("").toString();
        String attribute1 = Optional.ofNullable(map_.get("attribute1")).orElse("").toString();
        String attribute2 = Optional.ofNullable(map_.get("attribute2")).orElse("").toString();
        String compareType = Optional.ofNullable(map_.get("compareType")).orElse("").toString();
        String attris = Optional.ofNullable(map_.get("attributes")).orElse("").toString();
        String attributes = ArraysToString(attris);
        String order = Optional.ofNullable(map_.get("order")).orElse("").toString();
        String desc = Optional.ofNullable(map_.get("desc")).orElse("").toString();
        String startValue = Optional.ofNullable(map_.get("start")).orElse("").toString();
        int start;
        String countValue = Optional.ofNullable(map_.get("count")).orElse("").toString();
        int count;
        try {
            if (countValue != null && !countValue.isEmpty())
                count = Integer.parseInt(countValue);
            else
                count = 100;
            if (startValue != null && !startValue.isEmpty()) {
                start = Integer.parseInt(startValue);
            } else {
                start = 0;
            }
            if (attributes.isEmpty()) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("table1", table1);
                tmp.put("table2", table2);
                attributes = queryConnectListMapping(tmp)
                        .stream()
                        .map(map -> map.get("attribute")) // 获取每个映射中的attribute值
                        .filter(Objects::nonNull) // 过滤掉null值
                        .map(Object::toString) // 将对象转换为字符串
                        .collect(Collectors.joining(", "));
            }
            return baseMapper.queryConnectList(table1, table2, attribute1, attribute2, compareType, attributes, order, desc, start, count);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



    public List<Map<String, Object>> getTableName(){
        return baseMapper.queryTableName();
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryConnectAttributeMapping(Map<String, Object> map_) {
        String table1 = Optional.ofNullable(map_.get("table1")).orElse("").toString();
        String table2 = Optional.ofNullable(map_.get("table2")).orElse("").toString();
        String attris = Optional.ofNullable(map_.get("attributes")).orElse("").toString();
        String attributes = ArraysToString(attris);
        List<LinkedHashMap<String, Object>> res = new ArrayList<>();
        try {
            if (!attributes.isEmpty()) {
                List<Map.Entry<String, String>> resultList = new ArrayList<>();
                String[] parts = attributes.split(",");
                for (String part : parts) {
                    String[] keyValue = part.split("\\.", 2); // 使用正则表达式限制分割次数为2
                    if (keyValue.length == 2) {
                        resultList.add(new AbstractMap.SimpleEntry<>(keyValue[0], keyValue[1]));
                    }
                }
                for (Map.Entry<String, String> entry : resultList) {
                    LinkedHashMap<String, Object> tem = baseMapper.queryAttribute(entry.getKey(), entry.getValue()).get(0);
                    if (table1.equals(entry.getKey())) {
                        String translationValue = (String) tem.get("translation");
                        tem.put("translation", "表1." + translationValue);
                    } else if (table2.equals(entry.getKey())) {
                        String translationValue = (String) tem.get("translation");
                        tem.put("translation", "表2." + translationValue);
                    }
                    else{
                        System.out.println("error");
                    }
                    res.add(tem);
                }
            }
            else{
                List<LinkedHashMap<String, Object>> t1 = baseMapper.queryAllAttribute(table1);
                for (LinkedHashMap<String, Object> item : t1) {
                    item.put("translation", "表1." + item.get("translation"));
                }
                List<LinkedHashMap<String, Object>> t2 = baseMapper.queryAllAttribute(table2);
                for (LinkedHashMap<String, Object> item : t2) {
                    item.put("translation", "表2." + item.get("translation"));
                }
                res.addAll(t1);
                res.addAll(t2);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;

    }

    @Override
    public List<Map<String, Object>> getGroupSearchedList(String table,String aggregateAttri,String groupAttri,String aggregateType){
        switch (aggregateType) {
            case "求和" -> {
                return baseMapper.queryGroupSumList(table, aggregateAttri, groupAttri);
            }
            case "平均值" -> {
                return baseMapper.queryGroupAvgList(table, aggregateAttri, groupAttri);
            }
            case "最小值" -> {
                return baseMapper.queryGroupMinList(table, aggregateAttri, groupAttri);
            }
            case "最大值" -> {
                return baseMapper.queryGroupMaxList(table, aggregateAttri, groupAttri);
            }
            case "计数" -> {
                return baseMapper.queryGroupCountList(table, aggregateAttri, groupAttri);
            }
            case "标准差" -> {
                return baseMapper.queryGroupStdList(table, aggregateAttri, groupAttri);
            }
            case "方差" -> {
                return baseMapper.queryGroupSqrtList(table, aggregateAttri, groupAttri);
            }
            case "中位数" -> {
                return baseMapper.queryGroupMedianList(table, aggregateAttri, groupAttri);
            }
            case "众数" -> {
                return baseMapper.queryGroupModeList(table, aggregateAttri, groupAttri);
            }
        }
        return null;
    }
}
