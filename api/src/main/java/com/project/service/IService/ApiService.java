package com.project.service.IService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.ApiMapper;

import java.util.Map;

public interface ApiService{
    boolean apiCreate(Map<String, Object> map);

    boolean apiCheck(Map<String, Object> map);
}
