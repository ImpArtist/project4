package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.ApiMapper;
import com.project.service.IService.ApiService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiServiceImpl  extends ServiceImpl<ApiMapper, Object> implements ApiService {
    @Override
    public LinkedHashMap<String, Object> apiCreate(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        String info = Optional.ofNullable(map.get("info")).orElse("").toString();
        String command = Optional.ofNullable(map.get("command")).orElse("").toString();
        String flowControl = Optional.ofNullable(map.get("flowControl")).orElse("").toString();
        String business = Optional.ofNullable(map.get("business")).orElse("").toString();
        String url = null;
        boolean flag = true;
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        int now = 0,next = 0;
        try {
            if(business.isEmpty()){
                business = "public";
            }
            Map<String, Object> res = baseMapper.getUrl(business);
            if(res == null)
                url = "Undefined";
            else
                url = res.get("api_url").toString();
            if(url.equals("Undefined")){
                baseMapper.apiCreate(name, info, business, command, flowControl,business,"http://127.0.0.1:10010/api/selfDefind/"+business+"/1");
                url = "http://127.0.0.1:10010/api/selfDefind/"+business+"/1";
            }
            else {
                String lastPart = url.substring(url.lastIndexOf("/") + 1);
                try {
                    now = Integer.parseInt(lastPart);
                    next = now + 1;
                } catch (NumberFormatException e) {
                    System.out.println("number format error");
                    flag = false;
                }
                String replacedUrl = url.replaceAll("/" + now + "$", "/" + next);
                baseMapper.apiCreate(name, info, business, command, flowControl,business,replacedUrl);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            flag = false;
        }
        result.put("judge",flag);
        result.put("url",url);
        return result;
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
    public List<LinkedHashMap<String, Object>> Visit(String privateKey, int num,String ip) {
        List<LinkedHashMap<String, Object>> result=null;
        try {
            String url = "http://127.0.0.1:10010/api/selfDefine/" + privateKey + "/" + num;
            List<LinkedHashMap<String, Object>> res = baseMapper.Visit(url);
            String sql = Optional.ofNullable(res.get(0).get("api_command")).orElse("").toString();
            result =  baseMapper.GetInfo(sql);
            baseMapper.updateAPI(url);
            String name = baseMapper.getAPIName(url).get(0).get("api_name").toString();
            LocalDateTime now = LocalDateTime.now();

            // 格式化时间输出，具体到秒
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            // 打印输出
            System.out.println("Current time: " + formattedDateTime);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public LinkedHashMap<String, Object> apiCheckName(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        boolean spacial = !name.contains(" ");
        List<LinkedHashMap<String, Object>>  res = baseMapper.apiCheckName(name);
        boolean flag = res.isEmpty();
        result.put("judgeSpace",spacial);
        result.put("judgeName",flag);
        return result;
    }

    @Override
    public LinkedHashMap<String, Object> GetAPIInfo() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> res =  baseMapper.GetAPIInfo();
        result.put("data",res);
        List<LinkedHashMap<String, Object>> trans = baseMapper.GetTransInfo();
        result.put("mapping",trans);
        return result;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getAPISelectedInfo(Map<String, Object> map) {
        String attribute = Optional.ofNullable(map.get("attribute")).orElse("").toString();
        String type = Optional.ofNullable(map.get("type")).orElse("").toString();
        String value = Optional.ofNullable(map.get("value")).orElse("").toString();
        return baseMapper.getAPISelectedInfo(attribute,value,type);
    }

    @Override
    public boolean deleteAPI(Map<String, Object> map) {
        boolean ans = true;
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        try{
            baseMapper.deleteAPI(name);
        }catch (Exception e){
            ans = false;
            System.out.println(e.getMessage());
        }
        return ans;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getNameList(Map<String, Object> map) {
        String value= Optional.ofNullable(map.get("value")).orElse("").toString();
        List<LinkedHashMap<String, Object>> res = new ArrayList<>();
        try{
            res = baseMapper.getNameList(value);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return res;
    }


}
