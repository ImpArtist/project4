package com.project.control;

import com.project.service.IService.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final ApiService Service;

    @PostMapping("create")
    public boolean create(@RequestBody Map<String, Object> map){
        return Service.apiCreate(map);
    }

    @PostMapping("check")
    public boolean check(@RequestBody Map<String, Object> map){
        return Service.apiCheck(map);
    }
}
