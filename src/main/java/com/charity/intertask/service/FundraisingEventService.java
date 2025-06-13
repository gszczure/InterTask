package com.charity.intertask.service;

import com.charity.intertask.dto.FinancialReportDto;
import com.charity.intertask.dto.FundraisingEventDto;
import com.charity.intertask.model.FundraisingEvent;
import com.charity.intertask.repository.FundraisingEventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public FundraisingEvent getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public FinancialReportDto generateFinancialReport() {
        List<FinancialReportDto.EventReportEntry> entries = eventRepository.findAll().stream()
                .map(event -> new FinancialReportDto.EventReportEntry(
                        event.getName(),
                        event.getBalance(),
                        event.getCurrency()
                ))
                .collect(Collectors.toList());

        return new FinancialReportDto(entries);
    }
}