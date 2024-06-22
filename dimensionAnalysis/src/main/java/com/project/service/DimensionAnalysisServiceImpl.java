package com.project.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.DimensionAnalysis;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DimensionAnalysisServiceImpl extends ServiceImpl<DimensionAnalysis, Object> implements  DimensionAnalysisService {


    @Override
    public List<LinkedHashMap<String, Object>> getList() {
        return baseMapper.getList();
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
            all2.put("value", "stu_info_grade.all");
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
                all3.put("value", "stu_class_name.all");
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
}
