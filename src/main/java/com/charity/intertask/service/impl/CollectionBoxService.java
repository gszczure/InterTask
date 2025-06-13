package com.charity.intertask.service.impl;

import com.charity.intertask.dto.CollectionBoxDto;
import com.charity.intertask.dto.MoneyDto;
import com.charity.intertask.model.BoxMoney;
import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.model.Currency;
import com.charity.intertask.model.FundraisingEvent;
import com.charity.intertask.repository.CollectionBoxRepository;
import com.charity.intertask.service.ICollectionBoxService;
import com.charity.intertask.service.ICurrencyExchangeService;
import com.charity.intertask.service.IFundraisingEventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionBoxService implements ICollectionBoxService {

    private final CollectionBoxRepository boxRepository;
    private final IFundraisingEventService eventService;
    private final ICurrencyExchangeService currencyExchangeService;

    public CollectionBoxService(CollectionBoxRepository boxRepository, IFundraisingEventService eventService, ICurrencyExchangeService currencyExchangeService) {
        this.boxRepository = boxRepository;
        this.eventService = eventService;
        this.currencyExchangeService = currencyExchangeService;
    }

    @Transactional
    public CollectionBox registerBox() {
        CollectionBox box = new CollectionBox();
        return boxRepository.save(box);
    }

    @Transactional(readOnly = true)
    public List<CollectionBoxDto> getAllBoxes() {
        return boxRepository.findAll().stream()
                .map(box -> new CollectionBoxDto(
                        box.getId(),
                        box.getAssignedEvent() != null,
                        box.isEmpty()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CollectionBox getBoxById(Long id) {
        return boxRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collection box not found with id: " + id));
    }

    @Transactional
    public CollectionBox assignBoxToEvent(Long boxId, Long eventId) {
        CollectionBox box = getBoxById(boxId);
        FundraisingEvent event = eventService.getEventById(eventId);

        if (!box.isEmpty()) {
            throw new IllegalStateException("Cannot assing a non-emty collection box");
        }

        box.setAssignedEvent(event);
        return boxRepository.save(box);
    }

    @Transactional
    public void addMoneyToBox(Long boxId, MoneyDto moneyDto) {
        CollectionBox box = getBoxById(boxId);

        if (box.getAssignedEvent() == null) {
            throw new IllegalStateException("Cannot add money to an unassigned collection box");
        }

        Optional<BoxMoney> existingMoney = box.getMoneyContents().stream()
                .filter(m -> m.getCurrency() == moneyDto.getCurrency())
                .findFirst();

        if (existingMoney.isPresent()) {
            BoxMoney money = existingMoney.get();
            money.setAmount(money.getAmount().add(moneyDto.getAmount()));
        } else {
            BoxMoney newMoney = new BoxMoney.builder()
                    .collectionBox(box)
                    .currency(moneyDto.getCurrency())
                    .amount(moneyDto.getAmount())
                    .build();
            box.getMoneyContents().add(newMoney);
        }

        boxRepository.save(box);
    }

    @Transactional
    public void deleteBox(Long id) {
        CollectionBox box = getBoxById(id);
        boxRepository.delete(box);
    }

    @Transactional
    public void withdrawFromBox(Long boxId) {
        CollectionBox box = getBoxById(boxId);

        if (box.getAssignedEvent() == null) {
            throw new IllegalStateException("Cannot empty an unassigned collection box");
        }

        FundraisingEvent event = box.getAssignedEvent();
        Currency targetCurrency = event.getCurrency();

        BigDecimal totalConverted = BigDecimal.ZERO;

        for (BoxMoney money : box.getMoneyContents()) {
            BigDecimal convertedAmount = currencyExchangeService.convert(
                    money.getAmount(),
                    money.getCurrency(),
                    targetCurrency
            );
            totalConverted = totalConverted.add(convertedAmount);
        }

        event.setBalance(event.getBalance().add(totalConverted));

        box.getMoneyContents().clear();
    }
}