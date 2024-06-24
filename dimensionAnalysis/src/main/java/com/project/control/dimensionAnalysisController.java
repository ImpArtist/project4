package com.project.control;


import com.project.service.DimensionAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/selectList")
    public List<LinkedHashMap<String, Object>> getselectList(){
        return Service.getselectList();
    }

    @PostMapping("/createChart")
    public LinkedHashMap<String, Object> createChart(@RequestBody Map<String,Object> map){
        return Service.createChart(map);
    }

    @PostMapping("/getStuNumList")
    public List<LinkedHashMap<String, Object>> getStuNumList(){
        return Service.getStuNumList();
    }

    @PostMapping("/getInfo")
    public String getInfo(@RequestBody Map<String,Object> map){
        return Service.getInfo(map);
    }

    @PostMapping("/createChart2")
    public LinkedHashMap<String, Object> createChart2(@RequestBody Map<String,Object> map){
        return Service.createChart2(map);
    }

    @PostMapping("/getChartList")
    public List<LinkedHashMap<String, Object>> getChartList() {
        return Service.getChartList();
    }
}
