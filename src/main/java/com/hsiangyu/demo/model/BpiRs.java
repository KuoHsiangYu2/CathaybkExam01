package com.hsiangyu.demo.model;

/* BPI Response model */
/* 對應 JSON格式 傳輸資料的 model */
public class BpiRs {

    /* 幣別 */
    private String bpiCode;

    /* 幣別中文名稱 */
    private String bpiCodeZhTW;

    /* 匯率 */
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
