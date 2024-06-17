package com.project.control;

import com.project.service.IService.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final ApiService Service;

    @PostMapping("create")
    public LinkedHashMap<String, Object> create(@RequestBody Map<String, Object> map){
        return Service.apiCreate(map);
    }

    @PostMapping("checksql")
    public boolean checkSQL(@RequestBody Map<String, Object> map){
        return Service.apiCheckSQL(map);
    }

    @PostMapping("checkname")
    public LinkedHashMap<String, Object> checkName(@RequestBody Map<String, Object> map){
        return Service.apiCheckName(map);
    }

    @GetMapping("/selfDefine/{private}/{num}")
    public List<LinkedHashMap<String, Object>> visit(@PathVariable("private") String privateKey, @PathVariable("num") int num){
        return Service.Visit(privateKey, num);
    }

    @PostMapping("/info")
    public LinkedHashMap<String, Object> getAPIInfo(){
        return Service.GetAPIInfo();
    }
}
