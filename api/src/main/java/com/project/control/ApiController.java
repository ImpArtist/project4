package com.project.control;

import com.project.service.IService.ApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final ApiService Service;
    @Autowired
    private HttpServletRequest request;

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
        String ipAddress = request.getHeader("X-Forwarded-For");

        // 如果 X-Forwarded-For 为空，使用 getRemoteAddr 方法获取 IP
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return Service.Visit(privateKey, num, ipAddress);
    }

    @PostMapping("/info")
    public LinkedHashMap<String, Object> getAPIInfo(){
        return Service.GetAPIInfo();
    }

    @PostMapping("/selectInfo")
    public List<LinkedHashMap<String, Object>> getAPISelectedInfo(@RequestBody Map<String, Object> map){
        return Service.getAPISelectedInfo(map);
    }

    @PostMapping("/delete")
    public boolean deleteAPI(@RequestBody Map<String, Object> map){
        return Service.deleteAPI(map);
    }

    @PostMapping("/getNameList")
    public List<LinkedHashMap<String, Object>> getNameList(@RequestBody Map<String, Object> map){
        return Service.getNameList(map);
    }

    @PostMapping("getConcreteChartsInfo")
    public LinkedHashMap<String, Object> getConcreteChartsInfo(@RequestBody Map<String, Object> map){
        return Service.getConcreteChartsInfo(map);
    }

    @PostMapping("getConcreteInfo")
    public LinkedHashMap<String, Object> getConcreteInfo(@RequestBody Map<String, Object> map){
        return Service.getConcreteInfo(map);
    }

    @PostMapping("open")
    public void open(@RequestBody Map<String, Object> map){
        Service.open(map);
    }

    @PostMapping("close")
    public void close(@RequestBody Map<String, Object> map){
        Service.close(map);
    }

}
