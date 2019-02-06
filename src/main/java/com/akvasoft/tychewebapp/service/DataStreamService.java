package com.akvasoft.tychewebapp.service;

import com.akvasoft.tychewebapp.modal.ExcelObject;
import com.akvasoft.tychewebapp.modal.RateDetails;
import com.akvasoft.tychewebapp.repo.RateDetailsRepo;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public boolean exportCSVFile(String start, String end) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = format.parse(end);
            endDate = DateUtils.addDays(endDate, 1);

            ExcelObject excelObjects = null;
            List<RateDetails> csvData = repo.getCSVData(format.parse(start), endDate);
            System.err.println(+csvData.size() + " **");
            createExcelFile(csvData);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void appendRows(List<RateDetails> datalist) {
//        try {
//
//            Workbook wb3 = WorkbookFactory.create(new FileInputStream("/var/lib/tomcat8/forex.xlsx"));
//            Sheet sh = wb3.getSheet("tyche");
//            int rows = 1;
//
//            for (RateDetails data : datalist) {
//                Row row = sh.createRow(rows++);
//                row.createCell(0).setCellValue(data.getDate().toString());
//                row.createCell(1).setCellValue(data.getSymbol());
//                row.createCell(2).setCellValue(data.getAsk());
//                row.createCell(3).setCellValue(data.getBid());
//                row.createCell(4).setCellValue(data.getShortValue());
//                row.createCell(5).setCellValue(data.getChangeOpenInterest());
//                row.createCell(6).setCellValue(data.getInnerLink());
//
//            }
//
//            FileOutputStream fileOut = new FileOutputStream("/var/lib/tomcat8/forex.xlsx");
//            wb3.write(fileOut);
//            fileOut.close();
//            System.out.println("excel updated.");
//
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private void createExcelFile(List<RateDetails> datalist) throws IOException {

//        try {
//            new FileInputStream("/var/lib/tomcat8/forex.xlsx");
//        } catch (FileNotFoundException e) {
//            new File("/var/lib/tomcat8/forex").mkdir();
        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("tyche");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(1);
        ArrayList<String> list = new ArrayList();

        list.add("SCRAPE_DATE");
        list.add("SYMBOL");
        list.add("ASK");
        list.add("BID");
        list.add("SHORT_VALUE");
        list.add("CHANGE_OPEN_INTEREST");
        list.add("INNER_LINK");

        for (int i = 0; i < list.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(list.get(i));
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        int rows = 2;
        for (RateDetails data : datalist) {
            Row row = sheet.createRow(rows++);
            row.createCell(0).setCellValue(data.getDate().toString());
            row.createCell(1).setCellValue(data.getSymbol());
            row.createCell(2).setCellValue(data.getAsk());
            row.createCell(3).setCellValue(data.getBid());
            row.createCell(4).setCellValue(data.getShortValue());
            row.createCell(5).setCellValue(data.getChangeOpenInterest());
            row.createCell(6).setCellValue(data.getInnerLink());
            System.out.println(data.getDate().toString());
        }

        FileOutputStream fileOut = new FileOutputStream("/var/lib/tomcat8/forex.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Excel Created");
//        }
    }
}
