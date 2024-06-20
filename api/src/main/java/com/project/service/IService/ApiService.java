package com.project.service.IService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.ApiMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ApiService{
    LinkedHashMap<String, Object> apiCreate(Map<String, Object> map);

    boolean apiCheckSQL(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> Visit(String privateKey, int num,String ip);

    LinkedHashMap<String, Object> apiCheckName(Map<String, Object> map);


    LinkedHashMap<String, Object> GetAPIInfo();

    List<LinkedHashMap<String, Object>> getAPISelectedInfo(Map<String, Object> map);

    boolean deleteAPI(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> getNameList(Map<String, Object> map);

    LinkedHashMap<String, Object> getConcreteChartsInfo(Map<String, Object> map);

    LinkedHashMap<String, Object> getConcreteInfo(Map<String, Object> map);

    boolean open(Map<String, Object> map);

    boolean close(Map<String, Object> map);
}
