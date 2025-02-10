package com.hsiangyu.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/* Response model */
/* 對應 JSON格式 傳輸資料的 model */
public class CoinCurrencyRs {

    /* 新增/修改/刪除/查詢 */
    /* create/update/delete/search */
    @JsonProperty("status")
    private String status;

    /* 新增/修改/刪除/查詢 資料筆數 */
    @JsonProperty("count")
    private int count;

    @JsonProperty("apiMessage")
    private String apiMessage;

    /* 時間格式範例：1990/01/01 00:00:00 */
    /* 更新時間 */
    @JsonProperty("updateTime")
    private String updateTime;

    /* 幣別資訊 */
    @JsonProperty("bpiRsList")
    private List<BpiRs> bpiRsList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<BpiRs> getBpiRsList() {
        return bpiRsList;
    }

    public void setBpiRsList(List<BpiRs> bpiRsList) {
        this.bpiRsList = bpiRsList;
    }

}
