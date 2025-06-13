package com.charity.intertask.dto;

import com.charity.intertask.model.Currency;

import java.math.BigDecimal;
import java.util.List;


public class FinancialReportDto {
    private List<EventReportEntry> events;

    public FinancialReportDto() {
    }

    public FinancialReportDto(List<EventReportEntry> events) {
        this.events = events;
    }

    public List<EventReportEntry> getEvents() {
        return events;
    }

    public void setEvents(List<EventReportEntry> events) {
        this.events = events;
    }

    public static class EventReportEntry {
        private String name;
        private BigDecimal amount;
        private Currency currency;

        public EventReportEntry() {
        }

        public EventReportEntry(String name, BigDecimal amount, Currency currency) {
            this.name = name;
            this.amount = amount;
            this.currency = currency;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public Currency getCurrency() {
            return currency;
        }

        public void setCurrency(Currency currency) {
            this.currency = currency;
        }
    }
}
