package com.charity.intertask.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "box_money")
public class BoxMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id", nullable = false)
    private CollectionBox collectionBox;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    public BoxMoney() {
    }

    public BoxMoney(Long id, CollectionBox collectionBox, Currency currency, BigDecimal amount) {
        this.id = id;
        this.collectionBox = collectionBox;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CollectionBox getCollectionBox() {
        return collectionBox;
    }

    public void setCollectionBox(CollectionBox collectionBox) {
        this.collectionBox = collectionBox;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static class builder {
        private Long id;
        private CollectionBox collectionBox;
        private Currency currency;
        private BigDecimal amount;

        public builder id(Long id) {
            this.id = id;
            return this;
        }

        public builder collectionBox(CollectionBox collectionBox) {
            this.collectionBox = collectionBox;
            return this;
        }

        public builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public BoxMoney build() {
            return new BoxMoney(id, collectionBox, currency, amount);
        }
    }
}
