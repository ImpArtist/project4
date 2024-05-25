package com.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.pojo.StuInfo;
import com.project.mapper.InfoMapper;
import com.project.service.IService.IService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Infoimpl extends ServiceImpl<InfoMapper, Object> implements IService {

    @Override
    public List<Map<String, Object>> queryListConcrete(String table,String attribute, String value){
        return baseMapper.queryListByAttributeConcrete(table,attribute, value);
    }

    @Override
    public List<Map<String, Object>> queryListAbstract(String table,String attribute, String value){
        return baseMapper.queryListByAttributeAbstract(table,attribute, value);
    }

    @Override
    public List<Map<String, Object>> queryRangeList(String table,String attribute, String start, String end){
        return baseMapper.queryRangeListByAttribute(table,attribute, start, end);
    }

    @Override
    public List<Map<String, Object>>  queryAll(String table){
        return baseMapper.queryAll(table);
    }

    @Override
    public List<Map<String, Object>> getTableAttribute(String table) {
        return baseMapper.queryAllAttribute(table);
    }

    @Override
    public List<Map<String, Object>> getConnectSearchedList(String table1,String table2,String attribute1,String attribute2,String compareType){
        return baseMapper.queryConnectSearchedList(table1,table2,attribute1,attribute2,compareType);
    }

    public List<Map<String, Object>> getTableName(){
        return baseMapper.queryTableName();
    }

    @Override
    public List<Map<String, Object>> getGroupSearchedList(String table,String aggregateAttri,String groupAttri,String aggregateType){
        switch (aggregateType) {
            case "求和" -> {
                return baseMapper.queryGroupSumList(table, aggregateAttri, groupAttri);
            }
            case "平均值" -> {
                return baseMapper.queryGroupAvgList(table, aggregateAttri, groupAttri);
            }
            case "最小值" -> {
                return baseMapper.queryGroupMinList(table, aggregateAttri, groupAttri);
            }
            case "最大值" -> {
                return baseMapper.queryGroupMaxList(table, aggregateAttri, groupAttri);
            }
            case "计数" -> {
                return baseMapper.queryGroupCountList(table, aggregateAttri, groupAttri);
            }
            case "标准差" -> {
                return baseMapper.queryGroupStdList(table, aggregateAttri, groupAttri);
            }
            case "方差" -> {
                return baseMapper.queryGroupSqrtList(table, aggregateAttri, groupAttri);
            }
            case "中位数" -> {
                return baseMapper.queryGroupMedianList(table, aggregateAttri, groupAttri);
            }
            case "众数" -> {
                return baseMapper.queryGroupModeList(table, aggregateAttri, groupAttri);
            }
        }
        return null;
    }
}
