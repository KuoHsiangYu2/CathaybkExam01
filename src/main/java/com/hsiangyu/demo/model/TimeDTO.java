package com.hsiangyu.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/* 對應 JSON格式 傳輸資料的 model */
public class TimeDTO {

    /* "Feb 4, 2025 05:53:57 UTC" */
    @JsonProperty("updated")
    private String updated;

    /* "2025-02-04T05:53:57+00:00" */
    @JsonProperty("updatedISO")
    private String updatedISO;

    /* "Feb 4, 2025 at 05:53 GMT" */
    @JsonProperty("updateduk")
    private String updateduk;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(String updatedISO) {
        this.updatedISO = updatedISO;
    }

    public String getUpdateduk() {
        return updateduk;
    }

    public void setUpdateduk(String updateduk) {
        this.updateduk = updateduk;
    }

}
