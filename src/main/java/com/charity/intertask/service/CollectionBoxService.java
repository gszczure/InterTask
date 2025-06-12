package com.charity.intertask.service;

import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.repository.CollectionBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectionBoxService {

    private final CollectionBoxRepository boxRepository;

    @Transactional
    public CollectionBox registerBox() {
        CollectionBox box = new CollectionBox();
        return boxRepository.save(box);
    }
}