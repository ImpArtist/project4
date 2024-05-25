package com.project.domain.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class StuInfo {
    Long id;
    String stuNumber;
    String name;
    Integer sex;
    String category;
    String ethnicity;
    String idNumber;
    Date birthday;
    String className;
    String eduLevel;
    String politicalStatus;
    String dormitory;
    String nativePlace;
    String contact;
    String address;
    String emergencyContactName;
    String emergencyContactPhone;
    String Wechat;
    String emailAdr;
}
