package com.webapp.vascomm.http_response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.*;

@Setter
@Getter

public class http_response {

    private List<?> data = new ArrayList<>();
    private String message;
    private Integer status;
    private HttpStatus statuscode;

    public void setFail() {

        this.data = null;
        this.message = "Fail";
        this.statuscode = HttpStatus.BAD_REQUEST;
        this.status = 0;
    }

    public void setFailNull() {
        List<?> dummy = new ArrayList<>();
        this.data = dummy;
        this.message = "Fail Null or Not Found";
        this.statuscode = HttpStatus.NOT_FOUND;
        this.status = 0;
    }

    public void setSuccess() {
        this.message = "Success";
        this.statuscode = HttpStatus.OK;
        this.status = 1;
    }

    public void setSuccessWithData(List<?> input_data) {
        this.data = input_data;
        this.message = "Success";
        this.statuscode = HttpStatus.OK;
        this.status = 1;
    }

}