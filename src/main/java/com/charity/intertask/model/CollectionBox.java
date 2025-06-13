package com.charity.intertask.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collection_boxes")
public class CollectionBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private FundraisingEvent assignedEvent;

    @OneToMany(mappedBy = "collectionBox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoxMoney> moneyContents = new ArrayList<>();

    public CollectionBox() {
    }

    public CollectionBox(Long id, FundraisingEvent assignedEvent, List<BoxMoney> moneyContents) {
        this.id = id;
        this.assignedEvent = assignedEvent;
        this.moneyContents = moneyContents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FundraisingEvent getAssignedEvent() {
        return assignedEvent;
    }

    public void setAssignedEvent(FundraisingEvent assignedEvent) {
        this.assignedEvent = assignedEvent;
    }

    public List<BoxMoney> getMoneyContents() {
        return moneyContents;
    }

    public void setMoneyContents(List<BoxMoney> moneyContents) {
        this.moneyContents = moneyContents;
    }

    public boolean isEmpty() {
        return moneyContents.isEmpty() ||
                moneyContents.stream()
                        .allMatch(money -> money.getAmount().signum() == 0);
    }
}
