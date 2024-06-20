package com.project.service.IService;

import com.project.domain.pojo.AttributeTranslation;
import com.project.domain.pojo.StuInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IService {

    List<LinkedHashMap<String, Object>> queryList(Map<String, Object> map);

    List<LinkedHashMap<String, String>> queryListMapping(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> queryRangeList(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> queryConnectList(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> queryConnectListMapping(Map<String, Object> map);


    List<Map<String, Object>> queryAll(String table);

    List<LinkedHashMap<String, Object>> getTableAttribute(Map<String, Object> table);


    List<LinkedHashMap<String, Object>> queryGroupList(Map<String, Object> map);

    List<Map<String, Object>> getTableName();

    List<LinkedHashMap<String, Object>> queryConnectAttributeMapping(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> queryGroupListMapping(Map<String, Object> map);

    LinkedHashMap<String, Object> analyseBarchart(Map<String, Object> map);

    LinkedHashMap<String, Object> analyseLineChart(Map<String, Object> map);

    LinkedHashMap<String, Object> analysePieChart(Map<String, Object> map);

    LinkedHashMap<String, Object> analyseData(Map<String, Object> map);

    String analyseGetClass(Map<String, Object> map);
}
