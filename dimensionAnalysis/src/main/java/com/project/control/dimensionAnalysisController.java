package com.project.control;


import com.project.service.DimensionAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stuAbility")
@RequiredArgsConstructor
public class dimensionAnalysisController
{

    private final DimensionAnalysisService Service;
    @PostMapping("/list")
    public List<LinkedHashMap<String, Object>> getList(){
        return Service.getList();
    }
}
