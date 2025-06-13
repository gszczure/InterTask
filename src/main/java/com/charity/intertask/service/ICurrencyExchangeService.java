package com.charity.intertask.service;

import com.charity.intertask.model.Currency;

import java.math.BigDecimal;

public interface ICurrencyExchangeService {

    /**
     * Converts an amount from one currency to another.
     *
     * @param amount Amount to convert
     * @param fromCurrency Source currency
     * @param toCurrency Target currency
     * @return Converted amount
     */
    BigDecimal convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency);
}