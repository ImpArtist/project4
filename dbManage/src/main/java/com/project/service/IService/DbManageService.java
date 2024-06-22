package com.project.service.IService;

import java.util.Map;

public interface DbManageService {
    boolean createTable(Map<String, Object> map);

    Object[] getTables(Map<String, Object> map);
}
