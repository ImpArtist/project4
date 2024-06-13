package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.pojo.AttributeTranslation;
import com.project.mapper.InfoMapper;
import com.project.service.IService.IService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
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

    private double calculateMedian(List<Double> scores) {
        Collections.sort(scores);
        int size = scores.size();
        if (size % 2 == 0) {
            return (scores.get(size / 2 - 1) + scores.get(size / 2)) / 2.0;
        } else {
            return scores.get(size / 2);
        }
    }

    private double calculateVariance(List<Double> scores, double mean) {
        double temp = 0;
        for (double score : scores) {
            temp += (score - mean) * (score - mean);
        }
        return temp / scores.size();
    }

    private double calculateMode(List<Double> scores) {
        Map<Double, Long> frequencyMap = scores.stream()
                .collect(Collectors.groupingBy(Double::doubleValue, Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Double.NaN);
    }

    private LinkedHashMap<String, Object> createResultMap(String name, List<?> data, String type) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("data", data);
        map.put("type", type);
        map.put("smooth", "true".equals(type));
        return map;
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
    public List<LinkedHashMap<String, Object>> queryGroupListMapping(Map<String, Object> map) {
        String aggregateTypes = ArraysToString(Optional.ofNullable(map.get("aggregateTypes")).orElse("").toString());
        String groupValue = Optional.ofNullable(map.get("group")).orElse("").toString();
        String table = Optional.ofNullable(map.get("table")).orElse("").toString();
        String aggregateValue = Optional.ofNullable(map.get("aggregate")).orElse("").toString();
        try {
            String aggregate = baseMapper.queryAttribute(table, aggregateValue).get(0).get("translation").toString();
            List<LinkedHashMap<String, Object>> res = new ArrayList<>();
            String group = baseMapper.queryAttribute(table, groupValue).get(0).get("translation").toString();
            LinkedHashMap<String, Object> first = new LinkedHashMap<>();
            first.put("translation", group);
            first.put("attribute", groupValue);
            res.add(first);
            String[] functions = aggregateTypes.split(",");
            for (String func : functions) {
                LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
                switch (func) {
                    case "AVG":
                        tmp.put("translation", aggregate + "平均值");
                        tmp.put("attribute", "AVG(" +  aggregateValue + ")");
                        break;
                    case "SUM":
                        tmp.put("translation", aggregate + "求和值");
                        tmp.put("attribute", "SUM(" +  aggregateValue + ")");
                        break;
                    case "MIN":
                        tmp.put("translation", aggregate + "最小值");
                        tmp.put("attribute", "MIN(" +  aggregateValue + ")");
                        break;
                    case "MAX":
                        tmp.put("translation", aggregate + "最大值");
                        tmp.put("attribute", "MAX(" +  aggregateValue + ")");
                        break;
                    case "STD":
                        tmp.put("translation", aggregate + "标准差");
                        tmp.put("attribute", "STD(" +  aggregateValue + ")");
                        break;
                    case "MEDIAN":
                        tmp.put("translation", aggregate + "中位数");
                        tmp.put("attribute", "MEDIAN(" +  aggregateValue + ")");
                        break;
                    case "MODE":
                        tmp.put("translation", aggregate + "众数");
                        tmp.put("attribute", "MODE(" +  aggregateValue + ")");
                        break;
                    case "VAR":
                        tmp.put("translation", aggregate + "方差");
                        tmp.put("attribute", "VAR(" +  aggregateValue + ")");
                        break;
                    case "COUNT":
                        tmp.put("translation", aggregate + "计数");
                        tmp.put("attribute", "COUNT(" +  aggregateValue + ")");
                        break;

                }
                res.add(tmp);
            }
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<LinkedHashMap<String, Object>> analyseBarchart(Map<String, Object> map) {
        Object dataObj = map.get("data");
        String groupName = Optional.ofNullable(map.get("group")).orElse("").toString();
        String aggregate = Optional.ofNullable(map.get("aggregate")).orElse("").toString();
        ObjectMapper objectMapper = new ObjectMapper();

        List<LinkedHashMap<String, Object>> result = new ArrayList<>();

        try {
            List<Map<String, Object>> students = objectMapper.convertValue(dataObj, new TypeReference<List<Map<String, Object>>>() {});

            // 排序学生列表以按班级名称分组
            students.sort(Comparator.comparing(student -> (String) student.get("stu_class_name")));

            // 按班级名称分组
            Map<String, List<Map<String, Object>>> groupedByClassName = students.stream()
                    .collect(Collectors.groupingBy(student -> (String) student.get("stu_class_name")));

            // 获取按班级名称排序的班级列表
            List<String> sortedClassNames = new ArrayList<>(groupedByClassName.keySet());
            Collections.sort(sortedClassNames);

            // 准备各个统计值的列表
            List<String> xValues = new ArrayList<>();
            List<Long> countValues = new ArrayList<>();
            List<Double> sumValues = new ArrayList<>();
            List<Double> minValues = new ArrayList<>();
            List<Double> maxValues = new ArrayList<>();
            List<Double> avgValues = new ArrayList<>();
            List<Double> stdDevValues = new ArrayList<>();
            List<Double> medianValues = new ArrayList<>();
            List<Double> varianceValues = new ArrayList<>();
            List<Double> modeValues = new ArrayList<>();

            // 遍历按排序后的班级名称
            for (String className : sortedClassNames) {
                List<Map<String, Object>> group = groupedByClassName.get(className);

                xValues.add(className);
                countValues.add((long) group.size());

                // 提取并处理分数数据
                List<Double> scores = group.stream()
                        .filter(student -> student.get("stu_id") != null)
                        .map(student -> ((Number) student.get("stu_id")).doubleValue())
                        .collect(Collectors.toList());

                double sum = scores.stream().mapToDouble(Double::doubleValue).sum();
                double min = scores.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
                double max = scores.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
                double avg = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                double median = calculateMedian(scores);
                double variance = calculateVariance(scores, avg);
                double stdDev = Math.sqrt(variance);
                double mode = calculateMode(scores);

                sumValues.add(sum);
                minValues.add(min);
                maxValues.add(max);
                avgValues.add(avg);
                medianValues.add(median);
                varianceValues.add(variance);
                stdDevValues.add(stdDev);
                modeValues.add(mode);
            }

            // 构建最终结果
            result.add(createResultMap("班级", xValues, "bar"));
            result.add(createResultMap("数量", countValues, "bar"));
            result.add(createResultMap("总和", sumValues, "bar"));
            result.add(createResultMap("最小值", minValues, "bar"));
            result.add(createResultMap("最大值", maxValues, "bar"));
            result.add(createResultMap("平均值", avgValues, "line"));
            result.add(createResultMap("标准差", stdDevValues, "line"));
            result.add(createResultMap("中位数", medianValues, "line"));
            result.add(createResultMap("方差", varianceValues, "line"));
            result.add(createResultMap("众数", modeValues, "line"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryGroupList(Map<String, Object> map){
        String table = Optional.ofNullable(map.get("table")).orElse("").toString();
        String attribute = Optional.ofNullable(map.get("attribute")).orElse("").toString();
        String value = Optional.ofNullable(map.get("value")).orElse("").toString();
        String order = Optional.ofNullable(map.get("order")).orElse("").toString();
        String group = Optional.ofNullable(map.get("group")).orElse("").toString();
        String aggregateTypes = Optional.ofNullable(map.get("aggregateTypes")).orElse("").toString();
        String aggregate = Optional.ofNullable(map.get("aggregate")).orElse("").toString();
        String desc = Optional.ofNullable(map.get("desc")).orElse("").toString();
        String startValue = Optional.ofNullable(map.get("start")).orElse("").toString();
        int start;
        String countValue = Optional.ofNullable(map.get("count")).orElse("").toString();
        int count;
        String type = Optional.ofNullable(map.get("type")).orElse("").toString();
        String[] functions = ArraysToString(aggregateTypes).split(",");
        List<String> normalList = new ArrayList<>();
        List<String> specialList = new ArrayList<>();
        for (String function : functions) {
            if (function.equals("MODE") || function.equals("MEDIAN") || function.equals("VAR"))
                specialList.add(function);
            else
                normalList.add(function + "(" + aggregate + ")");
        }
        String modifiedAggregate = "";
        if(!Objects.equals(ArraysToString(aggregateTypes), ""))
            modifiedAggregate = String.join(",", normalList);
        List<LinkedHashMap<String, Object>> res = new ArrayList<>();
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
            res = baseMapper.queryGroupList(table, attribute, value, order, desc, start, count,type,group,modifiedAggregate,aggregate);
            for(String function : specialList){
                if(function.equals("MODE")){
                    List<LinkedHashMap<String, Object>> MODERes = baseMapper.queryMODE(table,group,aggregate);
                    for(LinkedHashMap<String, Object> item : res){
                        for(LinkedHashMap<String, Object> item2 : MODERes){
                            if (item.get(group).equals(item2.get(group))) {
                                item.put("MODE(" + aggregate + ")", item2.get("mode_value"));
                                break;
                            }
                        }
                    }
                }
                if(function.equals("MEDIAN")){
                    List<LinkedHashMap<String, Object>> MEDIANRes = baseMapper.queryMEDIAN(table,group,aggregate);
                    for(LinkedHashMap<String, Object> item : res){
                        for(LinkedHashMap<String, Object> item2 : MEDIANRes){
                            if (item.get(group).equals(item2.get(group))) {
                                item.put("MEDIAN(" + aggregate + ")", item2.get("median_value"));
                                break;
                            }
                        }
                    }
                }
                if(function.equals("VAR")){
                    List<LinkedHashMap<String, Object>> VARRes = baseMapper.queryGroupList(table, attribute, value, order, desc, start, count,type,group,"STD(" + aggregate + ")",aggregate);
                    int counter = 0;
                    for(LinkedHashMap<String, Object> item : res){
                        item.put("VAR(" + aggregate + ")", Math.pow((Double)VARRes.get(counter).get("STD(" + aggregate + ")"),2));
                        counter++;
                    }
                }

            }

            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
