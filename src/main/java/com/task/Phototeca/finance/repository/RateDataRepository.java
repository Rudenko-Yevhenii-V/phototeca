package com.task.phototeca.finance.repository;

import com.task.phototeca.finance.repository.entity.RateData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RateDataRepository extends JpaRepository<RateData, UUID> {

    Optional<RateData> findByOrderByCreatedDateDesc();

}
