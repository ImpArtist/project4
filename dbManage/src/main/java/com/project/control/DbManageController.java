package com.project.control;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.service.IService.DbManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/DbManage")
@RestController
@RequiredArgsConstructor
public class DbManageController {

    private final DbManageService Service;
    @PostMapping("/createTable")
    public boolean createTable(@RequestBody Map<String, Object> map){
        return Service.createTable(map);
    }

    @PostMapping("/getTables")
    public List<LinkedHashMap<String,Object>> getTables(Map<String, Object> map){
        return Service.getTables(map);
    }

    @PostMapping("/getTableData")
    LinkedHashMap<String,Object> getTableData(@RequestBody Map<String, Object> map){
        return Service.getTableData(map);
    }

    @PostMapping("/getTableStruct")
    LinkedHashMap<String,Object> getTableStruct(@RequestBody Map<String, Object> map){
        return Service.getTableStruct(map);
    }

    @PostMapping("/getFieldList")
    List<String> getFieldList(@RequestBody Map<String, Object> map){
        return Service.getFieldList(map);
    }

    @PostMapping("/deleteTable")
    boolean deleteTable(@RequestBody Map<String, Object> map){
        return Service.deleteTable(map);
    }


    @PostMapping("/deleteRecord")
    boolean deleteRecord(@RequestBody Map<String, Object> map){
        return Service.deleteRecord(map);
    }

}
