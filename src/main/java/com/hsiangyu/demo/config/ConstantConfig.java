package com.hsiangyu.demo.config;

import java.util.HashMap;
import java.util.Map;

/* 定義常數的類別 */
public class ConstantConfig {

    /* coindesk API 網址 */
    public static final String coindeskAPI = "https://api.coindesk.com/v1/bpi/currentprice.json";

    /* 幣別 {key=英文名稱} {value=中文名稱} 對照map */
    private static final Map<String, String> coinConstantMap = new HashMap<String, String>();
    static {
        coinConstantMap.put("USD", "美金");
        coinConstantMap.put("GBP", "英鎊");
        coinConstantMap.put("EUR", "歐元");
        coinConstantMap.put("JPY", "日圓");
        coinConstantMap.put("HKD", "港幣");
        coinConstantMap.put("CHF", "瑞士法郎");
        coinConstantMap.put("AUD", "澳幣");
        coinConstantMap.put("CAD", "加拿大幣");
        coinConstantMap.put("SGD", "新加坡幣");
        coinConstantMap.put("SEK", "瑞典幣");
        coinConstantMap.put("DKK", "丹麥幣");
        coinConstantMap.put("THB", "泰銖");
        coinConstantMap.put("NZD", "紐西蘭幣");
        coinConstantMap.put("ZAR", "南非幣");
        coinConstantMap.put("KRW", "韓圜");
    }

    public static Map<String, String> getCoinConstantMap() {
        return coinConstantMap;
    }

}
