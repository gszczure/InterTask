package com.charity.intertask.service;

import com.charity.intertask.dto.CollectionBoxDto;
import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.repository.CollectionBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionBoxService {

    private final CollectionBoxRepository boxRepository;

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
}