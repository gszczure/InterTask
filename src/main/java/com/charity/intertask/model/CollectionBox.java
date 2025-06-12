package com.charity.intertask.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collection_boxes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private FundraisingEvent assignedEvent;

    @OneToMany(mappedBy = "collectionBox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoxMoney> moneyContents = new ArrayList<>();
}
