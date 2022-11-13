package com.webapp.vascomm.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogMessage {
    
    public LogMessage() {
    }
    private Integer httpStatus;
    private String httpMethod;
    private String path;
    private String clientIp;
    private String javaMethod;
    private String response;
}
