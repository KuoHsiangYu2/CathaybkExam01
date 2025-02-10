package com.hsiangyu.demo.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/* 對應 JSON格式 傳輸資料的 model */
public class CoinCurrencyDTORq {

    @JsonProperty("coinCurrencyPK")
    private long coinCurrencyPK = -1;

    @JsonProperty("time")
    private TimeDTO time;

    @JsonProperty("disclaimer")
    private String disclaimer;

    @JsonProperty("chartName")
    private String chartName;

    @JsonProperty("bpi")
    private Map<String, BpiDTO> bpi;

    public long getCoinCurrencyPK() {
        return coinCurrencyPK;
    }

    public void setCoinCurrencyPK(long coinCurrencyPK) {
        this.coinCurrencyPK = coinCurrencyPK;
    }

    public TimeDTO getTime() {
        return time;
    }

    public void setTime(TimeDTO time) {
        this.time = time;
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

    public Map<String, BpiDTO> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, BpiDTO> bpi) {
        this.bpi = bpi;
    }

}
