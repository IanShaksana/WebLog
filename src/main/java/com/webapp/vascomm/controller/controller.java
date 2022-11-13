package com.webapp.vascomm.controller;

import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.webapp.vascomm.table.holiday;
import com.webapp.vascomm.table.req;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping(path = "")
public class controller {

    private final OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build();;

    private req defaultReq = new req("ID", Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)+1);
    private Boolean searchCheck = true;

    @GetMapping()
    public String index(Model model) {
        try {

            model.addAttribute("name", "IAN");

            String countryCodesString = sendGet("https://date.nager.at/api/v3/AvailableCountries");
            holiday[] countryCodeList = new Gson().fromJson(countryCodesString, holiday[].class);
            model.addAttribute("countryCodes", countryCodeList);

            if (searchCheck) {
                String myCountryCodeString = sendGet(" http://ip-api.com/json/" + getPublicIP());
                holiday json = new Gson().fromJson(myCountryCodeString, holiday.class);
                defaultReq.setCountryCode(json.getCountryCode());
            }
            model.addAttribute("req", defaultReq);

            if (defaultReq.getMonth() == null) {

                String holidayListString = sendGet(
                        "https://date.nager.at/api/v3/PublicHolidays/" + defaultReq.getYear()
                                + "/" + defaultReq.getCountryCode());
                holiday[] holidayList = new Gson().fromJson(holidayListString, holiday[].class);

                model.addAttribute("holiday", holidayList);

            } else {
                String holidayListString = sendGet("https://date.nager.at/api/v3/PublicHolidays/" + defaultReq.getYear()
                        + "/" + defaultReq.getCountryCode());
                holiday[] holidayList = new Gson().fromJson(holidayListString, holiday[].class);

                List<holiday> showableHoliday = new ArrayList<>();
                for (holiday holiday : holidayList) {

                    Integer month = Integer.parseInt(holiday.getDate().split("-")[1]);
                    if (month == defaultReq.getMonth()) {
                        showableHoliday.add(holiday);
                    }
                }
                model.addAttribute("holiday", showableHoliday);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @PostMapping("/search")
    public String search(req req) {

        System.out.println(req.getMonth());
        System.out.println(req.getYear());
        defaultReq.setMonth(req.getMonth());
        defaultReq.setCountryCode(req.getCountryCode());
        defaultReq.setYear(req.getYear());

        if (req.getYear() == null) {
            defaultReq.setYear(Calendar.getInstance().get(Calendar.YEAR));
        }
        searchCheck = false;
        return "redirect:/";
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

    public String getPublicIP() {
        try {
            String urlString = "http://checkip.amazonaws.com/";
            URL url = new URL(urlString);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine();

        } catch (Exception e) {
            // TODO: handle exception
            return "192.168.1.10";
        }

    }

}
