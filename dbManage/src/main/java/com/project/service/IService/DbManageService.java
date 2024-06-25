package com.project.service.IService;

import java.util.LinkedHashMap;
import java.util.Map;

public interface DbManageService {
    boolean createTable(Map<String, Object> map);

    Object[] getTables(Map<String, Object> map);

    LinkedHashMap<String, Object> getTableData(Map<String, Object> map);

    LinkedHashMap<String, Object> getTableStruct(Map<String, Object> map);
}
