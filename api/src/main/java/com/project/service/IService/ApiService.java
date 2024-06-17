package com.project.service.IService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.ApiMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ApiService{
    LinkedHashMap<String, Object> apiCreate(Map<String, Object> map);

    boolean apiCheckSQL(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> Visit(String privateKey, int num);

    LinkedHashMap<String, Object> apiCheckName(Map<String, Object> map);


    List<LinkedHashMap<String, Object>> GetAPIInfo();
}
