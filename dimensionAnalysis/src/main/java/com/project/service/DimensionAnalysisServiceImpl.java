package com.project.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.DimensionAnalysis;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class DimensionAnalysisServiceImpl extends ServiceImpl<DimensionAnalysis, Object> implements  DimensionAnalysisService {


    @Override
    public List<LinkedHashMap<String, Object>> getList() {
        List<LinkedHashMap<String, Object>> res;
        res = baseMapper.getList();
        return res;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getselectList() {
        List<LinkedHashMap<String, Object>> res = new ArrayList<>();
        List<LinkedHashMap<String, Object>> edu = baseMapper.getEduList();
        LinkedHashMap<String, Object> all  = new LinkedHashMap<>();
        all.put("value", "stu_edu_level.all");
        all.put("label", "全部培养层次");
        res.add(all);
        for (LinkedHashMap<String, Object> map : edu) {
            LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
            tmp.put("value", "stu_edu_level."+map.get("stu_edu_level"));
            tmp.put("label", map.get("stu_edu_level"));
            String eduName = (String) map.get("stu_edu_level");
            List<LinkedHashMap<String, Object>> grade = new ArrayList<>();
            LinkedHashMap<String, Object> all2  = new LinkedHashMap<>();
            all2.put("value", "stu_info_grade."+eduName+".all");
            all2.put("label", "全部年级");
            grade.add(all2);
            grade.addAll(baseMapper.getGradeList(eduName));
            for (LinkedHashMap<String, Object> map1 : grade) {
                if (map1.get("stu_info_grade") != null) {
                map1.put("value", "stu_info_grade."+eduName+"."+map1.get("stu_info_grade"));
                map1.put("label", map1.get("stu_info_grade"));
                String gradeName = (String) map1.get("stu_info_grade");
                List<LinkedHashMap<String, Object>> cla =  new ArrayList<>();
                LinkedHashMap<String, Object> all3  = new LinkedHashMap<>();
                all3.put("value", "stu_class_name."+eduName+"."+gradeName+".all");
                all3.put("label", "全部班级");
                cla.add(all3);
                cla.addAll(baseMapper.getClassList(eduName,gradeName));
                for (LinkedHashMap<String, Object> map2 : cla) {
                    if (map2.get("stu_class_name") != null) {
                        map2.put("value", "stu_class_name." + eduName + "." + gradeName + "." + map2.get("stu_class_name"));
                        map2.put("label", map2.get("stu_class_name"));
                        map2.remove("stu_class_name");
                    }
                }
                map1.put("children", cla);
                map1.remove("stu_info_grade");
                }
            }
            tmp.put("children", grade);
            res.add(tmp);
        }
        return res;
    }

    @Override
    public LinkedHashMap<String, Object> createChart(Map<String,Object> map) {
        List<String>  legend = new ArrayList<>();
        LinkedHashMap<String, Object> series = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> indicator = new ArrayList<>();
        List<LinkedHashMap<String, Object>> data = new ArrayList<>();
        LinkedHashMap<String, Object> res = new LinkedHashMap<>();
        LinkedHashMap<String, Object> areaStyle = new LinkedHashMap<>();
        series.put("name","雷达图");
        series.put("type","radar");
        series.put("areaStyle",areaStyle);
        String attributes = Optional.ofNullable(map.get("attributes")).orElse("").toString();
        String dimension = Optional.ofNullable(map.get("dimension")).orElse("").toString();
        String stuNumber = Optional.ofNullable(map.get("stuNumber")).orElse("").toString();
        dimension = dimension.replaceAll("\\[","").replaceAll("]","");
        attributes = attributes.replaceAll("\\[","").replaceAll("]","");
        String[] part = attributes.split(",");

        StringBuilder resultBuilder = new StringBuilder();

        // 遍历每个单词
        for (String part_ : part) {
            String wrapped = "AVG(" + part_.trim() + ")";
            resultBuilder.append(wrapped).append(",");
        }
        String result = resultBuilder.substring(0, resultBuilder.length() - 1);
        String[] dimensionArray = dimension.split(",");
        for (String str : dimensionArray) {
            String level = str.contains(".") ? str.substring(0, str.indexOf(".")) : str;
            level = level.replaceAll(" ","");
            String val = str.contains(".") ? str.substring(str.indexOf(".") + 1) : str;
            LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
            if(level.equals("stu_edu_level")){
                if(val.contains("all")){
                    tmp.put("name","全部培养层次");
                    List<LinkedHashMap<String, Object>>  tmp1 = baseMapper.getEduChart(result,"生");
                    Collection<Object> valuesCollection = tmp1.get(0).values();
                    Object[] valuesArray = valuesCollection.toArray();
                    tmp.put("value",valuesArray);
                    legend.add(tmp.get("name").toString());
                }
                else{
                    tmp.put("name",val);
                    List<LinkedHashMap<String, Object>>  tmp1 = baseMapper.getEduChart(result,val);
                    Collection<Object> valuesCollection = tmp1.get(0).values();
                    Object[] valuesArray = valuesCollection.toArray();
                    tmp.put("value",valuesArray);
                    legend.add(tmp.get("name").toString());
                }
            }
            if(level.equals("stu_info_grade")){
                String[] parts = val.split("\\.");
                if(val.contains("all")){
                    tmp.put("name",parts[0] + "全部年级");
                    List<LinkedHashMap<String, Object>>  tmp1 = baseMapper.getGradeChart(result,parts[0],"级");
                    Collection<Object> valuesCollection = tmp1.get(0).values();
                    Object[] valuesArray = valuesCollection.toArray();
                    tmp.put("value",valuesArray);
                    legend.add(tmp.get("name").toString());
                }
                else{
                    tmp.put("name",parts[0] + parts[1]);
                    List<LinkedHashMap<String, Object>>  tmp1 = baseMapper.getGradeChart(result,parts[0],parts[1]);
                    Collection<Object> valuesCollection = tmp1.get(0).values();
                    Object[] valuesArray = valuesCollection.toArray();
                    tmp.put("value",valuesArray);
                    legend.add(tmp.get("name").toString());
                }
            }
            if(level.equals("stu_class_name")){
                String[] parts = val.split("\\.");
                if(val.contains("all")){
                    tmp.put("name",parts[0] + parts[1] + "全部班级");
                    List<LinkedHashMap<String, Object>>  tmp1 = baseMapper.getClassChart(result,parts[0],parts[1],"班");
                    Collection<Object> valuesCollection = tmp1.get(0).values();
                    Object[] valuesArray = valuesCollection.toArray();
                    tmp.put("value",valuesArray);
                    legend.add(tmp.get("name").toString());
                }
                else{
                    tmp.put("name",parts[0] + parts[1] + parts[2]);
                    List<LinkedHashMap<String, Object>>  tmp1 = baseMapper.getClassChart(result,parts[0],parts[1],parts[2]);
                    Collection<Object> valuesCollection = tmp1.get(0).values();
                    Object[] valuesArray = valuesCollection.toArray();
                    tmp.put("value",valuesArray);
                    legend.add(tmp.get("name").toString());
                }
            }
            data.add(tmp);
        }
        LinkedHashMap<String, Object> tmp_ = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>>  tmp1 = baseMapper.getStuChart(result,stuNumber);
        Collection<Object> valuesCollection = tmp1.get(0).values();
        Object[] valuesArray = valuesCollection.toArray();
        tmp_.put("name","学生");
        tmp_.put("value",valuesArray);
        data.add(tmp_);
        series.put("data",data);
        res.put("series",series);
        legend.add(tmp_.get("name").toString());
        for (String str : part) {
            LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
            String trans = baseMapper.getTrans(str.trim()).get(0).get("translation").toString();
            tmp.put("name",trans);
            if(trans.contains("绩点")){
                tmp.put("max",5.0);
            }
            else{
                Float max = (Float) baseMapper.getMax(str.trim()).get(0).get("max");
                tmp.put("max",max);
            }
            indicator.add(tmp);
        }
        res.put("indicator",indicator);
        res.put("legend",legend);
        return res;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getStuNumList() {
        return baseMapper.getStuNumList();
    }

    @Override
    public String getInfo(@RequestBody Map<String,Object> map) {
        String num = Optional.ofNullable(map.get("stu_number")).orElse("").toString();
        Collection<Object> res = baseMapper.getInfo(num).get(0).values();
        String str ="";
        for (Object o : res) {
            str = str.concat(o.toString());
            str = str.concat(" ");
        }

        return str;
    }
}
