package com.hsiangyu.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* 資料庫 對應的 表 */
/* 幣值 */
@Entity
@Table(name = "coin_currency")
public class CoinCurrency {

    /* CoinCurrency [Primary Key] */
    @Id
    @Column(name = "coin_currency_id")
    private long coinCurrencyId;

    @Column(name = "updated_time")
    private String updatedTime;

    @Column(name = "updatedISO_time")
    private String updatedISOTime;

    @Column(name = "updateduk_time")
    private String updatedukTime;

    @Column(name = "disclaimer")
    private String disclaimer;

    @Column(name = "chart_name")
    private String chartName;

    public long getCoinCurrencyId() {
        return coinCurrencyId;
    }

    public void setCoinCurrencyId(long coinCurrencyId) {
        this.coinCurrencyId = coinCurrencyId;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedISOTime() {
        return updatedISOTime;
    }

    public void setUpdatedISOTime(String updatedISOTime) {
        this.updatedISOTime = updatedISOTime;
    }

    public String getUpdatedukTime() {
        return updatedukTime;
    }

    public void setUpdatedukTime(String updatedukTime) {
        this.updatedukTime = updatedukTime;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

}
