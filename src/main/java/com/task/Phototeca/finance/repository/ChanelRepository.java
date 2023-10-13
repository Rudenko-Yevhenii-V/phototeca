package com.task.phototeca.finance.repository;

import com.task.phototeca.finance.repository.entity.ChanelData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChanelRepository extends JpaRepository<ChanelData, UUID> {

    Optional<ChanelData> findByChanelId(long chatId);

}
