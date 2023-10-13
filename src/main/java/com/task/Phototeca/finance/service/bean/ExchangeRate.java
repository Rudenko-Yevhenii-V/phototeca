package com.task.phototeca.finance.service.bean;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@FieldDefaults(level = PRIVATE)
public class ExchangeRate {

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("price")
    private String price;

    public static StringBuilder getStatistics(List<ExchangeRate> one,
                                                  List<ExchangeRate> two,
                                                  int threshold) {

        StringBuilder data = new StringBuilder();
        for (ExchangeRate rateOne : one) {
            for (ExchangeRate rateTwo : two) {
                if (rateOne.getSymbol().equals(rateTwo.getSymbol())) {
                    double priceOne = Double.parseDouble(rateOne.getPrice());
                    double priceTwo = Double.parseDouble(rateTwo.getPrice());

                    double priceDifference = Math.abs(priceOne - priceTwo);
                    int priceChangePercentage = (int)(priceDifference / priceOne) * 100;

                    if (priceChangePercentage > threshold) {
                        String info;
                        if (priceTwo > priceOne) {
                            info = rateOne.getSymbol() + " increased by " + priceDifference + "\n";
                        } else {
                            info = rateOne.getSymbol() + " decreased by " + priceDifference + "\n";
                        }
                        data.append(info);
                    }
                }
            }
        }
        return data;
    }

}