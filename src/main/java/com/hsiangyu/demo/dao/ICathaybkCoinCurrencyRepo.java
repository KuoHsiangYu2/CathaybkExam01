package com.hsiangyu.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsiangyu.demo.entity.CoinCurrency;

/* 查詢幣值 Repository */
@Repository
public interface ICathaybkCoinCurrencyRepo extends JpaRepository<CoinCurrency, Long> {

    @Modifying
    @Query(value = " INSERT INTO "
            + " coin_currency(updated_time, updatedISO_time, updateduk_time, disclaimer, chart_name) "
            + " VALUES(:updated_time, :updatedISO_time, :updateduk_time, :disclaimer, :chart_name) ", nativeQuery = true)
    public abstract int insertCoinCurrency(@Param("updated_time") String updatedTime,
            @Param("updatedISO_time") String updatedISOTime, @Param("updateduk_time") String updatedukTime,
            @Param("disclaimer") String disclaimer, @Param("chart_name") String chartName);

    /* 用來找出最新一筆新增資料的 PK值 */
    @Query(value = " SELECT coin_currency_id FROM coin_currency " + " ORDER BY coin_currency_id DESC "
            + " LIMIT 1 ", nativeQuery = true)
    public abstract long getInsertDataPK();

    @Modifying
    @Query(value = " DELETE FROM coin_currency " + " WHERE coin_currency_id = :coinCurrencyId ", nativeQuery = true)
    public abstract void deleteData(@Param("coinCurrencyId") long coinCurrencyPK);

    @Query(value = " SELECT * FROM coin_currency " + " WHERE coin_currency_id = :coinCurrencyId ", nativeQuery = true)
    public abstract CoinCurrency searchDataById(@Param("coinCurrencyId") long coinCurrencyPK);

}
