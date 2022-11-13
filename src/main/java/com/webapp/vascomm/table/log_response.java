package com.webapp.vascomm.table;

import java.util.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

public class log_response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 50)
    private Integer id;

    @Column(columnDefinition = "LONGTEXT")
    private String json;


    @Column(columnDefinition = "DATETIME")
    private Date date;

    
    public log_response() {
    }


    public log_response(String json) {
        this.json = json;
        this.date = new Date();
    }

    
    
}
