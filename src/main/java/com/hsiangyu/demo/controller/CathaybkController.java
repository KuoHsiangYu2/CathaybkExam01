package com.hsiangyu.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hsiangyu.demo.model.CoinCurrencyDTORq;
import com.hsiangyu.demo.model.CoinCurrencyRs;
import com.hsiangyu.demo.model.CoindeskRs;
import com.hsiangyu.demo.service.ICathaybkService;

@RestController
public class CathaybkController {

    private static final Logger logger = LoggerFactory.getLogger(CathaybkController.class);

    @Autowired
    private ICathaybkService cathaybkService;

    /* 呼叫 coindesk API，解析其下行內容與資料轉換，並實作新的 API。 */
    @PostMapping("/Cathaybk/coindeskAPI")
    public CoindeskRs getCoindeskAPI() {
        logger.info("Post Cathaybk coindeskAPI");
        return cathaybkService.getCoindeskAPI();
    }

    /* 新增 */
    @PostMapping("/Cathaybk/create")
    public CoinCurrencyRs createCoinData(@RequestBody CoinCurrencyDTORq coinCurrencyRequest) {
        logger.info("Post Cathaybk create");
        return cathaybkService.createCoinData(coinCurrencyRequest);
    }

    /* 更新 */
    @PostMapping("/Cathaybk/update")
    public CoinCurrencyRs updateCoinData(@RequestBody CoinCurrencyDTORq coinCurrencyRequest) {
        logger.info("Post Cathaybk update");
        return cathaybkService.updateCoinData(coinCurrencyRequest);
    }

    /* 刪除 */
    @PostMapping("/Cathaybk/delete/{coinCurrencyPK}")
    public CoinCurrencyRs deleteCoinData(@PathVariable long coinCurrencyPK) {
        logger.info("Post Cathaybk delete coinCurrencyPK={}", coinCurrencyPK);
        return cathaybkService.deleteCoinData(coinCurrencyPK);
    }

    /* 查詢 */
    @PostMapping("/Cathaybk/search/{coinCurrencyPK}")
    public CoinCurrencyRs searchCoinData(@PathVariable long coinCurrencyPK) {
        logger.info("Post Cathaybk search coinCurrencyPK={}", coinCurrencyPK);
        return cathaybkService.searchCoinData(coinCurrencyPK);
    }

}
