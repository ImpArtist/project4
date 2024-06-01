package com.project.control;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.service.IService.DbManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/dbManage")
@RestController
@RequiredArgsConstructor
public class DbManageController {

    private final DbManageService Service;
    @PostMapping("/create")
    public boolean createTable(List<Map<String, Object>> map){
        return Service.createTable(map);
    }
}
