package com.webapp.vascomm.table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class holiday {

    private String name;
    private String localName;
    private String date;
    private String countryCode;

    public holiday(String name, String localName, String date, String countryCode) {
        this.name = name;
        this.localName = localName;
        this.date = date;
        this.countryCode = countryCode;
    }

    
}
