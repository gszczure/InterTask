package com.charity.intertask.service;

import com.charity.intertask.dto.CollectionBoxDto;
import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.model.FundraisingEvent;
import com.charity.intertask.repository.CollectionBoxRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}