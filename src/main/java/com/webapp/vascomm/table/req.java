package com.webapp.vascomm.table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class req {

    private String countryCode;
    private Integer year;
    private Integer month;
    
    public req(String countryCode, Integer year, Integer month) {
        this.countryCode = countryCode;
        this.year = year;
        this.month = month;
    }
    
}
