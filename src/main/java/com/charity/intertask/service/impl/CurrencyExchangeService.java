package com.charity.intertask.service.impl;

import com.charity.intertask.model.Currency;
import com.charity.intertask.service.ICurrencyExchangeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;

@Service
public class CurrencyExchangeService implements ICurrencyExchangeService {

    private final Map<Currency, Map<Currency, BigDecimal>> exchangeRates;

    public CurrencyExchangeService() {
        exchangeRates = new EnumMap<>(Currency.class);

        Map<Currency, BigDecimal> eurRates = new EnumMap<>(Currency.class);
        eurRates.put(Currency.EUR, BigDecimal.ONE);
        eurRates.put(Currency.USD, new BigDecimal("1.15"));
        eurRates.put(Currency.GBP, new BigDecimal("0.84"));
        exchangeRates.put(Currency.EUR, eurRates);

        Map<Currency, BigDecimal> usdRates = new EnumMap<>(Currency.class);
        usdRates.put(Currency.EUR, new BigDecimal("0.86"));
        usdRates.put(Currency.USD, BigDecimal.ONE);
        usdRates.put(Currency.GBP, new BigDecimal("0.73"));
        exchangeRates.put(Currency.USD, usdRates);

        Map<Currency, BigDecimal> gbpRates = new EnumMap<>(Currency.class);
        gbpRates.put(Currency.EUR, new BigDecimal("1.17"));
        gbpRates.put(Currency.USD, new BigDecimal("1.36"));
        gbpRates.put(Currency.GBP, BigDecimal.ONE);
        exchangeRates.put(Currency.GBP, gbpRates);
    }

    public BigDecimal convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == toCurrency) {
            return amount;
        }

        BigDecimal rate = exchangeRates.get(fromCurrency).get(toCurrency);
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
