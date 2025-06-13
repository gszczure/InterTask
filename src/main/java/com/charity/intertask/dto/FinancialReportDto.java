package com.charity.intertask.dto;

import com.charity.intertask.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialReportDto {
    private List<EventReportEntry> events;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventReportEntry {
        private String name;
        private BigDecimal amount;
        private Currency currency;
    }
}
