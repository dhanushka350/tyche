package com.akvasoft.tychewebapp.repo;

import com.akvasoft.tychewebapp.modal.RateDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateDetailsRepo extends JpaRepository<RateDetails, Integer> {
}
