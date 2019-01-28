package com.akvasoft.tychewebapp.repo;

import com.akvasoft.tychewebapp.modal.RateDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RateDetailsRepo extends JpaRepository<RateDetails, Integer> {

    @Query(value = "select * from T_RATE_DETAILS where SCR_DATE >= ?2 and SYMBOL=?1", nativeQuery = true)
    List<RateDetails> getLastDaysRecords(String currency, Date date);
}
