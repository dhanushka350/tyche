package com.akvasoft.tychewebapp.service;

import com.akvasoft.tychewebapp.modal.RateDetails;
import com.akvasoft.tychewebapp.repo.RateDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataStreamService {

    @Autowired
    private RateDetailsRepo repo;

    public List<RateDetails> getChart(String currency, int days) {
        if (days == 5) {
            return getForFiveDays(currency);
        } else if (days == 10) {
            return getForTenDays(currency);
        } else if (days == 30) {
            return getForThirtyDays(currency);
        } else {
            return getForNinetyDays(currency);
        }
    }

    private List<RateDetails> getForNinetyDays(String currency) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -90);
        return repo.getLastDaysRecords(currency, cal.getTime());
    }

    private List<RateDetails> getForThirtyDays(String currency) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        return repo.getLastDaysRecords(currency, cal.getTime());
    }

    private List<RateDetails> getForTenDays(String currency) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);
        return repo.getLastDaysRecords(currency, cal.getTime());
    }

    private List<RateDetails> getForFiveDays(String currency) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -5);
        return repo.getLastDaysRecords(currency, cal.getTime());
    }

    public String exportCSVFile(String start, String end) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<RateDetails> csvData = repo.getCSVData(format.parse(start), format.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
