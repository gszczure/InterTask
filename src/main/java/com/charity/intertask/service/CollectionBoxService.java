package com.charity.intertask.service;

import com.charity.intertask.dto.CollectionBoxDto;
import com.charity.intertask.dto.MoneyDto;
import com.charity.intertask.model.BoxMoney;
import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.model.FundraisingEvent;
import com.charity.intertask.repository.CollectionBoxRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionBoxService {

    private final CollectionBoxRepository boxRepository;
    private final FundraisingEventService eventService;

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
            BoxMoney newMoney = BoxMoney.builder()
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
}