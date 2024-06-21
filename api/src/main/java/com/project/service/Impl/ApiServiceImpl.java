package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.ApiMapper;
import com.project.service.IService.ApiService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Object> implements ApiService {

    String baseUrl = "127.0.0.1";
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
                baseMapper.apiCreate(name, info, business, command, flowControl,business,"http://" + baseUrl + ":10010/api/selfDefine/" + business + "/1");
                url = "http://" + baseUrl + ":10010/api/selfDefine/" + business + "/1";
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
            baseMapper.apiCheck(sql);
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
            String url = "http://"+ baseUrl + ":10010/api/selfDefine/" + privateKey + "/" + num;
            List<LinkedHashMap<String, Object>> res = baseMapper.Visit(url);
            String sql = Optional.ofNullable(res.get(0).get("api_command")).orElse("").toString();
            String status = Optional.ofNullable(res.get(0).get("api_status")).orElse("").toString();
            if(status.equals("停止"))
                return null;
            result =  baseMapper.GetInfo(sql);
            baseMapper.updateAPI(url);
            String name = baseMapper.getAPIName(url).get(0).get("api_name").toString();
            LocalDateTime now = LocalDateTime.now();
            baseMapper.updateAPIRecord(name,ip,now.toString());

            // 打印输出
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

    @Override
    public LinkedHashMap<String, Object> getConcreteChartsInfo(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        String type = Optional.ofNullable(map.get("type")).orElse("").toString();
        LocalDate currentDate = LocalDate.now();
        LocalDate daysAgo = currentDate;
        List<LinkedHashMap<String, Object>> records = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        switch (type) {
            case "最近一个月" -> daysAgo = currentDate.minusDays(29);
            case "最近一个星期" -> daysAgo = currentDate.minusDays(6);
            case "最近一天" -> {
                LocalDateTime exactTimeAgo = currentDateTime.minusDays(1); // 减去一天

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = exactTimeAgo.format(formatter);
                records = baseMapper.getWithinRecordsByDay(formattedDateTime, currentDateTime, name);
            }
        }
        if(!type.equals("最近一天")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = daysAgo.format(formatter);
            records = baseMapper.getWithinRecordsByDay(formattedDate, currentDateTime,name);
        }

        List<String> x = new ArrayList<>();
        List<Long> values;

        values = switch (type) {
            case "最近一个月" -> {
                generateDaysAgo(x, 30);
                yield countRecordsByDayAgo(records, x);
            }
            case "最近一个星期" -> {
                generateDaysAgo(x, 7);
                yield countRecordsByDayAgo(records, x);
            }
            case "最近一天" -> {
                generateHoursAgo(x);
                yield countRecordsByHourAgo(records, x);

            }
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };

        Map<String, Integer> ipCountMap = new HashMap<>();
        for (Map<String, Object> record : records) {
            String ip = (String) record.get("api_record_ip");
            ipCountMap.put(ip, ipCountMap.getOrDefault(ip, 0) + 1);
        }

        // Step 2: Sort by occurrence count descending
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(ipCountMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Step 3: Find the top 5 IPs with the most occurrences
        List<LinkedHashMap<String, Object>> result_ = new ArrayList<>();
        int totalRecords = records.size();
        int threshold = totalRecords / 10; // 1/10 of total records

        int remainingCount = 0;
        LinkedHashMap<String, Object> otherMap = new LinkedHashMap<>();

        for (int i = 0; i < sortedEntries.size(); i++) {
            String ip = sortedEntries.get(i).getKey();
            int count = sortedEntries.get(i).getValue();

            if (i < 5) {
                LinkedHashMap<String, Object> entryMap = new LinkedHashMap<>();
                entryMap.put("name", ip);
                entryMap.put("value", count);
                result_.add(entryMap);
            } else {
                if (count < threshold) {
                    remainingCount += count;
                } else {
                    LinkedHashMap<String, Object> entryMap = new LinkedHashMap<>();
                    entryMap.put("name", ip);
                    entryMap.put("value", count);
                    result_.add(entryMap);
                }
            }
        }

        // Step 4: Add "其他" entry if there are remaining IPs with count less than threshold
        if (remainingCount > 0) {
            otherMap.put("name", "其他");
            otherMap.put("value", remainingCount);
            result_.add(otherMap);
        }

        Map<String, Object> series = new LinkedHashMap<>();
        series.put("name", "计数");
        series.put("type", "pie");
        series.put("radius", "50%");


        series.put("data", result_);

        // 构建emphasis对象
        Map<String, Object> emphasis = new LinkedHashMap<>();
        Map<String, Object> itemStyle = new LinkedHashMap<>();
        itemStyle.put("shadowBlur", 10);
        itemStyle.put("shadowOffsetX", 0);
        itemStyle.put("shadowColor", "rgba(0, 0, 0, 0.5)");
        emphasis.put("itemStyle", itemStyle);
        series.put("emphasis", emphasis);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        DateTimeFormatter formatter_ = DateTimeFormatter.ofPattern("HH:00");
        List<String> values_ = switch (type) {
            case "最近一个月" -> IntStream.rangeClosed(0, 29)
                    .mapToObj(daysAgo_ -> LocalDate.now().minusDays(daysAgo_).format(formatter))
                    .toList();
            case "最近一个星期" -> IntStream.rangeClosed(0, 7)
                    .mapToObj(daysAgo_ -> LocalDate.now().minusDays(daysAgo_).format(formatter))
                    .toList();
            case "最近一天" -> IntStream.rangeClosed(0, 23)
                    .mapToObj(hoursAgo -> LocalDateTime.now().minusHours(hoursAgo).withMinute(0).withSecond(0).withNano(0).format(formatter_))
                    .collect(Collectors.toList());
            default -> new ArrayList<>();
        };
        // 生成从1到30天前的日期，并格式化为不包含年份的字符串
        List<LinkedHashMap<String, Object>> bar =  new ArrayList<>();
        LinkedHashMap<String, Object> barMap = new LinkedHashMap<>();
        barMap.put("name", "访问数");
        barMap.put("data", values);
        barMap.put("type", "line");
        bar.add(barMap);

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("xValues", values_);
        result.put("pie", series);
        result.put("bar", bar);
        return result;
    }


    private void generateDaysAgo(List<String> x, int max) {
        for (int i = 0; i < max; i++) {
            x.add(String.valueOf(i));
        }
    }

    private void generateHoursAgo(List<String> x) {
        for (int i = 0; i < 24; i++) {
            x.add(String.valueOf(i));
        }
    }


    private List<Long> countRecordsByDayAgo(List<LinkedHashMap<String, Object>> records, List<String> x) {
        Map<String, Long> dayCounts = records.stream()
                .collect(Collectors.groupingBy(record -> {
                    String time = record.get("api_record_time").toString().split(" ")[0]; // 移除小数点和后续的所有字符
                    LocalDate recordDate = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate currentDate =  LocalDate.now();
                    return getDayAgoString(currentDate, recordDate);
                }, Collectors.counting()));

        return fillValues(x, dayCounts);
    }

    private List<Long> countRecordsByHourAgo(List<LinkedHashMap<String, Object>> records, List<String> x) {
        Map<String, Long> hourCounts = records.stream()
                .collect(Collectors.groupingBy(record -> {
                    String time = record.get("api_record_time").toString().split("\\.")[0];
                    LocalDateTime recordTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    return getHourAgoString(recordTime, currentDateTime);
                }, Collectors.counting()));

        return fillValues(x, hourCounts);
    }

    private String getDayAgoString(LocalDate currentDate, LocalDate recordDate) {
        long daysBetween = ChronoUnit.DAYS.between(recordDate, currentDate);
        return String.valueOf(daysBetween);
    }

    private String getHourAgoString(LocalDateTime recordTime, LocalDateTime currentDateTime) {
        long hoursBetween = ChronoUnit.HOURS.between(recordTime, currentDateTime);
        return String.valueOf(hoursBetween);
    }

    private List<Long> fillValues(List<String> x, Map<String, Long> dayOrHourCounts) {
        List<Long> values = new ArrayList<>();
        for (String dayOrHour : x) {
            Long count = dayOrHourCounts.getOrDefault(dayOrHour, 0L);
            values.add(count);
        }
        return values;
    }

    @Override
    public LinkedHashMap<String, Object> getConcreteInfo(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        try{
            return baseMapper.getConcreteInfo(name).get(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new LinkedHashMap<>();
    }

    @Override
    public boolean open(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        baseMapper.open(name);
        return true;
    }

    @Override
    public boolean close(Map<String, Object> map) {
        String name = Optional.ofNullable(map.get("name")).orElse("").toString();
        baseMapper.close(name);
        return true;
    }


}
