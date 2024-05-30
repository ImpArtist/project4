package com.project.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StuInfo {
    Long stu_id;
    String stu_number;
    String stu_name;
    Integer stu_sex;
    String stu_category;
    String stu_ethnicity;
    String stu_idNumber;
    Date stu_birthday;
    String stu_className;
    String stu_eduLevel;
    String stu_politicalStatus;
    String stu_dormitory;
    String stu_nativePlace;
    String stu_contact;
    String stu_address;
    String stu_emergencyContactName;
    String stu_emergencyContactPhone;
    String stu_Wechat;
    String stu_emailAdr;
}
