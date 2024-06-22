package com.project.control;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import com.project.service.IService.IService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final IService Service;
    private static final Logger log = (Logger) LoggerFactory.getLogger(SearchController.class);

    //返回表的所有记录
    @GetMapping("{table}")
    public List<Map<String, Object>> getList(@PathVariable("table") String table){
        return Service.queryAll(table);
    }


    //统一的常规搜索
    @PostMapping("normal")
    public List<LinkedHashMap<String, Object>>queryList(@RequestBody Map<String, Object> map){
        return Service.queryList(map);
    }

    @PostMapping("mapping")
    public List<LinkedHashMap<String, String>>queryListMapping(@RequestBody Map<String, Object> map){
        return Service.queryListMapping(map);
    }



    //根据属性范围查找
    @PostMapping("/range")
    public List<LinkedHashMap<String, Object>> getListByRange(@RequestBody Map<String, Object> map){
        return Service.queryRangeList(map);
    }


    //根据表名获取属性名
    @PostMapping("/table/attribute")
    public List<LinkedHashMap<String, Object>> getTableAttribute(@RequestBody Map<String, Object> map){
        return Service.getTableAttribute(map);
    }

    //获得所有表名
    @GetMapping("/table")
    public List<Map<String, Object>> getTableName(){
        return Service.getTableName();
    }

    //关联搜索
    @PostMapping("/connect")
    public List<LinkedHashMap<String, Object>> queryConnectList(@RequestBody Map<String, Object> map){
        return Service.queryConnectList(map);
    }

    @PostMapping("/connect/mapping")
    public List<LinkedHashMap<String, Object>> queryConnectListMapping(@RequestBody Map<String, Object> map){
        return Service.queryConnectListMapping(map);
    }

    @PostMapping("/connect/attribute/mapping")
    public List<LinkedHashMap<String, Object>> queryConnectAttributeMapping(@RequestBody Map<String, Object> map){
        return Service.queryConnectAttributeMapping(map);
    }

    //分组搜索
    @PostMapping("/group")
    public List<LinkedHashMap<String, Object>> queryGroupList(@RequestBody Map<String, Object> map) {
        return Service.queryGroupList(map);
    }

    @PostMapping("/group/mapping")
    public List<LinkedHashMap<String, Object>> queryGroupListMapping(@RequestBody Map<String, Object> map) {
        return Service.queryGroupListMapping(map);
    }

    @PostMapping("/analysis/bar")
    public LinkedHashMap<String, Object> analyseBarchart(@RequestBody Map<String, Object> map) {
        return Service.analyseBarchart(map);
    }

    @PostMapping("/analysis/line")
    public LinkedHashMap<String, Object> analyseLineChart(@RequestBody Map<String, Object> map) {
        return Service.analyseLineChart(map);
    }

    @PostMapping("/analysis/pie")
    public LinkedHashMap<String, Object> analysePieChart(@RequestBody Map<String, Object> map) {
        return Service.analysePieChart(map);
    }

    @PostMapping("/analysis/all")
    public String analyseData(@RequestBody Map<String, Object> map) {
        return Service.analyseData(map);
    }

    @PostMapping("/analysis/getClass")
    public String analyseGetClass(@RequestBody Map<String, Object> map) {
        return Service.analyseGetClass(map);
    }


}
