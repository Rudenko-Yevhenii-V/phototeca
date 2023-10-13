package com.task.phototeca.finance.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.phototeca.finance.repository.ChanelRepository;
import com.task.phototeca.finance.repository.RateDataRepository;
import com.task.phototeca.finance.repository.entity.ChanelData;
import com.task.phototeca.finance.repository.entity.RateData;
import com.task.phototeca.finance.service.FinanceService;
import com.task.phototeca.finance.service.bean.ExchangeRate;
import com.task.phototeca.telegram.config.BotConfig;
import com.task.phototeca.telegram.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FinanceServiceImpl implements FinanceService {

    TelegramBot bot;

    BotConfig botConfig;

    RateDataRepository rateDataRepository;

    ChanelRepository chanelRepository;

    RestTemplate restTemplate;

    @Value("${crypto.base-url}")
    @NonFinal
    String baseUrl;

    @Value("${crypto.rate}")
    @NonFinal
    String threshold;

    @Override
    public void checkCryptoRates() {
        Optional<RateData> rateData = rateDataRepository.findByOrderByCreatedDateDesc();
        String result = getResponseEntity(baseUrl).getBody();

        if (rateData.isPresent()) {
            String jsonRateData = rateData.get().getJsonRateData();
            List<ExchangeRate> newRate = new Gson()
                    .fromJson(result, new TypeToken<List<ExchangeRate>>(){}.getType());

            List<ExchangeRate> savedRates = new Gson()
                    .fromJson(jsonRateData, new TypeToken<List<ExchangeRate>>(){}.getType());

            StringBuilder infoList = ExchangeRate
                    .getStatistics(newRate, savedRates, Integer.parseInt(threshold));

            if (StringUtils.isNotBlank(infoList)) {
                List<ChanelData> all = chanelRepository.findAll();

                all.stream()
                        .filter(this::isEnabled)
                        .forEach(chanelData -> bot
                            .sendMessage( chanelData.getChanelId(), infoList.toString()));
            }

        }
        rateDataRepository.save(RateData.builder()
                .jsonRateData(result)
                .build());
    }

    private boolean isEnabled(ChanelData cD) {
        if (cD.isEnabled()) return true;
        cD.setEnabled(true);
        chanelRepository.save(cD);
        return false;
    }

    private ResponseEntity<String> getResponseEntity(String url) {
        return restTemplate.getForEntity(url, String.class);
    }
}
