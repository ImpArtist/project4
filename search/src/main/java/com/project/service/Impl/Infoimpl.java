package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.pojo.AttributeTranslation;
import com.project.mapper.InfoMapper;
import com.project.service.IService.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

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
            if (countValue != null && !countValue.isEmpty())
                count = Integer.parseInt(countValue);
            else
                count = 100;
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
        String begin = map.get("begin").toString();
        String end = map.get("end").toString();
        String order = map.get("order").toString();
        String desc = map.get("desc").toString();
        String startValue = map.get("start").toString();
        int start;
        String countValue = map.get("count").toString();
        int count;
        String[] attributes = JsonToArrays(map.get("attributes").toString());
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
    public List<Map<String, Object>> getTableAttribute(Map<String, Object> map) {
        if(map.get("table") != null) {
            return baseMapper.queryAllAttribute(map.get("table"));
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getConnectSearchedList(Map<String, Object> map){
        String attributes = ArraysToString(map.get("attributes"));
        if(attributes.isEmpty())
            return baseMapper.queryConnectSearchedList(map.get("table1").toString(),map.get("table2").toString(),map.get("attribute1").toString(),map.get("attribute2").toString(),map.get("compareType").toString(), "*");
        return baseMapper.queryConnectSearchedList(map.get("table1").toString(),map.get("table2").toString(),map.get("attribute1").toString(),map.get("attribute2").toString(),map.get("compareType").toString(),attributes);
    }

    public List<Map<String, Object>> getTableName(){
        return baseMapper.queryTableName();
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
