package com.project.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.DimensionAnalysis;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DimensionAnalysisServiceImpl extends ServiceImpl<DimensionAnalysis, Object> implements  DimensionAnalysisService {


    @Override
    public List<LinkedHashMap<String, Object>> getList() {
        return baseMapper.getList();
    }
}
