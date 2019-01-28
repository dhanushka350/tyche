package com.akvasoft.tychewebapp.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_RATE_DETAILS")
public class RateDetails {
    @Id
    @Column(name = "RATE_DETAILS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "SYMBOL")
    private String symbol;
    @Column(name = "BID")
    private String bid;
    @Column(name = "ASK")
    private String ask;
    @Column(name = "SHORT_VALUE")
    private String shortValue;
    @Column(name = "CHANGE_OPEN_INTEREST")
    private String changeOpenInterest;
    @Column(name = "INNER_LINK")
    private String innerLink;
    @Column(name = "SCR_DATE")
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getShortValue() {
        return shortValue;
    }

    public void setShortValue(String shortValue) {
        this.shortValue = shortValue;
    }

    public String getChangeOpenInterest() {
        return changeOpenInterest;
    }

    public void setChangeOpenInterest(String changeOpenInterest) {
        this.changeOpenInterest = changeOpenInterest;
    }

    public String getInnerLink() {
        return innerLink;
    }

    public void setInnerLink(String innerLink) {
        this.innerLink = innerLink;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
