package com.project.service.IService;

import com.project.domain.pojo.StuInfo;

import java.util.List;
import java.util.Map;

public interface IService {
    List<Map<String, Object>> queryListConcrete(String table,String attribute, String value);

    List<Map<String, Object>> queryListAbstract(String table,String attribute, String value);

    List<Map<String, Object>> queryRangeList(String table,String attribute, String start, String end);

    List<Map<String, Object>> queryAll(String table);

    List<Map<String, Object>> getTableAttribute(Map<String, Object> table);

    List<Map<String, Object>> getConnectSearchedList(String table1,String table2,String attribute1,String attribute2,String compareType);

    List<Map<String, Object>> getGroupSearchedList(String table,String aggregateAttri,String groupAttri,String aggregateType);

    List<Map<String, Object>> getTableName();

}
