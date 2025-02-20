package com.hsiangyu.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/* BPI Response model */
/* 對應 JSON格式 傳輸資料的 model */
public class BpiRs {

    /* 幣別 */
    @JsonProperty("bpiCode")
    private String bpiCode;

    /* 幣別中文名稱 */
    @JsonProperty("bpiCodeZhTW")
    private String bpiCodeZhTW;

    /* 匯率 */
    @JsonProperty("rateFloat")
    private double rateFloat;

    public String getBpiCode() {
        return bpiCode;
    }

    public void setBpiCode(String bpiCode) {
        this.bpiCode = bpiCode;
    }

    public String getBpiCodeZhTW() {
        return bpiCodeZhTW;
    }

    public void setBpiCodeZhTW(String bpiCodeZhTW) {
        this.bpiCodeZhTW = bpiCodeZhTW;
    }

    public double getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(double rateFloat) {
        this.rateFloat = rateFloat;
    }
}
