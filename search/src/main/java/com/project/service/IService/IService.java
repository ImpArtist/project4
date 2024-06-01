package com.project.service.IService;

import com.project.domain.pojo.AttributeTranslation;
import com.project.domain.pojo.StuInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IService {

    List<LinkedHashMap<String, Object>> queryList(Map<String, Object> map);

    List<LinkedHashMap<String, String>> queryListMapping(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> queryRangeList(Map<String, Object> map);

    List<Map<String, Object>> queryAll(String table);

    List<Map<String, Object>> getTableAttribute(Map<String, Object> table);

    List<Map<String, Object>> getConnectSearchedList(Map<String, Object> map);

    List<Map<String, Object>> getGroupSearchedList(String table,String aggregateAttri,String groupAttri,String aggregateType);

    List<Map<String, Object>> getTableName();

}
