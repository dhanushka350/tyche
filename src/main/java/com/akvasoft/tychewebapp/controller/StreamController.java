package com.akvasoft.tychewebapp.controller;

import com.akvasoft.tychewebapp.modal.RateDetails;
import com.akvasoft.tychewebapp.service.DataStreamService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "scrape/stream")
public class StreamController {


    @Autowired
    private DataStreamService streamService;

    @RequestMapping(value = "/getChartData/{currency}/{days}", method = RequestMethod.GET)
    public List<RateDetails> getChartData(@PathVariable String currency, @PathVariable int days) {
        return streamService.getChart(currency, days);
    }

}
