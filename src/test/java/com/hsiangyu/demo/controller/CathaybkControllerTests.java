package com.hsiangyu.demo.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/* Unit Test 程式碼 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CathaybkControllerTests {

    @Autowired
    private MockMvc mockMvc;

    /* 這邊的 Unit Test程式 執行時，會直接連接資料庫存取，所以應設定好 測試資料庫 */
    /* 更新 或 刪除 都需要指定 測試資料庫資料表中已存在該筆資料的PK才能成功 */
    /* 用 flag 控制哪隻 Unit Test函數 被執行 */
    private static int flag = -1;

    /* 呼叫 coindesk API，解析其下行內容與資料轉換，並實作新的 API。 */
    @Test
    @Order(2)
    public void getCoindeskAPI() throws Exception {
        System.out.println("#### SpringBootTest-02 ####");
        if (flag == 2) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "text/html; charset='utf-8'");

            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Cathaybk/coindeskAPI").headers(httpHeaders);

            mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.updateTime").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.bpiRsList").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").value("success"));

            System.out.println("$$$$ SpringBootTest-02 end $$$$");
        }
    }

    /* 新增 */
    @Test
    @Order(3)
    public void createCoinData() throws Exception {
        System.out.println("#### SpringBootTest-03 ####");
        if (flag == 3) {
            /* 組出JSON格式資料，做出 RequestBody */
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            JSONObject request = new JSONObject();
            request.put("coinCurrencyPK", -1); // 預設 -1 讓 PK自動新增。.

            JSONObject tempTime = new JSONObject();
            tempTime.put("updated", "Feb 4, 2025 05:53:57 UTC");
            tempTime.put("updatedISO", "2025-02-04T05:53:57+00:00");
            tempTime.put("updateduk", "Feb 4, 2025 at 05:53 GMT");
            request.put("time", tempTime); // TimeDTO物件

            request.put("disclaimer", "This data was produced from the CoinDesk Bitcoin Price");
            request.put("chartName", "Bitcoin");

            JSONObject bpiMap = new JSONObject();

            JSONObject bpi01 = new JSONObject();
            bpi01.put("code", "USD");
            bpi01.put("symbol", "&#36;");
            bpi01.put("rate", "100,775.547");
            bpi01.put("description", "United States Dollar");
            bpi01.put("rate_float", 100775.5466);
            bpiMap.put("USD", bpi01);

            JSONObject bpi02 = new JSONObject();
            bpi02.put("code", "GBP");
            bpi02.put("symbol", "&pound;");
            bpi02.put("rate", "80,931.531");
            bpi02.put("description", "British Pound Sterling");
            bpi02.put("rate_float", 80931.5314);
            bpiMap.put("GBP", bpi02);

            JSONObject bpi03 = new JSONObject();
            bpi03.put("code", "EUR");
            bpi03.put("symbol", "&euro;");
            bpi03.put("rate", "96,349.182");
            bpi03.put("description", "Euro");
            bpi03.put("rate_float", 96349.1823);
            bpiMap.put("EUR", bpi03);

            request.put("bpi", bpiMap);

            System.out.println("request.toString()");
            System.out.println(request.toString());
            System.out.println();
            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Cathaybk/create").headers(httpHeaders)
                    .content(request.toString());

            mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.count").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.updateTime").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("create"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").value("success"));

            System.out.println("$$$$ SpringBootTest-03 end $$$$");
        }
    }

    /* 更新 */
    @Test
    @Order(4)
    public void updateCoinData() throws Exception {
        System.out.println("#### SpringBootTest-04 ####");
        if (flag == 4) {
            /* 組出JSON格式資料，做出 RequestBody */
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            JSONObject request = new JSONObject();
            request.put("coinCurrencyPK", 3); // 資料庫表 已有資料的 PK 。.

            JSONObject tempTime = new JSONObject();
            tempTime.put("updated", "Feb 4, 2025 05:53:57 UTC");
            tempTime.put("updatedISO", "2025-02-04T05:53:57+00:00");
            tempTime.put("updateduk", "Feb 4, 2025 at 05:53 GMT");
            request.put("time", tempTime); // TimeDTO物件

            request.put("disclaimer", "This data was produced from the CoinDesk Bitcoin Price");
            request.put("chartName", "Bitcoin");

            JSONObject bpiMap = new JSONObject();

            JSONObject bpi03 = new JSONObject();
            bpi03.put("code", "EUR");
            bpi03.put("symbol", "&euro;");
            bpi03.put("rate", "96,349.182");
            bpi03.put("description", "Euro");
            bpi03.put("rate_float", 999.1823);
            bpiMap.put("EUR", bpi03);

            request.put("bpi", bpiMap);

            System.out.println("request.toString()");
            System.out.println(request.toString());
            System.out.println();
            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Cathaybk/update").headers(httpHeaders)
                    .content(request.toString());

            mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.count").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.updateTime").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("update"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").value("success"));

            System.out.println("$$$$ SpringBootTest-04 end $$$$");
        }
    }

    /* 查詢 */
    @Test
    @Order(5)
    public void searchCoinData() throws Exception {
        System.out.println("#### SpringBootTest-05 ####");
        if (flag == 5) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "text/html; charset='utf-8'");

            long coinCurrencyPK = 3; // 資料庫表 已有資料的 PK 。.
            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Cathaybk/search/" + coinCurrencyPK)
                    .headers(httpHeaders);

            mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.count").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.updateTime").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.bpiRsList").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("search"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").value("success"));

            System.out.println("$$$$ SpringBootTest-05 end $$$$");
        }
    }

    /* 刪除 */
    @Test
    @Order(6)
    public void deleteCoinData() throws Exception {
        System.out.println("#### SpringBootTest-06 ####");
        if (flag == 6) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "text/html; charset='utf-8'");

            long coinCurrencyPK = 1; // 資料庫表 已有資料的 PK 。.
            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Cathaybk/delete/" + coinCurrencyPK)
                    .headers(httpHeaders);

            mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.count").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.updateTime").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.bpiRsList").hasJsonPath())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("delete"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apiMessage").value("success"));

            System.out.println("$$$$ SpringBootTest-06 end $$$$");
        }
    }

}
