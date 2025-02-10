package com.hsiangyu.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsiangyu.demo.entity.Bpi;

/* 查詢幣值 Repository */
@Repository
public interface ICathaybkBpiRepo extends JpaRepository<Bpi, Long> {

    @Modifying
    @Query(value = " DELETE FROM bpi " + " WHERE bpi_id = :bpiId ", nativeQuery = true)
    public abstract void deleteData(@Param("bpiId") long bpiId);

    @Query(value = " SELECT * FROM bpi " + " WHERE bpi_id = :bpiId ", nativeQuery = true)
    public abstract List<Bpi> searchDataById(@Param("bpiId") long bpiId);

}
