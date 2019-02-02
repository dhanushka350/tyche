package com.akvasoft.tychewebapp.service;

import com.akvasoft.tychewebapp.modal.RateDetails;
import com.akvasoft.tychewebapp.repo.RateDetailsRepo;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class MainService implements InitializingBean {

    @Autowired
    private RateDetailsRepo repo;

    private FirefoxDriver innerDriver;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = null;

    public void start(FirefoxDriver driver) {
        System.setProperty("webdriver.gecko.driver", "/var/lib/tomcat8/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);

        try {
            scrapeRates(driver);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scrapeRates(FirefoxDriver driver) throws InterruptedException, IOException, InvalidFormatException {
        RateDetails r = null;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://www.dailyfx.com/forex-rates?ref=TopRates");

        WebElement table = driver.findElementByXPath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[1]/table");
        List<WebElement> trs = table.findElement(By.tagName("tbody")).findElements(By.xpath("./*"));
        Thread.sleep(2000);
        for (WebElement tr : trs) {
            if (tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("EURUSD")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("GBPUSD")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("USDJPY")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("USDCAD")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("AUDUSD")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("NZDUSD")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("Ethereum")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("Bitcoin")) {

                System.out.println(tr.findElements(By.xpath("./*")).get(1).findElement(By.tagName("span")).getAttribute("data-value") + "+=======================");
                System.out.println(tr.findElements(By.xpath("./*")).get(2).findElement(By.tagName("span")).getAttribute("data-value") + "+=======================");

                r = new RateDetails();
                r.setSymbol(tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText"));
                r.setBid(tr.findElements(By.xpath("./*")).get(1).findElement(By.tagName("span")).getAttribute("data-value"));
                r.setAsk(tr.findElements(By.xpath("./*")).get(2).findElement(By.tagName("span")).getAttribute("data-value"));

                Thread.sleep(1000);

                if (r.getSymbol().equalsIgnoreCase("EURUSD")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("GBPUSD")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("USDJPY")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("USDCAD")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("AUDUSD")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("NZDUSD")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("Ethereum")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("Bitcoin")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("Oil - US Crude")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                }

            }
        }


        WebElement Indices = driver.findElementByXPath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[2]/table[1]/tbody");
        List<WebElement> trs2 = Indices.findElements(By.xpath("./*"));
        for (WebElement tr : trs2) {
            System.out.println(tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText"));
            // US 500 for SP 500
            // Wall Street for US 30 Wall Street
            if (tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("Oil - US Crude")) {

                r = new RateDetails();
                r.setSymbol(tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText"));
                r.setBid(tr.findElements(By.xpath("./*")).get(1).findElement(By.tagName("span")).getAttribute("data-value"));
                r.setAsk(tr.findElements(By.xpath("./*")).get(2).findElement(By.tagName("span")).getAttribute("data-value"));

                if (r.getSymbol().equalsIgnoreCase("Oil - US Crude")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                }

            }
        }


        Indices = driver.findElementByXPath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[2]/table[2]/tbody");
        trs2 = Indices.findElements(By.xpath("./*"));
        for (WebElement tr : trs2) {

            // US 500 for SP 500
            // Wall Street for US 30 Wall Street
            if (tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("Wall Street")
                    || tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText").equalsIgnoreCase("US 500")) {

                r = new RateDetails();
                r.setSymbol(tr.findElements(By.xpath("./*")).get(0).getAttribute("innerText"));
                r.setBid(tr.findElements(By.xpath("./*")).get(1).findElement(By.tagName("span")).getAttribute("data-value"));
                r.setAsk(tr.findElements(By.xpath("./*")).get(2).findElement(By.tagName("span")).getAttribute("data-value"));

                if (r.getSymbol().equalsIgnoreCase("Wall Street")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                } else if (r.getSymbol().equalsIgnoreCase("US 500")) {
                    saveRate(getInnerData(r, tr.findElements(By.xpath("./*")).get(0).findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href")));
                }

            }
        }

        driver.close();
    }

    private RateDetails getInnerData(RateDetails r, String attribute) throws InterruptedException {

        innerDriver.get(attribute);
        Thread.sleep(5000);

        WebElement sentiment = null;
        String shortValue = null;
        String interest = null;
        now = LocalDateTime.now();

        sentiment = innerDriver.findElementByCssSelector(".margin-bottom-40 > div:nth-child(1) > div:nth-child(3)");
        shortValue = sentiment.findElements(By.xpath("./*")).get(2).getAttribute("innerText");
        interest = sentiment.findElements(By.xpath("./*")).get(3).getAttribute("innerText");

        System.out.println(shortValue + "=======");
        System.out.println(interest + "=======");

        r.setShortValue(shortValue.split("\n")[1]);
        r.setChangeOpenInterest(interest.split("\n")[1]);
        r.setDate(new Date());
        r.setInnerLink(attribute);
        System.out.println(r.getSymbol() + " collected");
        return r;
    }

    public void saveRate(RateDetails details) {
        repo.save(details);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("webdriver.gecko.driver", "/var/lib/tomcat8/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        innerDriver = new FirefoxDriver(options);
        FirefoxDriver driver=new FirefoxDriver(options);
        new Thread(() -> {
            while (true) {
                start(driver);
//                try {
////                    Thread.sleep(50000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();
    }
}
