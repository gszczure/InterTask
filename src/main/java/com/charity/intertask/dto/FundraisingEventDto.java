package com.charity.intertask.dto;

import com.charity.intertask.model.Currency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FundraisingEventDto {

    private Long id;

    @NotBlank(message = "Event name is required")
    private String name;

    @NotNull(message = "Currency is required")
    private Currency currency;

    public FundraisingEventDto() {
    }

    public FundraisingEventDto(Long id, String name, Currency currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public static class Builder {
        private Long id;
        private String name;
        private Currency currency;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public FundraisingEventDto build() {
            return new FundraisingEventDto(id, name, currency);
        }
    }
}
