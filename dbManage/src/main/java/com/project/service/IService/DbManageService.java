package com.project.service.IService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface DbManageService {
    boolean createTable(Map<String, Object> map);

    List<LinkedHashMap<String, Object>> getTables(Map<String, Object> map);

    LinkedHashMap<String, Object> getTableData(Map<String, Object> map);

    LinkedHashMap<String, Object> getTableStruct(Map<String, Object> map);

    List<String> getFieldList(Map<String, Object> map);

    boolean deleteTable(Map<String, Object> map);

    boolean deleteRecord(Map<String, Object> map);

    boolean updateStructTable(Map<String, Object> map);

    boolean insertRecord(Map<String, Object> map);

    boolean deleteField(Map<String, Object> map);
}
