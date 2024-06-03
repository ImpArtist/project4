package com.project.domain.pojo;

import lombok.Data;

@Data
public class Api {
    Long apiId;
    String apiName;
    String apiUrl;
    String apiInfo;
    Integer apiStatus;
    String apiPrivacy;
    String apiCommand;
    String apiFlowControl;
    Long apiViewCount;
}
