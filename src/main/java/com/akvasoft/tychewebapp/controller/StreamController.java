package com.akvasoft.tychewebapp.controller;

import com.akvasoft.tychewebapp.modal.RateDetails;
import com.akvasoft.tychewebapp.service.DataStreamService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/scrape")
public class StreamController {


    @Autowired
    private DataStreamService streamService;

    class Response {
        private List<RateDetails> data = new ArrayList<>();

        public List<RateDetails> getData() {
            return data;
        }

        public void setData(List<RateDetails> data) {
            this.data = data;
        }
    }

    @RequestMapping(value = "/getChartData/{currency}/{days}", method = RequestMethod.GET)
    public Response getChartData(@PathVariable String currency, @PathVariable int days) {
        System.out.println(currency + "-" + days);
        List<RateDetails> chart = streamService.getChart(currency, days);

        Response response = new Response();
        response.setData(chart);
        System.out.println("FOUND " + chart.size() + " OBJECTS");
        return response;
    }

    @RequestMapping(value = "/exportCSV/{start}/{end}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> exportCSV(@PathVariable String start, @PathVariable String end, HttpServletResponse response) throws FileNotFoundException {
        streamService.exportCSVFile(start, end);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"forex.xlsx\"");
        InputStreamResource resource = new InputStreamResource(new FileInputStream("/var/lib/tomcat8/forex.xlsx"));
        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
