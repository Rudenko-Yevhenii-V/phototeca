package com.task.phototeca.common;

import com.task.phototeca.finance.service.FinanceService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class Scheduler {

    FinanceService financeService;

    @Scheduled(fixedDelayString = "${telegram.bot.execute-time-sec}000")
    public void checkAlerts() {
        financeService.checkCryptoRates();
    }
}
