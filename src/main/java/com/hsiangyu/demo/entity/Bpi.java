package com.hsiangyu.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* 資料庫 對應的 表 */
/* 幣值詳細資料清單 */
@Entity
@Table(name = "bpi")
public class Bpi {

    /* Bpi [Foreign Key] */
    @Column(name = "bpi_id")
    private long bpiId;

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "rate")
    private String rate;

    @Column(name = "description")
    private String description;

    @Column(name = "rate_float")
    private String rateFloat;

    public long getBpiId() {
        return bpiId;
    }

    public void setBpiId(long bpiId) {
        this.bpiId = bpiId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(String rateFloat) {
        this.rateFloat = rateFloat;
    }

}
