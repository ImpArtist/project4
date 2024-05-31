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

    //根据属性及其属性值的全字搜索
    @PostMapping("normal/concrete")
    public List<LinkedHashMap<String, Object>> queryListConcrete(@RequestBody Map<String, Object> map){
        return Service.queryListConcrete(map);
    }

    //根据属性及其属性值的模糊搜索
    @PostMapping("normal/abstract")
    public List<LinkedHashMap<String, Object>>queryListAbstract(@RequestBody Map<String, Object> map){
        return Service.queryListAbstract(map);
    }

    //统一的常规搜索
    @PostMapping("normal")
    public List<LinkedHashMap<String, Object>>queryList(@RequestBody Map<String, Object> map){
        return Service.queryList(map);
    }

    //根据属性范围查找
    @PostMapping("/range")
    public List<Map<String, Object>> getListByRange(@RequestBody Map<String, Object> map){
        return Service.queryRangeList(map);
    }

    //根据表名获取属性名
    @PostMapping("/table/attribute")
    public List<Map<String, Object>> getTableAttribute(@RequestBody Map<String, Object> map){
        return Service.getTableAttribute(map);
    }

    //获得所有表名
    @GetMapping("/table")
    public List<Map<String, Object>> getTableName(){
        return Service.getTableName();
    }

    //关联搜索
    @PostMapping("/connect")
    public List<Map<String, Object>> getConnectSearchedList(@RequestBody Map<String, Object> map){
        return Service.getConnectSearchedList(map);
    }

    //分组搜索
    @GetMapping("/group/{table}/{aggregateAttri}/{groupAttri}/{aggregateType}")
    public List<Map<String, Object>> getGroupSearchedList(@PathVariable("table") String table,@PathVariable("aggregateAttri") String aggregateAttri,@PathVariable("groupAttri") String groupAttri,@PathVariable("aggregateType") String aggregateType) {
        return Service.getGroupSearchedList(table, aggregateAttri, groupAttri, aggregateType);
    }
}
