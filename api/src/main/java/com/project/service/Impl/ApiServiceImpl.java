package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.ApiMapper;
import com.project.service.IService.ApiService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiServiceImpl  extends ServiceImpl<ApiMapper, Object> implements ApiService {
    @Override
    public boolean apiCreate(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        String info = Optional.ofNullable(map.get("info")).orElse("").toString();
        String privacy = Optional.ofNullable(map.get("privacy")).orElse("").toString();
        String command = Optional.ofNullable(map.get("command")).orElse("").toString();
        String flowControl = Optional.ofNullable(map.get("flowControl")).orElse("").toString();
        String business = Optional.ofNullable(map.get("business")).orElse("").toString();
        String url;
        int now,next;
        try {
            Map<String, Object> res = baseMapper.getUrl(privacy);
            if(res == null)
                url = "Undefined";
            else
                url = res.get("api_url").toString();
            if(url.equals("Undefined")){
                baseMapper.apiCreate(name, info, privacy, command, flowControl,business,"http://127.0.0.1:10010/api/selfDefind/"+privacy+"/1");
                System.out.println("http://127.0.0.1:10010/api/selfDefind/"+privacy+"/1");
            }
            else {
                String lastPart = url.substring(url.lastIndexOf("/") + 1);
                try {
                    now = Integer.parseInt(lastPart);
                    next = now + 1;
                } catch (NumberFormatException e) {
                    System.out.println("number format error");
                    return false;
                }
                String replacedUrl = url.replaceAll("/" + now + "$", "/" + next);
                baseMapper.apiCreate(name, info, privacy, command, flowControl,business,replacedUrl);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error");
            return false;
        }
    }

    @Override
    public boolean apiCheckSQL(Map<String, Object> map) {
        String sql = Optional.ofNullable(map.get("sql")).orElse("").toString();
        // 定义用于匹配 select 语句的正则表达式
        String selectRegex = "(?i)^\\s*SELECT\\s+.*\\s+FROM\\s+.*$";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(selectRegex, Pattern.CASE_INSENSITIVE);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(sql);
        if (!matcher.matches()) {
            return false;
        }
        try {
            List<Map<String, Object>> res = baseMapper.apiCheck(sql);
        } catch (Exception e) {
            System.out.println(sql);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<LinkedHashMap<String, Object>> Visit(String privateKey, int num) {
        List<LinkedHashMap<String, Object>> result=null;
        try {
            String url = "http://127.0.0.1:10010/api/selfDefine/" + privateKey + "/" + num;
            List<LinkedHashMap<String, Object>> res = baseMapper.Visit(url);
            String sql = Optional.ofNullable(res.get(0).get("api_command")).orElse("").toString();
            result =  baseMapper.GetInfo(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean apiCheckName(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        List<LinkedHashMap<String, Object>>  res = baseMapper.apiCheckName(name);
        return !res.isEmpty();
    }

    @Override
    public List<LinkedHashMap<String, Object>> GetAPIInfo() {
        return baseMapper.GetAPIInfo();
    }


}
