package com.charity.intertask.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fundraising_events")
public class FundraisingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal balance;

    public FundraisingEvent() {
    }

    public FundraisingEvent(Long id, String name, Currency currency, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static class builder {
        private Long id;
        private String name;
        private Currency currency;
        private BigDecimal balance;

        public builder id(Long id) {
            this.id = id;
            return this;
        }

        public builder name(String name) {
            this.name = name;
            return this;
        }

        public builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public FundraisingEvent build() {
            return new FundraisingEvent(id, name, currency, balance);
        }
    }

}
