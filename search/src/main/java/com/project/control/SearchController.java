package com.project.control;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import com.project.service.IService.IService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.web.bind.annotation.*;

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

    //根据属性及其属性值的全字搜索
    @GetMapping("{table}/0/normal/{attribute}/{value}")
    public List<Map<String, Object>> getListByAttributeConcrete(@PathVariable("table") String table,@PathVariable("attribute") String attribute, @PathVariable("value")String value){
        return Service.queryListConcrete(table,attribute,value);
    }

    //根据属性及其属性值的模糊搜索
    @GetMapping("{table}/1/normal/{attribute}/{value}")
    public List<Map<String, Object>> getListByAttributeAbstract(@PathVariable("table") String table,@PathVariable("attribute") String attribute, @PathVariable("value")String value){
        return Service.queryListAbstract(table,attribute,value);
    }

    //根据属性范围查找
    @GetMapping("/{table}/range/{attribute}/{start}/{end}")
    public List<Map<String, Object>> getListByRange(@PathVariable("table") String table,@PathVariable("attribute") String attribute, @PathVariable("start")String start, @PathVariable("end")String end){
        return Service.queryRangeList(table,attribute,start,end);
    }

    //根据表名获取属性名
    @GetMapping("/table/attribute")
    public List<Map<String, Object>> getTableAttribute(@RequestBody Map<String, Object> map){
        return Service.getTableAttribute(map);
    }

    //获得所有表名
    @GetMapping("/table")
    public List<Map<String, Object>> getTableName(){
        return Service.getTableName();
    }

    //关联搜索
    @GetMapping("/connect/{table1}/{table2}/{attribute1}/{attribute2}/{compareType}")
    public List<Map<String, Object>> getConnectSearchedList(@PathVariable("table1") String table1,@PathVariable("table2") String table2,@PathVariable("attribute1") String attribute1,@PathVariable("attribute2") String attribute2,@PathVariable("compareType") String compareType){
        return Service.getConnectSearchedList(table1, table2, attribute1, attribute2, compareType);
    }

    //分组搜索
    @GetMapping("/group/{table}/{aggregateAttri}/{groupAttri}/{aggregateType}")
    public List<Map<String, Object>> getGroupSearchedList(@PathVariable("table") String table,@PathVariable("aggregateAttri") String aggregateAttri,@PathVariable("groupAttri") String groupAttri,@PathVariable("aggregateType") String aggregateType) {
        return Service.getGroupSearchedList(table, aggregateAttri, groupAttri, aggregateType);
    }
}
