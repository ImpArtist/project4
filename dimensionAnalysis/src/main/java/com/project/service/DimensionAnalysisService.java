package com.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public interface DimensionAnalysisService {
    List<LinkedHashMap<String, Object>> getList();

    List<LinkedHashMap<String, Object>> getselectList();

    LinkedHashMap<String, Object> createChart(Map<String,Object> map);

    List<LinkedHashMap<String, Object>> getStuNumList();

    String getInfo(@RequestBody Map<String,Object> map);

    LinkedHashMap<String, Object> createChart2(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> getChartList();
}
