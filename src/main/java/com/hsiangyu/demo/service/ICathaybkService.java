package com.hsiangyu.demo.service;

import com.hsiangyu.demo.model.CoinCurrencyDTORq;
import com.hsiangyu.demo.model.CoinCurrencyRs;
import com.hsiangyu.demo.model.CoindeskRs;

public interface ICathaybkService {

    /* 呼叫 coindesk API，解析其下行內容與資料轉換，並實作新的 API。 */
    public abstract CoindeskRs getCoindeskAPI();

    /* 新增 */
    public abstract CoinCurrencyRs createCoinData(CoinCurrencyDTORq coinCurrencyDTORq);

    /* 更新 */
    public abstract CoinCurrencyRs updateCoinData(CoinCurrencyDTORq coinCurrencyDTORq);

    /* 查詢 */
    public abstract CoinCurrencyRs searchCoinData(long coinCurrencyPK);

    /* 刪除 */
    public abstract CoinCurrencyRs deleteCoinData(long coinCurrencyPK);

    /* 計算數字加總 */
    public abstract int calculateNumberSum01(int minNum, int maxNum);

    /* 計算數字加總 */
    public abstract int calculateNumberSum02(int minNum, int maxNum);

}
