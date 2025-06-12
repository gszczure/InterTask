package com.charity.intertask.service;

import com.charity.intertask.dto.FundraisingEventDto;
import com.charity.intertask.model.FundraisingEvent;
import com.charity.intertask.repository.FundraisingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FundraisingEventService {

    private final FundraisingEventRepository eventRepository;

    @Transactional
    public FundraisingEvent createEvent(FundraisingEventDto eventDto) {
        FundraisingEvent event = FundraisingEvent.builder()
                .name(eventDto.getName())
                .currency(eventDto.getCurrency())
                .balance(BigDecimal.ZERO)
                .build();

        return eventRepository.save(event);
    }
}