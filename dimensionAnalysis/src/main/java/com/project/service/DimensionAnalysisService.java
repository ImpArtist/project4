package com.project.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;


public interface DimensionAnalysisService {
    List<LinkedHashMap<String, Object>> getList();
}
