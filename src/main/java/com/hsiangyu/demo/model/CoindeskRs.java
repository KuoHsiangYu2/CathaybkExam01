package com.hsiangyu.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/* Coindesk Response model */
/* 對應 JSON格式 傳輸資料的 model */
public class CoindeskRs {

    /* 顯示打 coindesk API 成功/失敗 訊息 */
    @JsonProperty("apiMessage")
    private String apiMessage;

    /* 時間格式範例：1990/01/01 00:00:00 */
    /* 更新時間 */
    @JsonProperty("updateTime")
    private String updateTime;

    /* 幣別資訊 */
    @JsonProperty("bpiRsList")
    private List<BpiRs> bpiRsList;

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
