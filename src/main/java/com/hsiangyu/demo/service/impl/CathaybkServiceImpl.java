package com.hsiangyu.demo.service.impl;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsiangyu.demo.config.ConstantConfig;
import com.hsiangyu.demo.dao.ICathaybkBpiRepo;
import com.hsiangyu.demo.dao.ICathaybkCoinCurrencyRepo;
import com.hsiangyu.demo.entity.Bpi;
import com.hsiangyu.demo.entity.CoinCurrency;
import com.hsiangyu.demo.model.BpiDTO;
import com.hsiangyu.demo.model.BpiRs;
import com.hsiangyu.demo.model.CoinCurrencyDTORq;
import com.hsiangyu.demo.model.CoinCurrencyRs;
import com.hsiangyu.demo.model.CoindeskRs;
import com.hsiangyu.demo.service.ICathaybkService;

@Service
public class CathaybkServiceImpl implements ICathaybkService {

    private static final Logger logger = LoggerFactory.getLogger(CathaybkServiceImpl.class);

    /* 主資料表 */
    @Autowired
    private ICathaybkCoinCurrencyRepo coinCurrencyRepo;

    /* 副資料表 */
    @Autowired
    private ICathaybkBpiRepo bpiRepo;

    /* 呼叫 coindesk API，解析其下行內容與資料轉換，並實作新的 API。 */
    @Override
    public CoindeskRs getCoindeskAPI() {
        String coindeskAPI = ConstantConfig.coindeskAPI;
        String resultContent = "";

        CoindeskRs resultObject = new CoindeskRs();
        resultObject.setApiMessage("success");

        /* 打 coindesk API 發出 GET Request */
        HttpGet httpGet = new HttpGet(coindeskAPI);
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;

        try {
            closeableHttpClient = HttpClients.createDefault();
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();
            resultContent = EntityUtils.toString(entity);
            if (closeableHttpResponse.getCode() != 200) {
                resultObject.setApiMessage("fetch coindesk API failure");
            }
        } catch (ParseException e) {
            logger.debug("fetch coindesk API failure");
            logger.debug("ParseException Message {}", e.getMessage());
            logger.debug("{}", e);
            resultObject.setApiMessage("fetch coindesk API failure");
        } catch (IOException e) {
            logger.debug("fetch coindesk API failure");
            logger.debug("IOException Message {}", e.getMessage());
            logger.debug("{}", e);
            resultObject.setApiMessage("fetch coindesk API failure");
        } finally {
            if (closeableHttpResponse != null) {
                try {
                    closeableHttpResponse.close();
                } catch (IOException e) {
                    logger.debug("{}", e);
                }
            }
            if (closeableHttpClient != null) {
                try {
                    closeableHttpClient.close();
                } catch (IOException e) {
                    logger.debug("{}", e);
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        CoinCurrencyDTORq coinCurrencyDTORq = null;
        try {
            coinCurrencyDTORq = objectMapper.readValue(resultContent, CoinCurrencyDTORq.class);
        } catch (JsonMappingException e) {
            logger.debug("analysis JSON data failure");
            logger.debug("JsonMappingException Message {}", e.getMessage());
            logger.debug("{}", e);
            resultObject.setApiMessage("analysis JSON data failure");
        } catch (JsonProcessingException e) {
            logger.debug("analysis JSON data failure");
            logger.debug("JsonProcessingException Message {}", e.getMessage());
            logger.debug("{}", e);
            resultObject.setApiMessage("analysis JSON data failure");
        }

        /* 進行資料轉換，把打 coindesk API取得的 日期更新資料 轉成 Response model。 */
        addUpdateTime(coinCurrencyDTORq, resultObject);

        /* 進行資料轉換，把打 coindesk API取得的 bpi幣值資料 轉成 Response model。 */
        addBpiData(coinCurrencyDTORq, resultObject);

        return resultObject;
    }

    /* 新增(資料庫) */
    @Transactional
    @Override
    public CoinCurrencyRs createCoinData(CoinCurrencyDTORq coinCurrencyDTORq) {
        CoinCurrencyRs resultObject = new CoinCurrencyRs();
        resultObject.setStatus("create");
        resultObject.setApiMessage("success");

        Map<String, BpiDTO> bpiMap = coinCurrencyDTORq.getBpi();
        if (bpiMap.size() == 0) {
            logger.info("error! bpi data not found");
            resultObject.setApiMessage("error! bpi data not found");
            return resultObject;
        }

        /* 主資料庫 新增資料 */
        int insertCount = coinCurrencyRepo.insertCoinCurrency(coinCurrencyDTORq.getTime().getUpdated(),
                coinCurrencyDTORq.getTime().getUpdatedISO(), coinCurrencyDTORq.getTime().getUpdateduk(),
                coinCurrencyDTORq.getDisclaimer(), coinCurrencyDTORq.getChartName());

        resultObject.setCount(insertCount);

        /* 取出新增資料的 PK */
        long insertCCPK = coinCurrencyRepo.getInsertDataPK();

        /* 副資料庫 新增資料 */
        Iterator<Map.Entry<String, BpiDTO>> mapIterator = bpiMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, BpiDTO> entry = mapIterator.next();
            BpiDTO mapValue = entry.getValue();

            Bpi tempEntity = new Bpi();
            tempEntity.setBpiId(insertCCPK);
            tempEntity.setCode(mapValue.getCode());
            tempEntity.setSymbol(mapValue.getSymbol());
            tempEntity.setRate(mapValue.getRate());
            tempEntity.setDescription(mapValue.getDescription());
            tempEntity.setRateFloat(String.valueOf(mapValue.getRateFloat()));

            bpiRepo.save(tempEntity);
        }

        return resultObject;
    }

    /* 修改(資料庫) */
    @Transactional
    @Override
    public CoinCurrencyRs updateCoinData(CoinCurrencyDTORq coinCurrencyDTORq) {
        CoinCurrencyRs resultObject = new CoinCurrencyRs();
        resultObject.setStatus("update");
        resultObject.setApiMessage("success");

        CoinCurrency tempCoinCurrencyEntity = new CoinCurrency();
        tempCoinCurrencyEntity.setCoinCurrencyId(coinCurrencyDTORq.getCoinCurrencyPK());
        tempCoinCurrencyEntity.setUpdatedTime(coinCurrencyDTORq.getTime().getUpdated());
        tempCoinCurrencyEntity.setUpdatedISOTime(coinCurrencyDTORq.getTime().getUpdatedISO());
        tempCoinCurrencyEntity.setUpdatedukTime(coinCurrencyDTORq.getTime().getUpdateduk());
        tempCoinCurrencyEntity.setDisclaimer(coinCurrencyDTORq.getDisclaimer());
        tempCoinCurrencyEntity.setChartName(coinCurrencyDTORq.getChartName());

        /* 主資料庫 更新資料 */
        coinCurrencyRepo.save(tempCoinCurrencyEntity);
        resultObject.setCount(1);

        Map<String, BpiDTO> bpiMap = coinCurrencyDTORq.getBpi();
        if (bpiMap.size() != 0) {
            /* 副資料庫 更新資料 */
            Iterator<Map.Entry<String, BpiDTO>> mapIterator = bpiMap.entrySet().iterator();
            long bpiFK = coinCurrencyDTORq.getCoinCurrencyPK();
            while (mapIterator.hasNext()) {
                Map.Entry<String, BpiDTO> entry = mapIterator.next();
                BpiDTO mapValue = entry.getValue();

                Bpi tempEntity = new Bpi();
                tempEntity.setBpiId(bpiFK);
                tempEntity.setCode(mapValue.getCode());
                tempEntity.setSymbol(mapValue.getSymbol());
                tempEntity.setRate(mapValue.getRate());
                tempEntity.setDescription(mapValue.getDescription());
                tempEntity.setRateFloat(String.valueOf(mapValue.getRateFloat()));

                bpiRepo.save(tempEntity);
            }
        }

        return resultObject;
    }

    /* 刪除(資料庫) */
    @Transactional
    @Override
    public CoinCurrencyRs deleteCoinData(long coinCurrencyPK) {
        CoinCurrencyRs resultObject = new CoinCurrencyRs();
        resultObject.setStatus("delete");
        resultObject.setApiMessage("success");

        bpiRepo.deleteData(coinCurrencyPK); // 刪除副表資料.
        coinCurrencyRepo.deleteData(coinCurrencyPK); // 刪除主表資料.
        resultObject.setCount(1);

        return resultObject;
    }

    /* 查詢(資料庫) */
    @Override
    public CoinCurrencyRs searchCoinData(long coinCurrencyPK) {
        CoinCurrencyRs resultObject = new CoinCurrencyRs();
        resultObject.setStatus("search");
        resultObject.setApiMessage("success");
        Map<String, String> coinConstantMap = ConstantConfig.getCoinConstantMap();

        // 主表查資料.
        CoinCurrency coinCurrency = coinCurrencyRepo.searchDataById(coinCurrencyPK);

        // 如果查無資料就返回空的 Response model.
        if (coinCurrency == null) {
            String errorMessage01 = String.format("%d CoinCurrency-PK no data found.%n", coinCurrencyPK);
            logger.info(errorMessage01);
            resultObject.setApiMessage(errorMessage01);
            return resultObject;
        }

        // 把ISO日期時間 轉成 指定格式日期時間.
        String ccUpdateISOTime = coinCurrency.getUpdatedISOTime();
        logger.info("input date [{}]", ccUpdateISOTime);
        OffsetDateTime dateTime = OffsetDateTime.parse(ccUpdateISOTime);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String outputDateTime = dateTime.format(outputFormatter);
        logger.info("output date [{}]", outputDateTime);

        resultObject.setUpdateTime(outputDateTime);

        // 副表查資料.
        List<Bpi> inputBpiList = bpiRepo.searchDataById(coinCurrencyPK);
        List<BpiRs> bpiRsList = new ArrayList<BpiRs>();
        for (int i = 0; i < inputBpiList.size(); i++) {
            Bpi bpiEntity = inputBpiList.get(i);
            BpiRs tempRs = new BpiRs();

            tempRs.setBpiCode(bpiEntity.getCode());
            tempRs.setBpiCodeZhTW(getVal(coinConstantMap.get(bpiEntity.getCode())));
            tempRs.setRateFloat(Double.valueOf(bpiEntity.getRateFloat()));

            bpiRsList.add(tempRs);
        }
        resultObject.setBpiRsList(bpiRsList);

        return resultObject;
    }

    // 加入更新日期資訊.
    private void addUpdateTime(CoinCurrencyDTORq coinCurrencyDTORq, CoindeskRs resultObject) {
        if (coinCurrencyDTORq == null) {
            logger.info("error! coinCurrencyDTO is null");
            return;
        }
        String inputDateTime = coinCurrencyDTORq.getTime().getUpdatedISO();
        OffsetDateTime dateTime = OffsetDateTime.parse(inputDateTime);

        /* 指定 輸出 的 日期格式 */
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        String outputDateTime = dateTime.format(outputFormatter);

        resultObject.setUpdateTime(outputDateTime);
    }

    private String getVal(String input) {
        if (input == null) {
            return "";
        }
        return input;
    }

    // 加入bpi幣值資訊.
    private void addBpiData(CoinCurrencyDTORq coinCurrencyDTORq, CoindeskRs resultObject) {
        if (coinCurrencyDTORq == null) {
            logger.info("error! coinCurrencyDTO is null");
            return;
        }
        Map<String, String> coinConstantMap = ConstantConfig.getCoinConstantMap();
        List<BpiRs> bpiRsList = new ArrayList<BpiRs>();
        Map<String, BpiDTO> bpiDTOMap = coinCurrencyDTORq.getBpi();
        Iterator<Map.Entry<String, BpiDTO>> mapIterator = bpiDTOMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, BpiDTO> entry = mapIterator.next();
            String mapKey = entry.getKey();
            BpiDTO mapValue = entry.getValue();

            BpiRs temp = new BpiRs();
            temp.setBpiCode(mapKey);
            temp.setBpiCodeZhTW(getVal(coinConstantMap.get(mapKey)));
            temp.setRateFloat(mapValue.getRateFloat());

            bpiRsList.add(temp);
        }

        resultObject.setBpiRsList(bpiRsList);
    }

}
