package com.webapp.vascomm.controller;

import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.webapp.vascomm.http_response.http_response;
import com.webapp.vascomm.table.holiday;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/api/test")
public class rest_controller {


    private final OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build();;

    @GetMapping(path = "/1")
    public ResponseEntity<http_response> test1(@RequestParam String date, @RequestParam String country) {
        
        http_response resp = new http_response();
        try {

            String holidayListString = sendGet("https://date.nager.at/api/v3/PublicHolidays/" + date.split("-")[0]
                    + "/" + country);
            holiday[] holidayList = new Gson().fromJson(holidayListString, holiday[].class);

            List<holiday> showableHoliday = new ArrayList<>();
            for (holiday holiday : holidayList) {

                Integer month = Integer.parseInt(holiday.getDate().split("-")[1]);
                if (month == Integer.parseInt (date.split("-")[1])) {
                    showableHoliday.add(holiday);
                }
            }

            // do something
            resp.setSuccessWithData(showableHoliday);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    private String sendGet(String url) {

        Request request = new Request.Builder()
                .url(url)
                // .addHeader("custom-key", "mkyong") // add request headers
                // .addHeader("User-Agent", "OkHttp Bot")
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
